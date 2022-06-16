package com.lmy.androidutilcode

import com.lmy.androidutilcode.adapter.HomeAdapter
import com.lmy.androidutilcode.base.BaseVMActivity
import com.lmy.androidutilcode.databinding.ActivityMainBinding
import com.lmy.androidutilcode.util.SnapShotUtil

class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {
    
    override fun getLayoutId(): Int = R.layout.activity_main
    
    override fun initVariableId(): Int = BR.vm
    
    override fun initData() {
        binding.rv.adapter = HomeAdapter(mutableListOf("地区选择列表"))
    }
}

