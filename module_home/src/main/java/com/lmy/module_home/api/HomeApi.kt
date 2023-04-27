package com.lmy.module_home.api

import com.lmy.module_home.bean.Article
import com.lmy.module_home.bean.BannerBean
import com.lmy.network.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/20 14:14
 */
interface HomeApi {
    
    // 首页banner
    @GET("banner/json")
    suspend fun getBanner(): BaseResult<List<BannerBean>>
    
   
    // 首页文章列表-- 注：该接口支持传入 page_size 控制分页数量，取值为[1-40]，不传则使用默认值，一旦传入了 page_size，后续该接口分页都需要带上，否则会造成分页读取错误。
    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page")page: Int,@Query("page_size") pageSize:Int): BaseResult<List<Article>>
}