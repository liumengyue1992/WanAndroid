package com.lmy.module_navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseViewModel
import com.lmy.module_home.bean.Article
import com.lmy.module_navigation.bean.Navi
import com.lmy.module_navigation.bean.Sys
import com.lmy.module_navigation.repo.NavigationRepo

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:48
 */
class NavigationViewModel(private val repo: NavigationRepo) : BaseViewModel() {

    val naviList = MutableLiveData<List<Navi>>()
    val sysList = MutableLiveData<List<Sys>>()
    val sysArticleList = MutableLiveData<Article>()
    fun getNaviList() {
        launch { repo.getNaviList(naviList) }
    }

    fun getSysList() {
        launch { repo.getSysList(sysList)}
    }

    private val pageSize = 20
    fun getSysArticleList(id: Int, page: Int) {
        launch { repo.getSysArticleList(id,page,pageSize,sysArticleList) }
    }
}