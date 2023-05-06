package com.lmy.base

import android.app.Application

/**
 * @author: mengyue.liu
 * @date: 2023/5/6
 * @description：
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var mContext: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}