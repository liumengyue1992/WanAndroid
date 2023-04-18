package com.lmy.module_navigation.ui

import com.lmy.base.BaseVMFragment
import com.lmy.module_navigation.databinding.FragmentNavigationBinding
import com.lmy.module_navigation.R
import com.lmy.module_navigation.BR
import com.lmy.module_navigation.viewmodel.NavigationViewModel
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