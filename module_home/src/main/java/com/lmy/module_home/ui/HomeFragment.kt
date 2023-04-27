package com.lmy.module_home.ui

import com.lmy.base.BaseVMFragment
import com.lmy.module_home.R
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
    
    override fun getLayoutId(): Int = R.layout.fragment_home
    
    override fun initData() {
        homeViewModel.getBannerData()
        homeViewModel.getHomeArticle()
        
        binding.banner.apply {
            setAdapter(BannerAdapter())
            registerLifecycleObserver(lifecycle)
        }.create()
        
        binding.homeRec.apply {
            adapter =
        }
    }
    
    override fun initObserver() {
        homeViewModel.bannerList.observe(this) {
            if (!it.isNullOrEmpty()) {
               binding.banner.refreshData(it)
            }
        }
    }
}


