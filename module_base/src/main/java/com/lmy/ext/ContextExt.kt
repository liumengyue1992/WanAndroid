package com.lmy.ext

import com.lmy.base.BaseApplication

fun getStringRes(resId: Int): String = BaseApplication.mContext.getString(resId)

fun getStringRes(resId: Int, arg: String): String = BaseApplication.mContext.getString(resId, arg)