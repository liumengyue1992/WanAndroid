package com.lmy.network

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/14 11:05
 */
class BaseResponse<T> {
    // errorCode如果为负数则认为错误
    // errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
    // errorCode = -1001 代表登录失效，需要重新登录。
    var errorCode: Int = -1
    var errorMsg: String = ""
    var data: T? = null
}