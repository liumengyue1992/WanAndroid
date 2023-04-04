package com.lmy.wanandroid

import com.lmy.wanandroid.base.BaseVMActivity
import com.lmy.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {
    
    override fun getLayoutId(): Int = R.layout.activity_main
    
    override fun initVariableId(): Int {
        // tip：参考：<README-关于dataBinding生成的BR文件.md>
        return BR.vm
    }
    
    override fun initData() {
    
    }
}

