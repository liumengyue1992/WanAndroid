package com.lmy.module_navigation.ui.sys_list

import android.os.Bundle
import com.lmy.base.BaseVMFragment
import com.lmy.module_navigation.R
import com.lmy.module_navigation.adapter.ArticleAdapter
import com.lmy.module_navigation.databinding.FragmentSysListBinding
import com.lmy.module_navigation.viewmodel.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author: mengyue.liu
 * @date: 2023/5/17
 * @description：
 */
class SysListFragment : BaseVMFragment<FragmentSysListBinding>() {
    private val navigationViewMode: NavigationViewModel by viewModel()
    private lateinit var sysArticleAdapter: ArticleAdapter
    private val page = 0
    companion object {
        private const val key_id = "id"
        fun newInstance(id: Int): SysListFragment {
            val fragment = SysListFragment()
            val bundle = Bundle()
            bundle.putInt(key_id, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_sys_list
    }

    override fun lazyLoad() {
        val id = arguments?.getInt(key_id)
        id?.let {
            navigationViewMode.getSysArticleList(it,page)
        }
    }

    override fun initData() {
        sysArticleAdapter = ArticleAdapter().apply{
            binding.rec.adapter = this
            setOnItemClickListener {

            }
        }
    }

    override fun initObserver() {
        navigationViewMode.sysArticleList.observe(this){
            sysArticleAdapter.setData(it.datas)
        }
    }
}