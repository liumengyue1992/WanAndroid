package com.lmy.module_mine.api

import com.lmy.module_mine.bean.IntegralRecordBean
import com.lmy.module_mine.bean.LoginBean
import com.lmy.module_mine.bean.MyPointsBean
import com.lmy.module_mine.bean.RankBean
import com.lmy.network.BaseResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
interface MineApi {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): BaseResult<LoginBean>

    @GET("user/logout/json")
    suspend fun logout(): BaseResult<String>

    // 积分排行榜
    @GET("coin/rank/1/json")
    suspend fun getRank(): BaseResult<RankBean>

    // 个人积分
    @GET("lg/coin/userinfo/json")
    suspend fun getMyPoints(): BaseResult<MyPointsBean>

    // 积分记录
    @GET("lg/coin/list/{pageNo}/json")
    suspend fun getIntegralRecord(@Path("pageNo") pageNo:Int): BaseResult<IntegralRecordBean>

}