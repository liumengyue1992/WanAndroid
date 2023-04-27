package com.lmy.module_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseViewModel
import com.lmy.module_home.bean.Article
import com.lmy.module_home.bean.BannerBean
import com.lmy.module_home.repo.HomeRepo

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/4 11:05
 */
class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {
    val page = 0
    val pageSize = 20
    var bannerList = MutableLiveData<List<BannerBean>>()
    var homeArticle = MutableLiveData<Article>()
    
    fun getBannerData() = launch { repo.getBanner(bannerList) }
    
    fun getHomeArticle() = launch { repo.getHomeArticle(page,pageSize,homeArticle) }
}