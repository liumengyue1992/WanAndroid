package com.lmy.module_navigation.ui

import android.content.Intent
import com.lmy.base.BaseVMFragment
import com.lmy.module_navigation.R
import com.lmy.module_navigation.adapter.SysAdapter
import com.lmy.module_navigation.bean.SysChild
import com.lmy.module_navigation.databinding.FragmentSysContentBinding
import com.lmy.module_navigation.ui.sys_list.SysListActivity
import java.util.ArrayList

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：体系
 */
class SysContentFragment : BaseVMFragment<FragmentSysContentBinding>() {

    private lateinit var naviFragment: NavigationFragment
    private lateinit var sysAdapter: SysAdapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_sys_content
    }

    override fun initData() {
        naviFragment = parentFragment as NavigationFragment

        sysAdapter = SysAdapter().apply {
            binding.rec.adapter = this
            setOnClickItemListener { sysChildList: List<SysChild> ->
                val intent = Intent(context,SysListActivity::class.java)
//                val idList = sysChildList.map { it.id }
//                intent.putIntegerArrayListExtra(SysListActivity.key_ids, ArrayList(idList) )
                intent.putParcelableArrayListExtra(SysListActivity.key_ids, ArrayList(sysChildList) )
                startActivity(intent)
            }
        }
        naviFragment.navigationViewModel.getSysList()
    }

    override fun initObserver() {
        naviFragment.navigationViewModel.sysList.observe(this) {
            if (it.isNotEmpty()){
                sysAdapter.setData(it)
            }
        }
    }

}