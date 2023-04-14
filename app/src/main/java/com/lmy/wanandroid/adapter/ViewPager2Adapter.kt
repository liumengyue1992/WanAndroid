package com.lmy.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:56
 */
class ViewPager2Adapter(fa: FragmentActivity, private val fragmentList: MutableList<Fragment>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }
    
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}