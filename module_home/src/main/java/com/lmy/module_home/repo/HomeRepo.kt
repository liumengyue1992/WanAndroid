package com.lmy.module_home.repo

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseRepository
import com.lmy.module_home.api.HomeApi
import com.lmy.module_home.bean.Article
import com.lmy.module_home.bean.ArticleDetail
import com.lmy.module_home.bean.BannerBean

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/20 14:16
 */
class HomeRepo(private val homeApi: HomeApi) : BaseRepository() {

    suspend fun getBanner(data: MutableLiveData<List<BannerBean>>) = request(
        block = { homeApi.getBanner() },
        data,
        // fail及error参数皆可为空，如需要，可以传递到HomeViewModel中赋值给对应的liveData,然后在activity/fragment中进行个性化异常处理
        fail = {
        }
    ) {

    }

    suspend fun getHomeArticle(page: Int, pageSize: Int, data: MutableLiveData<Article>) = request(
        block = { homeApi.getHomeArticle(page, pageSize) },
        data
    )

    suspend fun getHomeTopArticle(data: MutableLiveData<List<ArticleDetail>>) = request(
        block = { homeApi.getHomeTopArticle() },
        data
    )

}