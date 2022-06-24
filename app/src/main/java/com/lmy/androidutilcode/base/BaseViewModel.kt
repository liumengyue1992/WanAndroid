package com.lmy.androidutilcode.base

import android.app.Application
import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.network.BaseResult
import com.lmy.androidutilcode.util.DataStoreUtil
import com.lmy.androidutilcode.util.LogUtil
import com.lmy.androidutilcode.util.NetworkUtils
import kotlinx.coroutines.Job

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/11 14:30
 */
open class BaseViewModel(app: Application) : AndroidViewModel(app) {
    
    lateinit var cachedKey: String
    
    /**
     * 设置页面缓存数据唯一key
     */
    fun setCacheKey(key: String) {
        this.cachedKey = key
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
            onFail(it)
        },
        isRequestCache: Boolean = false,
    ): T? {
        try {
            if (!NetworkUtils.isNetworkConnected()) {
                return null
            }
            val response = block.invoke()
            if (response.isOk() && response.data != null) {
                if (isRequestCache) {
                    DataStoreUtil.putData(cachedKey, GsonUtils.toJson(response.data))
                }
                liveData?.value = response.data
                return response.data
            } else {
                fail(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(e)
        }
        return null
    }
    
    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        job?.let {
            if (job.isActive && !job.isCompleted && !job.isCancelled) {
                job.cancel()
            }
        }
    }
    
    private fun onError(e: Exception) {
        LogUtil.d("onError->${e}")
        ToastUtils.showShort("网络异常")
    }
    
    private fun <T> onFail(response: BaseResult<T>) {
        LogUtil.d("onFail->$response")
        // if (!TextUtils.isEmpty(response.msg)) {
        //     if (!TextUtils.equals(response.msg, NET_SUCCESS)) {
        //         response.msg?.let { toastShort(it) }
        //     }
        // } else {
        //     toastShort(R.string.server_slowly)
        // }
    }
}