package com.lmy.module_navigation.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lmy.base.BaseVMFragment
import com.lmy.module_navigation.R
import com.lmy.module_navigation.databinding.FragmentNavigationBinding
import com.lmy.module_navigation.viewmodel.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:47
 */
class NavigationFragment : BaseVMFragment<FragmentNavigationBinding>() {

    val navigationViewModel: NavigationViewModel by viewModel()

    private val naviTitleList = mutableListOf("导航", "体系")

    override fun initObserver() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation
    }


    override fun initData() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return naviTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return if (position == 0) {
                    NaviContentFragment()
                } else {
                    SysContentFragment()
                }
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, position ->
            tab.text = naviTitleList[position]
        }.attach()
    }
}