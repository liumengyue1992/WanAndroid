package com.lmy.wanandroid

import androidx.fragment.app.Fragment
import com.lmy.base.BaseActivity
import com.lmy.module_home.ext.clearLongClickToast
import com.lmy.module_home.ui.HomeFragment
import com.lmy.module_mine.ui.MineFragment
import com.lmy.module_navigation.ui.NavigationFragment
import com.lmy.module_project.ui.ProjectFragment
import com.lmy.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val bottomTitleList = arrayListOf("首页", "项目", "导航", "我的")

    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val projectFragment: ProjectFragment by lazy { ProjectFragment() }
    private val navigationFragment: NavigationFragment by lazy { NavigationFragment() }
    private val mineFragment: MineFragment by lazy { MineFragment() }

    private var mCurrentFragment: Fragment? = null
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        switchFragment(homeFragment)

        // 去除自带的选中颜色,去除后文字和图片选择效果就是跟我们自定义的效果一样
        binding.bottomNav.itemIconTintList = null

        binding.bottomNav.clearLongClickToast(
            mutableListOf(
                R.id.nav_home,
                R.id.nav_project,
                R.id.nav_navigation,
                R.id.nav_mine
            )
        )
        binding.bottomNav.setOnItemSelectedListener { item ->
            var index = 0
            when (item.itemId) {
                R.id.nav_home -> {
                    index = 0
                    switchFragment(homeFragment)
                }

                R.id.nav_project -> {
                    index = 1
                    switchFragment(projectFragment)
                }

                R.id.nav_navigation -> {
                    index = 2
                    switchFragment(navigationFragment)
                }

                R.id.nav_mine -> {
                    index = 3
                    switchFragment(mineFragment)
                }
            }
            binding.tvTitle.text = bottomTitleList[index]
            true
        }
        // tips:【BottomNavigationView】自带小红点（角标）功能
    }

    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mCurrentFragment) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            mCurrentFragment?.let { fragmentTransaction.hide(it) }
            if (!fragment.isAdded) {
                // 存入Tag,以便获取，解决界面重叠问题 参考:http://blog.csdn.net/showdy/article/details/50825800
                fragmentTransaction.add(
                    R.id.fl_container,
                    fragment,
                    fragment.javaClass.simpleName
                ).show(fragment)
            } else {
                fragmentTransaction.show(fragment)
            }
            fragmentTransaction.commitAllowingStateLoss()
            mCurrentFragment = fragment
        }
    }
}

