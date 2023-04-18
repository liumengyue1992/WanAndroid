package com.lmy.module_mine.ui

import com.lmy.base.BaseVMFragment
import com.lmy.module_mine.databinding.FragmentMineBinding
import com.lmy.module_mine.viewmodel.MineViewModel
import com.lmy.module_mine.R
import com.lmy.module_mine.BR
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