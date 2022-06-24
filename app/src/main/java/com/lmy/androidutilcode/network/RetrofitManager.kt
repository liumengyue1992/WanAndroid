package com.lmy.androidutilcode.network

import com.lmy.androidutilcode.BuildConfig

/**
 * @Description retrofit管理
 *
 * @author lei.yang
 * @date 2022/4/6
 */
object RetrofitManager {
    
    val apiService: ApiService by lazy {
        RetrofitCreator.getApiService(BuildConfig.API_HOST)
    }
}
