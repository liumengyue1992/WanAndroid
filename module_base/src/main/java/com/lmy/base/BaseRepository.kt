package com.lmy.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.lmy.network.BaseResult
import com.lmy.uitl.LogUtil
import com.lmy.uitl.ToastUtil
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * @description：封装网络请求及异常处理
 * @author：mengyue.liu
 * @time： 2023/4/20 17:28
 */
open class BaseRepository {
    
    /**
     * 网络请求封装
     * @param block SuspendFunction0<BaseResult<T>> 请求体
     * @param liveData MutableLiveData<T>? 请求结果数据
     * @param fail Function1<[@kotlin.ParameterName] BaseResult<T>, Unit> 请求失败异常处理
     * @param error Function1<[@kotlin.ParameterName] Exception, Unit> 请求错误异常处理
     */
    suspend fun <T> request(
        block: suspend () -> BaseResult<T>,
        liveData: MutableLiveData<T>? = null,
        fail: (result: BaseResult<T>) -> Unit = {
            onFail(it)
        },
        error: (e: Exception) -> Unit = {
            onError(it)
        },
    ) {
        try {
            
            //-------网络请求---------//
            val result = block.invoke()
            //-------网络请求---------//
            
            when (result.errorCode) {
                BaseResult.CODE_SUCCESS -> {
                    if (result.data != null) {
                        liveData?.value = result.data
                    }
                }
                BaseResult.CODE_LOGIN_FAILED -> {
                    onFail(result)
                    ToastUtil.showShort("认证过期，请重新登录！")
                    // TODO: 统一跳转到重新登录的页面
                }
                else -> {
                    onFail(result)
                    ToastUtil.showShort("code:" + result.errorCode.toString() + " / msg:" + result.errorMsg)
                }
            }
        } catch (e: Exception) {
            onError(e)
            Log.d("BaseRepository", "dealResp: Exception$e")
            when (e) {
                is UnknownHostException,
                is HttpException,
                is ConnectException,
                -> {
                    //网络error
                    ToastUtil.showShort("网络错误！")
                }
                else -> {
                    ToastUtil.showShort("未知错误！")
                }
            }
        }
    }
    
    /**
     * 对网络请求失败的统一处理，调用方也可以自行传参进行个性化处理
     * @param result BaseResult<T>
     */
    private fun <T> onFail(result: BaseResult<T>) {
        LogUtil.d("onFail->${result}")
        ToastUtils.showShort("errorCode = " + result.errorCode + "errorMsg = " + result.errorMsg)
    }
    
    /**
     * 对网络请求错误的统一处理，调用方也可以自行传参进行个性化处理
     * @param exception Exception
     */
    private fun onError(exception: Exception) {
        LogUtil.d("onError->${exception}")
        ToastUtils.showShort(exception.message)
    }
}