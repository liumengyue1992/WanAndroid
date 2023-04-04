package com.lmy.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.wanandroid.R
import com.lmy.wanandroid.bean.DistrictPhoneItemBean
import com.lmy.wanandroid.databinding.LayoutDistrictPhoneChildItemBinding

/**
 * @Description: TODO
 * @Author: liheng.cai
 * @Date: 2022/2/16 10:21
 * @Email: liheng.cai@upuphone.com
 */
class DistrictPhoneChildAdapter(itemList: MutableList<DistrictPhoneItemBean>?) : RecyclerView.Adapter<DistrictPhoneChildViewHolder>() {

    var itemList: MutableList<DistrictPhoneItemBean>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictPhoneChildViewHolder {
        val binding: LayoutDistrictPhoneChildItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_district_phone_child_item, parent, false)
        return DistrictPhoneChildViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DistrictPhoneChildViewHolder, position: Int) {
        val binding: LayoutDistrictPhoneChildItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        binding?.item = itemList?.get(position)
        binding?.root?.setOnClickListener { onItemClickListener?.invoke(itemList?.get(position)) }
        binding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    var onItemClickListener:((DistrictPhoneItemBean?) -> Unit)? = null
}

class DistrictPhoneChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


