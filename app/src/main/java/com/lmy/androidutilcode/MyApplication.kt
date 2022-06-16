package com.lmy.androidutilcode

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.lmy.BaseApplication
import com.lmy.androidutilcode.util.CrashHandlerUtils

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/4/21 11:20
 */
class MyApplication : BaseApplication() {
    
    companion object {
        lateinit var mContext: Context
    }
    
    override fun onCreate() {
        super.onCreate()
        mContext = this
        Utils.init(this)
        CrashHandlerUtils.instance.init(this)
    }
}