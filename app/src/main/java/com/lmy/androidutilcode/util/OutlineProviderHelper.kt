package com.lmy.androidutilcode.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

/**
 * @Description: TODO
 * @Author: liheng.cai
 * @Date: 2022/1/17 16:48
 * @Email: liheng.cai@upuphone.com
 */
object OutlineProviderHelper {
    
    /**
     *
     * @param v Float
     * @return ViewOutlineProvider
     */
    fun getOutlineProvider(v: Float): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, v)
            }
        }
    }
    
    /**
     *
     * @return ViewOutlineProvider
     */
    fun getOutlineProviderOval(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setOval(0, 0, view.width, view.height)
            }
        }
    }
    
    /**
     *
     * @return ViewOutlineProvider
     */
    fun getOutlineProviderHalfHeight(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, (view.height / 2).toFloat())
            }
        }
    }
}
