package com.lmy.module_project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseViewModel
import com.lmy.module_project.bean.Project
import com.lmy.module_project.bean.ProjectDetail
import com.lmy.module_project.bean.ProjectType
import com.lmy.module_project.repo.ProjectRepo

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:48
 */
class ProjectViewModel(private val projectRepo: ProjectRepo) : BaseViewModel() {
    val pageSize = 20
    val projectType = MutableLiveData<List<ProjectType>>()
    val projectList = MutableLiveData<Project>()
    val collectResult = MutableLiveData<String>()
    val cancelCollectResult = MutableLiveData<String>()
    fun getProjectCategory() = launch { projectRepo.getProjectCategory(projectType) }
    fun getProjectList(page: Int, cid: Int) {
        launch {
            projectRepo.getProjectList(page, pageSize, cid, projectList)
        }
    }

    fun collect(id: Int) {
        launch { projectRepo.collect(id,collectResult) }
    }

    fun cancelCollect(id:Int){
        launch { projectRepo.cancelCollect(id,cancelCollectResult) }
    }

}