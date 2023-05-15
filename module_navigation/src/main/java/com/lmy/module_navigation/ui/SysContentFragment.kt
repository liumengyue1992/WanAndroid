package com.lmy.module_navigation.ui

import com.lmy.base.BaseVMFragment
import com.lmy.module_navigation.R
import com.lmy.module_navigation.databinding.FragmentSysContentBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：体系
 */
class SysContentFragment:BaseVMFragment<FragmentSysContentBinding>() {

    override fun getLayoutId(): Int {
      return R.layout.fragment_sys_content
    }

    override fun initData() {

    }
    override fun initObserver() {
    }

}