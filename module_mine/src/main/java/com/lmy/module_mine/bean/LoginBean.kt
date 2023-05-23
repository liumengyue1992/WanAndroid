package com.lmy.module_mine.bean

/**
 * @author: mengyue.liu
 * @date: 2023/5/22
 * @description：
 */
data class LoginBean(
    var admin: Boolean,
    var chapterTops: List<Any>,
    var coinCount: Int,
    var collectIds: List<Int>,
    var email: String,
    var icon: String,
    var id: Int,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String
)
