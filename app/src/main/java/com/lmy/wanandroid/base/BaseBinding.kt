package com.lmy.wanandroid.base

import androidx.databinding.ViewDataBinding

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/11 14:05
 */
interface BaseBinding<V : ViewDataBinding> {
    fun V.initView()
    fun V.initData()
}