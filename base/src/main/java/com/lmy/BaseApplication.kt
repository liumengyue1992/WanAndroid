package com.lmy

import android.app.Application
import com.lmy.uitl.isMainProcess

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/4/21 11:47
 */
open class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (isMainProcess(this)){
            init()
        }
    }

    /**
     * 初始化操作
     */
    private fun init() {

    }
}