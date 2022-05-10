package com.lmy.androidutilcode

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.lmy.BaseApplication

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/4/21 11:20
 */
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}