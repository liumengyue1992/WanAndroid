package com.lmy.ext

import android.view.View

fun View.setGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}