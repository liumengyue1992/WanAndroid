package com.lmy.module_navigation.repo

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseRepository
import com.lmy.module_home.bean.Article
import com.lmy.module_navigation.api.NavigationApi
import com.lmy.module_navigation.bean.Navi
import com.lmy.module_navigation.bean.Sys

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
class NavigationRepo(private val api: NavigationApi) : BaseRepository() {
    suspend fun getNaviList(naviList: MutableLiveData<List<Navi>>) =
        request(
            block = {
                api.getNaviList()
            },
            naviList
        )

    suspend fun getSysList(sysList: MutableLiveData<List<Sys>>) {
        request(
            block = {
                api.getSysList()
            }, sysList
        )
    }

    suspend fun getSysArticleList(id: Int, page: Int, pageSize: Int,sysArticle:MutableLiveData<Article>) {
        request(
            block = {
                api.getSysArticleList(page,pageSize,id)
            },
            sysArticle
        )
    }
}
