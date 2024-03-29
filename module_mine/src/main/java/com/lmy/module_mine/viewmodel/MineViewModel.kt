package com.lmy.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseViewModel
import com.lmy.module_mine.bean.IntegralRecordBean
import com.lmy.module_mine.bean.LoginBean
import com.lmy.module_mine.bean.MyPointsBean
import com.lmy.module_mine.bean.RankBean
import com.lmy.module_mine.repo.MineRepo
import com.lmy.wanandroid.util.DataStoreUtil

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:48
 */
class MineViewModel(private val repo: MineRepo) : BaseViewModel() {
    val loginBean = MutableLiveData<LoginBean>()
    var rankBean = MutableLiveData<RankBean>()
    val myPointsBean = MutableLiveData<MyPointsBean>()
    val integralRecordList = MutableLiveData<IntegralRecordBean>()
    fun login(userName: String, passWord: String) {
        launch {
            repo.login(userName, passWord,loginBean)
        }
    }

    fun logout() {
        launch {
            repo.logout()
            // 清理cookie  清理datastore本地缓存数据
            DataStoreUtil.clear()
        }
    }

    fun getRank() {
        launch {
            repo.getRank(rankBean)
        }

    }

    fun getMyPoints() {
        launch { repo.getMyPoints(myPointsBean) }
    }

    fun getIntegralRecord(pageNo:Int) {
        launch { repo.getIntegralRecord(pageNo,integralRecordList) }
    }

}