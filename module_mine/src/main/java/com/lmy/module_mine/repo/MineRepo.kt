package com.lmy.module_mine.repo

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseRepository
import com.lmy.module_mine.api.MineApi
import com.lmy.module_mine.bean.IntegralRecordBean
import com.lmy.module_mine.bean.LoginBean
import com.lmy.module_mine.bean.MyPointsBean
import com.lmy.module_mine.bean.RankBean

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

    suspend fun getRank(rankBean: MutableLiveData<RankBean>) {
        request(block = {
            api.getRank()
        }, rankBean)

    }

    suspend fun getMyPoints(myPointsBean: MutableLiveData<MyPointsBean>) {
        request(block = {
            api.getMyPoints()
        }, myPointsBean)
    }

    suspend fun getIntegralRecord(
        pageNo: Int,
        integralRecordList: MutableLiveData<IntegralRecordBean>
    ) {
        request(block = {
            api.getIntegralRecord(pageNo)
        },integralRecordList)

    }
}