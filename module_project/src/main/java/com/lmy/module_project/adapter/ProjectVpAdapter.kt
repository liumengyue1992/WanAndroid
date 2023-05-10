package com.lmy.module_project.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lmy.module_project.bean.ProjectType
import com.lmy.module_project.ui.ProjectListFragment

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
class ProjectVpAdapter(fragment: Fragment, private val projectTypes: List<ProjectType>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return projectTypes.size
    }

    override fun createFragment(position: Int): Fragment {
       return ProjectListFragment.newInstance(projectTypes[position].id)
    }
}