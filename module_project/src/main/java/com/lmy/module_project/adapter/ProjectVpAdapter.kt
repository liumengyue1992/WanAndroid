package com.lmy.module_project.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
class ProjectVpAdapter(fragment: Fragment, private val fragmentList: MutableList<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
       return fragmentList[position]
    }
}