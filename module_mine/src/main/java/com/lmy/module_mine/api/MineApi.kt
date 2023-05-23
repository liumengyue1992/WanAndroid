package com.lmy.module_mine.api

import com.lmy.module_mine.bean.LoginBean
import com.lmy.network.BaseResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
interface MineApi {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") userName: String,@Field("password") passWord: String) :BaseResult<LoginBean>

    @GET("user/logout/json")
    suspend fun logout():BaseResult<Any>

}