package com.lmy.module_project.ui


import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lmy.base.BaseVMFragment
import com.lmy.module_project.R
import com.lmy.module_project.adapter.ProjectVpAdapter
import com.lmy.module_project.bean.ProjectType
import com.lmy.module_project.databinding.FragmentProjectBinding
import com.lmy.module_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:47
 */
class ProjectFragment : BaseVMFragment<FragmentProjectBinding>() {

    private val projectViewModel: ProjectViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {
        projectViewModel.getProjectCategory()
    }


    override fun initObserver() {
        projectViewModel.projectType.observe(this) {
            initTabLayout(it)
        }
    }

    private fun initTabLayout(projectTypes: List<ProjectType>) {
        val childFragmentList: MutableList<Fragment> = arrayListOf()

        projectTypes.forEachIndexed { _, projectType ->
            childFragmentList.add(ProjectListFragment.newInstance(projectType.id))
        }
        binding.viewPager.adapter = ProjectVpAdapter(this, childFragmentList)

        // 官方为我们提供了tabLayout+viewpager2的联动工具类：TabLayoutMediator
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // tab包含特殊符号  日历&amp;时钟  音视频&amp;相机
            if (projectTypes[position].name.contains("&amp;", true)) {
                tab.text = projectTypes[position].name.replace("&amp;", "&")
            } else {
                tab.text = projectTypes[position].name
            }
        }.attach()
    }
}