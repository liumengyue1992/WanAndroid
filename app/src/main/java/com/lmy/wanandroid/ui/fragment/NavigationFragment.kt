package com.lmy.wanandroid.ui.fragment

import com.lmy.wanandroid.BR
import com.lmy.wanandroid.R
import com.lmy.base.BaseVMFragment
import com.lmy.wanandroid.databinding.FragmentNavigationBinding
import com.lmy.wanandroid.databinding.FragmentProjectBinding

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:47
 */
class NavigationFragment : BaseVMFragment<FragmentNavigationBinding, NavigationViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation
    }
    
    override fun initVariableId(): Int {
        return BR._all
    }
    
    override fun initData() {
    
    }
}