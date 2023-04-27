package com.lmy.module_home.ui

import com.lmy.base.BaseVMFragment
import com.lmy.module_home.R
import com.lmy.module_home.adapter.ArticleAdapter
import com.lmy.module_home.adapter.BannerAdapter
import com.lmy.module_home.databinding.FragmentHomeBinding
import com.lmy.module_home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @description：Home
 * @author：mengyue.liu
 * @time： 2023/4/4 10:54
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var articleAdapter: ArticleAdapter
    private val REFRESHTIME = 1000
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initData() {
        homeViewModel.getBannerData()
        homeViewModel.getHomeArticle()

        binding.smartRefresh.setOnRefreshListener {
            // todo 刷新数据
            it.finishRefresh(REFRESHTIME)
        }

        binding.banner.apply {
            setAdapter(BannerAdapter())
            registerLifecycleObserver(lifecycle)
        }.create()

        articleAdapter = ArticleAdapter()
        binding.homeRec.adapter = articleAdapter
    }

    override fun initObserver() {
        homeViewModel.bannerList.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.banner.refreshData(it)
            }
        }
        homeViewModel.homeArticle.observe(this) {
            if (it.datas.isNotEmpty()) {
                articleAdapter.setData(it.datas)
            }
        }
    }
}


