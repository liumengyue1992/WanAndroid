package com.lmy.module_navigation.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author: mengyue.liu
 * @date: 2023/5/17
 * @description：
 */
@Parcelize
data class Sys(
    val id: Int,
    val name: String,
    val children: List<SysChild>,
) : Parcelable

@Parcelize
data class SysChild(
    val id: Int,
    val name: String,
) : Parcelable
