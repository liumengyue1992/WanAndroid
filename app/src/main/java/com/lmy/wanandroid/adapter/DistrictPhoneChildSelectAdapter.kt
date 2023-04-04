package com.upuphone.account.ui.login.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.wanandroid.R
import com.lmy.wanandroid.databinding.LayoutDistrictPhoneChildSelectItemBinding

/**
 * @Description: TODO
 * @Author: liheng.cai
 * @Date: 2022/2/16 10:21
 * @Email: liheng.cai@upuphone.com
 */
class DistrictPhoneChildSelectAdapter : RecyclerView.Adapter<DistrictPhoneSelectChildViewHolder>() {

    var itemList: MutableList<String>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        this.itemList = itemList
    }

    var itemClickListener:((String)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictPhoneSelectChildViewHolder {
        val binding: LayoutDistrictPhoneChildSelectItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_district_phone_child_select_item, parent, false)
        return DistrictPhoneSelectChildViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DistrictPhoneSelectChildViewHolder, position: Int) {
        val binding: LayoutDistrictPhoneChildSelectItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        binding?.content = itemList?.get(position)
        binding?.itemRadius = binding?.root?.context?.resources?.getDimension(R.dimen.district_phone_select_item_radius)
        binding?.selectLetterItemTv?.setOnClickListener {
            itemClickListener?.invoke(binding?.content?:"")
        }
        binding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }
}

class DistrictPhoneSelectChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


