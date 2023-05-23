package com.lmy.module_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseViewModel
import com.lmy.module_common.bean.Article
import com.lmy.module_common.bean.ArticleDetail
import com.lmy.module_home.bean.BannerBean
import com.lmy.module_home.repo.HomeRepo

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/4 11:05
 */
class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {

    private val pageSize = 20
    var bannerList = MutableLiveData<List<BannerBean>>()
    var homeArticle = MutableLiveData<Article>()
    var homeTopArticle = MutableLiveData<List<ArticleDetail>>()
    val collectResult = MutableLiveData<String>()

    fun getBannerData() = launch { repo.getBanner(bannerList) }

    fun getHomeArticle(currentPage: Int) =
        launch { repo.getHomeArticle(currentPage, pageSize, homeArticle) }

    fun getHomeTopArticle() {
        launch { repo.getHomeTopArticle(homeTopArticle) }
    }

    fun collect(id: Int) {
        launch { repo.collect(id,collectResult) }

    }
}