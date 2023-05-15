package com.lmy.module_mine.di

import com.lmy.module_mine.api.MineApi
import com.lmy.module_mine.repo.MineRepo
import com.lmy.module_mine.viewmodel.MineViewModel
import com.lmy.network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
val mineModule = module {
    single { RetrofitManager.getService(MineApi::class.java) }
    single { MineRepo(get()) }
    viewModel { MineViewModel(get()) }
}