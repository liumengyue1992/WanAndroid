package com.lmy.uitl

import android.widget.Toast
import com.lmy.base.BaseApplication

/**
 * @description：
 * 封装基础的toast使用方式，另外，需要解决的问题有：
 * 1、弹出一个新的toast时，上一个toast还没有显示完
 * 2、可能重复弹出相同的信息
 * 3、app退出了，toast还在谈
 * @author：Mengyue.Liu
 * @time： 2022/4/21 11:05
 */
object ToastUtil {

    fun showShort(msg: CharSequence) {
        Toast.makeText(BaseApplication.mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLong(msg: CharSequence) {
        Toast.makeText(BaseApplication.mContext, msg, Toast.LENGTH_LONG).show()
    }


}