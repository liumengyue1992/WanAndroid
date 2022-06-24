package com.lmy.androidutilcode.util

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * binding adapter
 */
object AdapterBindingUtil {
    
    /**
     * 设置圆角
     * @param view View
     * @param roundType String
     * @param roundRadius Float
     */
    @BindingAdapter(value = ["roundType", "roundRadius"], requireAll = false)
    @JvmStatic
    fun setRound(view: View, roundType: String, roundRadius: Float = 0f) {
        val outlineProvider = when (roundType) {
            "oval" -> OutlineProviderHelper.getOutlineProviderOval()
            "round" -> OutlineProviderHelper.getOutlineProvider(roundRadius)
            "half" -> OutlineProviderHelper.getOutlineProviderHalfHeight()
            else -> null
        }
        outlineProvider?.let {
            view.clipToOutline = true
            view.outlineProvider = it
        }
    }
}

