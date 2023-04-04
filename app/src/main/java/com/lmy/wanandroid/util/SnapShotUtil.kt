package com.lmy.wanandroid.util

import android.content.Context
import java.io.File
import java.io.IOException

/**
 * @description：屏幕截屏工具
 * @author：mengyue.liu
 * @time： 2022/6/16 17:05
 */
object SnapShotUtil {
    
    /**
     * 使用命令截屏
     * @param context Context
     * @param fileName String
     */
    fun screenShotByShell(context: Context,fileName: String) {
        // path:/storage/emulated/0/Android/data/<应用包名>/files
        val snapshotDir = context.getExternalFilesDir("snapshot").toString()
        val file = File(snapshotDir)
        if (!file.exists()) {
            file.mkdirs()
        }
        // val cmd = "screencap -p /sdcard/snapshot.png"
        val cmd = "screencap -p $snapshotDir/$fileName.png"
        try {
            Runtime.getRuntime().exec(cmd)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}