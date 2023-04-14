package com.lmy.network

import com.lmy.base.BuildConfig

/**
 * @Description retrofit管理
 *
 * @author lei.yang
 * @date 2022/4/6
 */
object RetrofitManager {
    
    val apiService: ApiService by lazy {
        RetrofitCreator.getApiService("")
    }
}
