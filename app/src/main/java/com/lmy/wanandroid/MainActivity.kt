package com.lmy.wanandroid

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.lmy.wanandroid.adapter.ViewPager2Adapter
import com.lmy.base.BaseVMActivity
import com.lmy.wanandroid.databinding.ActivityMainBinding
import com.lmy.wanandroid.ui.fragment.HomeFragment
import com.lmy.wanandroid.ui.fragment.MineFragment
import com.lmy.wanandroid.ui.fragment.NavigationFragment
import com.lmy.wanandroid.ui.fragment.ProjectFragment

class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {
    
    override fun getLayoutId(): Int = R.layout.activity_main
    
    override fun initVariableId(): Int {
        // tip：参考：<README-关于dataBinding生成的BR文件.md>
        return BR.vm
    }
    
    override fun initData() {
        // 去除自带的选中颜色,去除后文字和图片选择效果就是跟我们自定义的效果一样
        binding.bottomNav.itemIconTintList = null
        
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(HomeFragment())
        fragmentList.add(ProjectFragment())
        fragmentList.add(NavigationFragment())
        fragmentList.add(MineFragment())
        binding.viewPager2.adapter = ViewPager2Adapter(this, fragmentList)
        
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })
        
        binding.bottomNav.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.nav_home -> binding.viewPager2.currentItem = 0
                    
                    R.id.nav_project -> binding.viewPager2.currentItem = 1
                    
                    R.id.nav_navigation -> binding.viewPager2.currentItem = 2
                    
                    R.id.nav_mine -> binding.viewPager2.currentItem = 3
                    
                }
                return true
            }
        })
        
        // tips:【BottomNavigationView】自带小红点（角标）功能
    }
}

