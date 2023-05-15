package com.lmy.module_project.di

import com.lmy.module_project.api.ProjectApi
import com.lmy.module_project.repo.ProjectRepo
import com.lmy.module_project.viewmodel.ProjectViewModel
import com.lmy.network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
val projectModule = module {
    single { RetrofitManager.getService(ProjectApi::class.java) }
    single { ProjectRepo(get()) }
    viewModel { ProjectViewModel(get()) }
}