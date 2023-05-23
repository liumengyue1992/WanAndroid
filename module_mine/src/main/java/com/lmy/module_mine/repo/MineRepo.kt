package com.lmy.module_mine.repo

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseRepository
import com.lmy.module_mine.api.MineApi
import com.lmy.module_mine.bean.LoginBean

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
class MineRepo(private val api: MineApi) : BaseRepository() {
    suspend fun login(userName: String, passWord: String, loginBean: MutableLiveData<LoginBean>) {
        request(
            block = {
                api.login(userName, passWord)

            }, loginBean
        )
    }

    suspend fun logout() {
        request(block = {
            api.logout()
        })
    }
}