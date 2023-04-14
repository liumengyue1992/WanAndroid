package com.lmy.uitl

import android.app.ActivityManager
import android.content.Context
import android.os.Process

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/4/21 11:50
 */


/**
 * 是否是主进程
 * @param context Context
 * @return Boolean
 */
fun isMainProcess(context: Context) = context.packageName == currentProcessName(context)

/**
 * 当前进程的名称
 */
private fun currentProcessName(context: Context): String {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (process in manager.runningAppProcesses) {
        if (process.pid == Process.myPid()) {
            return process.processName
        }
    }
    return ""
}