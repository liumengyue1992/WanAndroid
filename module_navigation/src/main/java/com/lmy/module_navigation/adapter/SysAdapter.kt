package com.lmy.module_navigation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.ext.setGone
import com.lmy.ext.setVisible
import com.lmy.module_navigation.R
import com.lmy.module_navigation.bean.Sys
import com.lmy.module_navigation.bean.SysChild
import com.lmy.module_navigation.databinding.ItemSysListBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
class SysAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diff = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Sys>() {
        override fun areItemsTheSame(oldItem: Sys, newItem: Sys): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sys, newItem: Sys): Boolean {
            return oldItem.name == newItem.name
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = DataBindingUtil.inflate<ItemSysListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_sys_list,
            parent,
            false
        )
        return SysViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SysViewHolder) {
            holder.binding.tvTitle.text = diff.currentList[position].name
            val sysChildList = diff.currentList[position].children
            if (sysChildList.isNotEmpty()) {
                holder.binding.flex.removeAllViews()
                sysChildList.forEachIndexed { index, naviChild ->
                    val flex = LayoutInflater.from(holder.itemView.context)
                        .inflate(R.layout.item_flex_sys, null, false)
                    val tvTitle = flex.findViewById<TextView>(R.id.tv_title)
                    tvTitle.text = naviChild.name

                    holder.binding.flex.addView(flex)
                }
            }
            holder.binding.viewLine.setVisible(position != diff.currentList.size - 1)

            holder.itemView.setOnClickListener {
                onClickItemListener?.invoke(diff.currentList[position])
            }
        }
    }

    private var onClickItemListener: ((sys:Sys) -> Unit)? = null

    fun setOnClickItemListener(listener: (sys:Sys) -> Unit) {
        this.onClickItemListener = listener
    }

    fun setData(sysList: List<Sys>) {
        diff.submitList(ArrayList(sysList))
    }

    class SysViewHolder(val binding: ItemSysListBinding) : RecyclerView.ViewHolder(binding.root)
}