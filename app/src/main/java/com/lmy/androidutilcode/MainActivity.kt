package com.lmy.androidutilcode

import android.content.Intent
import com.lmy.androidutilcode.adapter.HomeAdapter
import com.lmy.androidutilcode.base.BaseVMActivity
import com.lmy.androidutilcode.databinding.ActivityMainBinding
import com.lmy.androidutilcode.ui.districtphonenum.DistrictPhoneNumberActivity
import com.lmy.androidutilcode.view.ContactsSyncView.Companion.STATE_SYNCING_FIRST
import com.lmy.androidutilcode.view.ContactsSyncView.Companion.STATE_SYNC_NOT

class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {
    
    override fun getLayoutId(): Int = R.layout.activity_main
    
    override fun initVariableId(): Int = BR.vm
    
    override fun initData() {
        binding.rv.adapter = HomeAdapter(mutableListOf("地区选择列表")).apply {
            setOnItemClickListener { position, item ->
                when (position) {
                    0 -> startActivity(Intent(this@MainActivity, DistrictPhoneNumberActivity::class.java))
                }
                
            }
        }
        binding.cs.setSyncState(STATE_SYNC_NOT)
        
        binding.tvStart.setOnClickListener {
            binding.cs.setSyncState(STATE_SYNCING_FIRST)
        }
    }
}

