package com.lmy.module_home.ext

import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author: mengyue.liu
 * @date: 2023/5/10
 * @description：
 */

/**
 * BottomNavigationView长按tab会弹出toast
 *
 * @param ids
 */
fun BottomNavigationView.clearLongClickToast(ids: MutableList<Int>) {
    val bottomNavigationView: ViewGroup = getChildAt(0) as ViewGroup
    for (position in 0 until ids.size) {
        bottomNavigationView.getChildAt(position).findViewById<View>(ids[position])
            .setOnLongClickListener { true }
    }
}