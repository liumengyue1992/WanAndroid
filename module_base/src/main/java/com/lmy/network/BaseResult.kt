package com.lmy.network

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/24 11:10
 */
data class BaseResult<T>(val errorCode: Int, val errorMsg: String?, val data: T) {
    // errorCode如果为负数则认为错误
    // errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
    // errorCode = -1001 代表登录失效或未登录，需要重新登录。
    companion object {
        const val CODE_SUCCESS = 0
        const val CODE_LOGIN_FAILED = -1001
    }
    
    fun isSuccess(): Boolean {
        return errorCode == CODE_SUCCESS
    }
}
