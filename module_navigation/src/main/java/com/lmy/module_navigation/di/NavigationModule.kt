package com.lmy.module_navigation.di

import com.lmy.module_navigation.api.NavigationApi
import com.lmy.module_navigation.repo.NavigationRepo
import com.lmy.module_navigation.viewmodel.NavigationViewModel
import com.lmy.network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */

val navigationModule = module {
    single { RetrofitManager.getService(NavigationApi::class.java) }
    single { NavigationRepo(get()) }
    viewModel { NavigationViewModel(get()) }
}