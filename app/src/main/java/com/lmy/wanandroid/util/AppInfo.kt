package com.lmy.wanandroid.util

import android.graphics.drawable.Drawable

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/4/28 14:51
 */
data class AppInfo(
    var packageName: String?,
    var name: String?,
    var icon: Drawable?,
    var packagePath: String?,
    var versionName: String?,
    var versionCode: Int,
    var isSystem: Boolean,
    var isInAppStore: Boolean, // 该应用是否可以在应用商店下载
) {

    override fun toString(): String {
        return """{
    pkg name: $packageName
    app icon: $icon
    app name: $name
    app path: $packagePath
    app v name: $versionName
    app v code: $versionCode
    is system: $isSystem
    is InAppStore: $isInAppStore
}"""
    }
}
