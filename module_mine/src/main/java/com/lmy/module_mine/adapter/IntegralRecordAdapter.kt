package com.lmy.module_mine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.module_mine.R
import com.lmy.module_mine.bean.IntegralListBean
import com.lmy.module_mine.databinding.ItemIntegralRecordBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/30
 * @description：
 */
class IntegralRecordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var diff = AsyncListDiffer(this, object : DiffUtil.ItemCallback<IntegralListBean>() {
        override fun areItemsTheSame(
            oldItem: IntegralListBean,
            newItem: IntegralListBean
        ): Boolean {
            // 判断列表项是否是同一个item，常常使用item的id来判断
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: IntegralListBean,
            newItem: IntegralListBean
        ): Boolean {
            // 判断列表项的内容是否相同
            return oldItem.date == newItem.date
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemIntegralRecordBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_integral_record,
            parent,
            false
        )
        return IntegralRecordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IntegralRecordViewHolder) {
            holder.binding.tvReason.text = diff.currentList[position].reason
            holder.binding.tvCoinCount.text = diff.currentList[position].coinCount.toString()
            holder.binding.tvDes.text = diff.currentList[position].desc
        }
    }


    fun setData(integralList: List<IntegralListBean>) {
        // AsyncListDiffer需要一个新数据，不然添加无效
        diff.submitList(ArrayList(integralList))
    }

    class IntegralRecordViewHolder(val binding: ItemIntegralRecordBinding) :
        RecyclerView.ViewHolder(binding.root)
}