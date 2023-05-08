package com.lmy.module_home.di

import com.lmy.module_home.api.HomeApi
import com.lmy.module_home.repo.HomeRepo
import com.lmy.module_home.viewmodel.HomeViewModel
import com.lmy.network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @description：
 * api
 * repo
 * viewModel
 * @author：mengyue.liu
 * @time： 2023/4/20 11:29
 */
val homeModule = module {
    single { RetrofitManager.getService(HomeApi::class.java) }
    single { HomeRepo(get()) }
    viewModel { HomeViewModel(get()) }
}
