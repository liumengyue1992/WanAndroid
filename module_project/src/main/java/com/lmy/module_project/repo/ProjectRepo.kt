package com.lmy.module_project.repo

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseRepository
import com.lmy.module_project.bean.ProjectType
import com.lmy.module_project.api.ProjectApi
import com.lmy.module_project.bean.Project
import com.lmy.module_project.bean.ProjectDetail

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
class ProjectRepo(private val projectApi: ProjectApi) : BaseRepository() {

    suspend fun getProjectCategory(data: MutableLiveData<List<ProjectType>>) = request(
        block = { projectApi.getProjectCategory() }, data
    )

    suspend fun getProjectList(
        page: Int,
        pageSize: Int,
        cid: Int,
        data: MutableLiveData<Project>
    ) =
        request(block = { projectApi.getProjectList(page, pageSize, cid) }, data)



    suspend fun collect(id: Int, collectResult: MutableLiveData<String>) {
        request(block = {
            projectApi.collect(id)
        }, collectResult)
    }

    suspend fun cancelCollect(id: Int, cancelCollectResult: MutableLiveData<String>) {
        request(block = {
            projectApi.cancelCollect(id)
        },cancelCollectResult)
    }
}