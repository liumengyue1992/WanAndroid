package com.lmy.module_navigation.ui.sys_list

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.lmy.base.BaseVMFragment
import com.lmy.constant.PATH_WEB
import com.lmy.constant.WEB_LINK
import com.lmy.constant.WEB_TITLE
import com.lmy.module_common.bean.ArticleDetail
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
    private var currentPage = 0
    private var id = -1
    private val articleList: MutableList<ArticleDetail> = arrayListOf()

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
        arguments?.let {
            id = it.getInt(key_id)
            navigationViewMode.getSysArticleList(id, currentPage)
        }
    }

    override fun initData() {
        binding.smartRefresh.setOnRefreshListener {
            currentPage = 0
            it.setEnableLoadMore(true)
            articleList.clear()
            navigationViewMode.getSysArticleList(id, currentPage)
        }
        binding.smartRefresh.setOnLoadMoreListener {
            currentPage++
            navigationViewMode.getSysArticleList(id, currentPage)
        }
        sysArticleAdapter = ArticleAdapter().apply {
            binding.rec.adapter = this
            setOnItemClickListener {
                ARouter.getInstance().build(PATH_WEB)
                    .withString(WEB_TITLE, it.title)
                    .withString(WEB_LINK, it.link)
                    .navigation()
            }
        }
    }

    override fun initObserver() {
        navigationViewMode.sysArticleList.observe(this) {
            if (it.over) {
                binding.smartRefresh.finishLoadMoreWithNoMoreData()
            } else {
                binding.smartRefresh.finishLoadMore()
            }
            if (binding.smartRefresh.isRefreshing) {
                binding.smartRefresh.finishRefresh()
            }
            if (it.datas.isNotEmpty()) {
                articleList.addAll(it.datas)
                sysArticleAdapter.setData(articleList)
            }
        }
    }
}