package com.lmy.wanandroid.ui.fragment

import com.lmy.wanandroid.BR
import com.lmy.wanandroid.R
import com.lmy.base.BaseVMFragment
import com.lmy.wanandroid.databinding.FragmentMineBinding
import com.lmy.wanandroid.databinding.FragmentProjectBinding

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:47
 */
class MineFragment : BaseVMFragment<FragmentMineBinding, MineViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }
    
    override fun initVariableId(): Int {
        return BR._all
    }
    
    override fun initData() {
    
    }
}