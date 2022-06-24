package com.lmy.androidutilcode.adapter

import com.lmy.androidutilcode.BR
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.base.BaseBindingAdapter
import com.lmy.androidutilcode.databinding.ItemHomeBinding

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/16 17:29
 */
class HomeAdapter(data: MutableList<String>) : BaseBindingAdapter<String, ItemHomeBinding>(R.layout.item_home, data) {
    override fun getItemVarId(): Int = BR.item
    override fun bindData(holder: DefaultViewHolder, position: Int, item: String) {
        holder.binding.tvTitle.setOnClickListener {
            itemClickListener?.invoke(position, item)
        }
    }
    
    // 声明变量，类型为一个函数
    private var itemClickListener: ((position: Int, item: String) -> Unit)? = null
    
    // 添加一个方法，参数为函数
    fun setOnItemClickListener(listener: ((position: Int, item: String) -> Unit)?) {
        itemClickListener = listener
    }
}