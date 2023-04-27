package com.lmy.wanandroid

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lmy.base.BaseViewModel

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/11 14:31
 */
class MainViewModel() : BaseViewModel() {
    val data = MutableLiveData<Int>(0)
    var textString = MutableLiveData<String>("hello world")
    fun getData() {
    }
}