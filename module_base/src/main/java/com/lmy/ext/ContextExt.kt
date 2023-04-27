package com.lmy.ext

import android.content.Context
import com.lmy.BaseApplication

fun getStringRes(resId: Int): String = BaseApplication.mContext.getString(resId)

fun getStringRes(resId: Int, arg: String): String = BaseApplication.mContext.getString(resId, arg)