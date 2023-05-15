package com.lmy.module_navigation.bean

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
data class Navi(
    val cid: Int,
    val name: String,
    val articles: List<NaviChild>
)

data class NaviChild(
    val id: Int,
    val link: String,
    val title: String,
)
