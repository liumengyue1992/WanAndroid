package com.lmy.module_navigation.api

import com.lmy.module_navigation.bean.Navi
import com.lmy.network.BaseResult
import retrofit2.http.GET

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
interface NavigationApi {

    @GET("navi/json")
    suspend fun getNaviList(): BaseResult<List<Navi>>
}