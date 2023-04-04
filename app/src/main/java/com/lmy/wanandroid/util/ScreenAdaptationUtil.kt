package com.lmy.wanandroid.util


import android.app.Activity

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration


/**
 * @Description: 屏幕适配工具类
 * @Author: liheng.cai
 * @Date: 2022/1/17 13:43
 * @Email: liheng.cai@upuphone.com
 */
object ScreenAdaptationUtil {

    private var sNonCompatDensity = 0f
    private var sNonCompatScaledDensity = 0f

    //默认假设 设计图宽360dp 我们根据实际设计图的尺寸修改
    private var designWidth = 360

    //默认假设 设计图高640dp 我们根据实际设计图的尺寸修改
    private var designHeight = 804

    /**
     *
     * @param width
     * @param height
     */
    fun initDesignSize(width: Int, height: Int) {
        designHeight = height
        designWidth = width
    }

    private fun setCustomDensity(activity: Activity, context: Context, isWidth: Boolean) {
        val displayMetrics = context.resources.displayMetrics
        if (sNonCompatDensity == 0f) {
            sNonCompatDensity = displayMetrics.density
            sNonCompatScaledDensity = displayMetrics.scaledDensity
            context.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = context.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }
        val targetDensity = if (isWidth) {
            //根据宽适配
            (displayMetrics.widthPixels / designWidth).toFloat()
        } else {
            //根据高适配
            (displayMetrics.heightPixels / designHeight).toFloat()
        }
        val targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity)
        val targetDensityDpi = (160 * targetDensity).toInt()
        displayMetrics.density = targetDensity
        displayMetrics.scaledDensity = targetScaledDensity
        displayMetrics.densityDpi = targetDensityDpi
        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }

    /**
     * 根据设计图宽设定density
     * @param activity
     * @param application
     */
    fun setDensityByWidth(activity: Activity, context: Context) {
        setCustomDensity(activity, context, true)
    }

    /**
     * 根据设计图高设定density
     * @param activity
     * @param application
     */
    fun setDensityByHeight(activity: Activity, context: Context) {
        setCustomDensity(activity, context, false)
    }
}