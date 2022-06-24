package com.lmy.androidutilcode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.bean.DistrictPhoneBean
import com.lmy.androidutilcode.bean.DistrictPhoneItemBean
import com.lmy.androidutilcode.databinding.LayoutDistrictPhoneItemBinding
import com.lmy.androidutilcode.view.LinearRecycleViewDivider

/**
 * @Description: TODO
 * @Author: liheng.cai
 * @Date: 2022/2/16 10:03
 * @Email: liheng.cai@upuphone.com
 */
class DistrictPhoneAdapter : RecyclerView.Adapter<DistrictPhoneViewHolder>() {
    
    var itemList: MutableList<DistrictPhoneBean>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictPhoneViewHolder {
        val binding: LayoutDistrictPhoneItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_district_phone_item,
            parent,
            false
        )
        return DistrictPhoneViewHolder(binding.root)
    }
    
    override fun onBindViewHolder(holder: DistrictPhoneViewHolder, position: Int) {
        val binding: LayoutDistrictPhoneItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        val context = binding?.root?.context
        context?.let {
            binding?.item = itemList?.get(position)
            binding?.districtPhoneInfoRv?.layoutManager = LinearLayoutManager(it)
            binding?.districtPhoneInfoRv?.addItemDecoration(LinearRecycleViewDivider(it, LinearLayoutManager.VERTICAL, it.resources.getDimensionPixelSize(R.dimen.district_phone_divider_height), ContextCompat.getColor(it, R.color.district_phone_divider_color)))
            binding?.districtPhoneInfoRv?.adapter = DistrictPhoneChildAdapter(itemList?.get(position)?.phoneAreaCodes).apply {
                onItemClickListener = this@DistrictPhoneAdapter.onItemClickListener
            }
            binding?.executePendingBindings()
        }
    }
    
    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }
    
    fun getFirstNameByLetter(letter: String): MutableList<String>? {
        return itemList?.find { it.initial == letter }?.firstNameList ?: null
    }
    
    fun getLocationByLetter(letter: String): Int {
        return itemList?.indexOfFirst { it.initial == letter } ?: 0
    }
    
    var onItemClickListener: ((DistrictPhoneItemBean?) -> Unit)? = null
}

class DistrictPhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)



