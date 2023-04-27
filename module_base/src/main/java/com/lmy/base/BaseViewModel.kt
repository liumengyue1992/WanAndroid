package com.lmy.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.lmy.network.BaseResult
import com.lmy.uitl.LogUtil
import com.lmy.uitl.NetworkUtils
import com.lmy.wanandroid.util.DataStoreUtil
import kotlinx.coroutines.launch

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/11 14:30
 */
typealias vmBlock = suspend () -> Unit

open class BaseViewModel : ViewModel() {
    
    lateinit var cachedKey: String
    
    /**
     * 设置页面缓存数据唯一key
     */
    fun setCacheKey(key: String) {
        this.cachedKey = key
    }
    
    fun launch(block: vmBlock) {
        // 使用viewModelScope需要添加依赖androidx.navigation:navigation-fragment-ktx
        // 这里不需要指定viewModelScope.launch(Dispatchers.IO)，因为retrofit自身会在子线程进行网络请求
        viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
    
    /**
     * 请求封装
     * @param block SuspendFunction0<BaseResult<T>> 请求体
     * @param liveData MutableLiveData<T> 结果数据
     * @param error Function1<[@kotlin.ParameterName] Exception, Unit> 异常处理回调
     * @param fail Function1<[@kotlin.ParameterName] BaseResult<T>, Unit> 失败处理回调
     * @param isRequestCache 请求是否需要缓存，一般这个作为页面主接口请求设置为true，其他请求默认不传
     */
    suspend fun <T> request(
        block: suspend () -> BaseResult<T>,
        liveData: MutableLiveData<T>? = null,
        error: (e: Exception) -> Unit = {
            onError(it)
        },
        fail: (response: BaseResult<T>) -> Unit = {
        
        },
        isRequestCache: Boolean = false,
    ): T? {
        try {
            if (!NetworkUtils.isNetworkConnected()) {
                return null
            }
            val result = block.invoke()
            if (result.isSuccess() && result.data != null) {
                if (isRequestCache) {
                    DataStoreUtil.putData(cachedKey, GsonUtils.toJson(result.data))
                }
                liveData?.value = result.data
                return result.data
            } else {
                fail(result)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(e)
        }
        return null
    }
    
    
    private fun onError(e: Exception) {
        LogUtil.d("onError->${e}")
        ToastUtils.showShort(e.message)
    }
}