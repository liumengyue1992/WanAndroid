package com.lmy.wanandroid.network

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/24 11:10
 */
data class BaseResult<T>(val code: Int, val msg: String?, val data: T) {
    fun isOk(): Boolean {
        return code == CODE_SUCCESS
    }
    
    companion object {
        const val CODE_SUCCESS = 0
        const val CODE_TOKEN_EXPIRE = 800004
        const val CODE_TOKEN_EMPTY = 800001
        const val CODE_CLOUD_SPACE_FULL = 4001
        const val CODE_BACKUP_REPEAT = 2174979
    }
}
