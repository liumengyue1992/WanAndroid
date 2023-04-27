package com.lmy.wanandroid

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.lmy.BaseApplication
import com.lmy.module_home.di.homeModule
import com.lmy.uitl.CrashHandlerUtils
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
    
    companion object {
        lateinit var mContext: Context
    }
    
    override fun onCreate() {
        super.onCreate()
        mContext = this
        Utils.init(this)
        CrashHandlerUtils.instance.init(this)
        
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext (this@MyApplication)
            androidFileProperties ()
            modules(modules)
        }
    }
}