package com.lmy.module_home.adapter

import android.widget.ImageView
import coil.load
import com.lmy.module_home.bean.BannerBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import com.lmy.module_home.R

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/25 17:36
 */
class BannerAdapter: BaseBannerAdapter<BannerBean>() {
    override fun bindData(holder: BaseViewHolder<BannerBean>, data: BannerBean, position: Int, pageSize: Int) {
        holder.findViewById<ImageView>(R.id.banner_item).load(data.imagePath)
    }
    
    override fun getLayoutId(viewType: Int): Int{
        return R.layout.banner_item
    }
}