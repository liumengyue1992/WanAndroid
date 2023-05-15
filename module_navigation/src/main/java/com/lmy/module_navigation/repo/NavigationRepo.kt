package com.lmy.module_navigation.repo

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseRepository
import com.lmy.module_navigation.api.NavigationApi
import com.lmy.module_navigation.bean.Navi

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

}
