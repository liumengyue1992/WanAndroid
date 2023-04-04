package com.lmy.androidutilcode.ui.fragment

import com.lmy.androidutilcode.BR
import com.lmy.androidutilcode.MainViewModel
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.base.BaseVMFragment
import com.lmy.androidutilcode.databinding.FragmentHomeBinding

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


