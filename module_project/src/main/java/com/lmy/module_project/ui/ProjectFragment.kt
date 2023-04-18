package com.lmy.module_project.ui


import com.lmy.base.BaseVMFragment
import com.lmy.module_project.viewmodel.ProjectViewModel
import com.lmy.module_project.databinding.FragmentProjectBinding
import com.lmy.module_project.R
import com.lmy.module_project.BR
/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:47
 */
class ProjectFragment : BaseVMFragment<FragmentProjectBinding, ProjectViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }
    
    override fun initVariableId(): Int {
        return BR._all
    }
    
    override fun initData() {
    
    }
}