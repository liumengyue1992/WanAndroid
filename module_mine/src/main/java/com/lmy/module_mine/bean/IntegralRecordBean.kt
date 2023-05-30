package com.lmy.module_mine.bean

import android.text.BoringLayout

/**
 * @author: mengyue.liu
 * @date: 2023/5/30
 * @description：
 */


data class IntegralRecordBean(
    val over:Boolean,
    val datas:List<IntegralListBean>,
)


data class IntegralListBean(
    val id:Int,
    val date:Long,
    val coinCount:Int,
    val reason:String,
    val desc:String
)