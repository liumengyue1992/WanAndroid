package com.lmy.wanandroid.ui.fragment

import com.lmy.wanandroid.BR
import com.lmy.wanandroid.R
import com.lmy.wanandroid.base.BaseVMFragment
import com.lmy.wanandroid.databinding.FragmentHomeBinding

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/4 10:54
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {
    
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
    
    override fun initVariableId(): Int {
        return BR.vm
    }
    override fun initData() {
    }
}


