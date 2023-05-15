package com.lmy.module_navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseViewModel
import com.lmy.module_navigation.bean.Navi
import com.lmy.module_navigation.repo.NavigationRepo

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:48
 */
class NavigationViewModel(private val repo: NavigationRepo) : BaseViewModel() {

    val naviList = MutableLiveData<List<Navi>>()
    fun getNaviList() {
        launch { repo.getNaviList(naviList) }
    }
}