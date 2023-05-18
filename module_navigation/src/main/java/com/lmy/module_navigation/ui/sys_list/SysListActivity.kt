package com.lmy.module_navigation.ui.sys_list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lmy.base.BaseVMActivity
import com.lmy.module_navigation.R
import com.lmy.module_navigation.bean.Sys
import com.lmy.module_navigation.bean.SysChild
import com.lmy.module_navigation.databinding.ActivitySysListBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/17
 * @description：体系下的文章数据
 */
class SysListActivity : BaseVMActivity<ActivitySysListBinding>() {

    companion object {
        const val key_sys = "sys"
    }

    override fun getLayoutId(): Int = R.layout.activity_sys_list

    override fun initData() {
        binding.ivBack.setOnClickListener { finish() }

        val sys = intent?.getParcelableExtra<Sys>(key_sys)
        binding.tvTitle.text = sys?.name
        val sysChildList = sys?.children
        if (sysChildList.isNullOrEmpty()) return
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return sysChildList.size
            }

            override fun createFragment(position: Int): Fragment {
                return SysListFragment.newInstance(
                    sysChildList[position].id
                )
            }
        }
        binding.viewPager.offscreenPageLimit = sysChildList.size - 1

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager, true
        ) { tab, position ->
            tab.text = sysChildList[position].name
        }.attach()
    }

    override fun initObserver() {
    }


}