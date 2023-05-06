package com.lmy.wanandroid

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.lmy.base.BaseApplication
import com.lmy.module_home.di.homeModule
import com.lmy.uitl.CrashHandlerUtils
import com.lmy.uitl.isMainProcess
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/4/21 11:20
 */
class MyApplication : BaseApplication() {
    private val modules = mutableListOf(homeModule)

    override fun onCreate() {
        super.onCreate()
        if (isMainProcess(this)) {
            init()
        }
    }

    private fun init() {
        Utils.init(this)
        CrashHandlerUtils.instance.init(this)
        initKoin()
        initArouter()
    }

    private fun initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog() // 开启日志
            ARouter.openDebug() // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
        }
        ARouter.init(this)
    }

    private fun initKoin() {
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MyApplication)
            androidFileProperties()
            modules(modules)
        }
    }
}