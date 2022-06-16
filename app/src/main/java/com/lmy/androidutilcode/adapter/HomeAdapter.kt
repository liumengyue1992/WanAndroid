package com.lmy.androidutilcode.adapter

import com.lmy.androidutilcode.BR
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.base.BaseBindingAdapter
import com.lmy.androidutilcode.databinding.ItemHomeBinding
import com.lmy.uitl.ToastUtil

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/16 17:29
 */
class HomeAdapter(data: MutableList<String>) : BaseBindingAdapter<String, ItemHomeBinding>(R.layout.item_home, data) {
    override fun getItemVarId(): Int = BR.item
    override fun bindData(holder: DefaultViewHolder, position: Int, item: String) {
        holder.binding.tvTitle.setOnClickListener {
            ToastUtil.showShort("点击了")
        }
    }
}