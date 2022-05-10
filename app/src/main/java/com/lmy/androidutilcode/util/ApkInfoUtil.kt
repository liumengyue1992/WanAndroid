package com.lmy.androidutilcode.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.blankj.utilcode.util.Utils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @description：获取Android应用程序信息(PackageInfo、ActivityInfo、ResolveInfo)
 * @author：Mengyue.Liu
 * @time： 2022/4/28 13:54
 */
object ApkInfoUtil {
    @SuppressLint("SdCardPath")
    const val BACK_UP_PATH = "/sdcard/lmy/backup/"

    /**
     * 获取已安装所有应用
     * @param context Context
     */
    fun queryInstalledApps(context: Context): List<PackageInfo> {
        val pm = context.packageManager
        return pm.getInstalledPackages(PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES)
    }

    /**
     * 获取已安装所有应用的信息(包含各种系统服务/设置,如指南针、屏保程序、壁纸、通话管理、滚动截屏...)
     * @return List<AppInfo>
     */
    fun getAppsInfo(): List<AppInfo> {
        val list: MutableList<AppInfo> = ArrayList()
        val pm = Utils.getApp().packageManager ?: return list
        //PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES  --》替换0？
        val installedPackages = pm.getInstalledPackages(0)
        for (pi in installedPackages) {
            val ai = getBean(pm, pi) ?: continue
            list.add(ai)
        }
        return list
    }


    /**
     * 通过包名获取apk源文件路径
     * @param packageName String
     * @param context Context
     * @return String?
     */
    fun getApkDir(packageName: String, context: Context): String? {
        var apkDir: String? = null
        try {
            apkDir = context.packageManager.getApplicationInfo(packageName, 0).sourceDir
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return apkDir
    }


    /**
     * 根据文件路径备份文件
     * @param path String 原文件的绝对路径
     * @param backupName String 备份的文件名称
     */
    fun backupApp(path: String, backupName: String) {
        val fileIn = File(path)
        val backupFile = File(BACK_UP_PATH)
        if (!backupFile.exists()) backupFile.mkdirs()
        val fileOut = File("$BACK_UP_PATH$backupName.apk")
        if (fileOut.exists()) {
            fileOut.delete()
        }
        fileOut.createNewFile()
        val fis = FileInputStream(fileIn)
        val fos = FileOutputStream(fileOut)
        var count: Int
        val buffer = ByteArray(256 * 1024)
        while (fis.read(buffer).also { count = it } > 0) {
            fos.write(buffer, 0, count)
        }
        fis.close()
        fos.flush()
        fos.close()
    }


    //判断是否是第三方应用方法
    fun isUserApp(pInfo: PackageInfo): Boolean {
        return pInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0 && pInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP == 0
    }

    private fun getBean(pm: PackageManager, pi: PackageInfo?): AppInfo? {
        if (pi == null) return null
        val versionName = pi.versionName
        val versionCode = pi.versionCode
        val packageName = pi.packageName
        val ai = pi.applicationInfo
            ?: return AppInfo(packageName, "", null, "", versionName, versionCode,
                isSystem = false,
                isInAppStore = false)
        val name = ai.loadLabel(pm).toString()
        val icon = ai.loadIcon(pm)
        val packagePath = ai.sourceDir
        val isSystem = ApplicationInfo.FLAG_SYSTEM and ai.flags != 0
        val isInAppStore = isInAppStore(packageName)
        return AppInfo(packageName,
            name,
            icon,
            packagePath,
            versionName,
            versionCode,
            isSystem,
            isInAppStore)
    }

    //todo 需要应用商店提供：完整的已上架应用列表，云服务对比该应用是否存在应用商店；或者提供其它可判断的方式
    private fun isInAppStore(packageName: String): Boolean {
        return true
    }
}