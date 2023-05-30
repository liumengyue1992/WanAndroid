package com.lmy.module_mine.bean

/**
 * @author: mengyue.liu
 * @date: 2023/5/29
 * @description：
 */
data class RankBean(
    val curPage:Int,
    val datas:List<RankDetail>
)

data class RankDetail(
    val userId:Int,
    val rank:Int,
    val nickname:String,
    val username:String,
    val coinCount:Int
)
