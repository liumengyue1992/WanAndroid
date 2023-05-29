package com.lmy.module_navigation.api

import com.lmy.module_common.bean.Article
import com.lmy.module_navigation.bean.Navi
import com.lmy.module_navigation.bean.Sys
import com.lmy.network.BaseResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
interface NavigationApi {

    @GET("navi/json")
    suspend fun getNaviList(): BaseResult<List<Navi>>

    @GET("tree/json")
    suspend fun getSysList(): BaseResult<List<Sys>>

    @GET("article/list/{page}/json")
    suspend fun getSysArticleList(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("cid") cid: Int
    ): BaseResult<Article>

    // 收藏站内文章
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseResult<String>

    // 取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id")id: Int):BaseResult<String>
}