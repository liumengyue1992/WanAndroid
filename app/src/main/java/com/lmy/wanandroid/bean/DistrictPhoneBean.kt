package com.lmy.wanandroid.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/24 10:54
 */
data class DistrictPhoneBean(
    var initial: String? = null,
    var firstNameList: MutableList<String>? = null,
    var phoneAreaCodes: MutableList<DistrictPhoneItemBean>? = null,
)

/**
 * 国家地区 手机号区号
 */
@Parcelize
data class DistrictPhoneItemBean(
    var cn: String?,
    var code: String?,
    var en: String?,
    var initial: String?,
) : Parcelable

/**
 * 滚动的位置
 */
data class DistrictPhoneIndexBean(var index: Int = 0, var childIndex: Int = 0)
