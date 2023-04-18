package com.lmy.module_home.ui

import coil.load
import com.lmy.module_home.R
import com.lmy.base.BaseVMFragment
import com.lmy.module_home.BR
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.lmy.module_home.bean.BannerBean
import com.lmy.module_home.databinding.FragmentHomeBinding
import com.lmy.module_home.viewmodel.HomeViewModel

/**
 * @description：Home
 * @author：mengyue.liu
 * @time： 2023/4/4 10:54
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {
    
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
    
    override fun initVariableId(): Int {
        return BR._all
    }
    override fun initData() {
        val bannerBeans = arrayListOf<BannerBean>()
        binding.banner.setAdapter(object : BannerImageAdapter<BannerBean>(bannerBeans) {
            override fun onBindView(holder: BannerImageHolder, data: BannerBean, position: Int, size: Int) {
                holder.imageView.load(data.imageUrl){
                    crossfade(true) // 渐进渐出
                    // placeholder(R.drawable.banner_place) // 加载中占位
                    // error(R.drawable.banner_error) // 错误占位
                }
            }
        })
    }
}


