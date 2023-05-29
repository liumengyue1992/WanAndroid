package com.lmy.module_project.api

import com.lmy.module_project.bean.Project
import com.lmy.module_project.bean.ProjectDetail
import com.lmy.module_project.bean.ProjectType
import com.lmy.network.BaseResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
interface ProjectApi {

    @GET("project/tree/json")
    suspend fun getProjectCategory(): BaseResult<List<ProjectType>>

    @GET("project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("cid") cid: Int
    ): BaseResult<Project>

    // 收藏站内文章
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseResult<String>

    // 取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id")id: Int):BaseResult<String>
}