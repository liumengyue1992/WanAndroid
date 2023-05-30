package com.lmy.module_mine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.module_common.bean.ArticleDetail
import com.lmy.module_mine.R
import com.lmy.module_mine.bean.RankDetail
import com.lmy.module_mine.databinding.ItemRankBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/29
 * @description：
 */
class RankAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var diff = AsyncListDiffer(this, object : DiffUtil.ItemCallback<RankDetail>() {
        override fun areItemsTheSame(oldItem: RankDetail, newItem: RankDetail): Boolean {
            // 判断列表项是否是同一个item，常常使用item的id来判断
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: RankDetail, newItem: RankDetail): Boolean {
            // 判断列表项的内容是否相同
            return oldItem.nickname == newItem.nickname
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemRankBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rank,
            parent,
            false
        )
        return RankViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return diff.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RankViewHolder){
            holder.binding.tvRank.text = diff.currentList[position].rank.toString()
            holder.binding.tvName.text = diff.currentList[position].username
            holder.binding.tvCoinCount.text = diff.currentList[position].coinCount.toString()
        }
    }

    fun setData(rankDetails: List<RankDetail>) {
        // AsyncListDiffer需要一个新数据，不然添加无效
        diff.submitList(ArrayList(rankDetails))
    }

    class RankViewHolder(val binding: ItemRankBinding):RecyclerView.ViewHolder(binding.root)
}