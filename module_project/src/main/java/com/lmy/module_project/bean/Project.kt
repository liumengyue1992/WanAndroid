package com.lmy.module_project.bean

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
data class Project(
    val datas: List<ProjectDetail>,
    var over: Boolean,
    val curPage: Int
)

data class ProjectDetail(
    val id: Int,
    val author: String,
    val link: String,
    val desc: String,
    val niceDate: String,
    val envelopePic: String,
    val title: String,
    var collect: Boolean
)
