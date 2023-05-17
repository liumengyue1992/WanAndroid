package com.lmy.module_navigation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.module_navigation.R
import com.lmy.module_navigation.bean.Navi
import com.lmy.module_navigation.databinding.ItemNaviListBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：
 */
class NaviAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diff = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Navi>() {
        override fun areItemsTheSame(oldItem: Navi, newItem: Navi): Boolean {
            return oldItem.cid == newItem.cid
        }

        override fun areContentsTheSame(oldItem: Navi, newItem: Navi): Boolean {
            return oldItem.name == newItem.name
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = DataBindingUtil.inflate<ItemNaviListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_navi_list,
            parent,
            false
        )
        return NaviViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NaviViewHolder) {
            holder.binding.tvTitle.text = diff.currentList[position].name
            val articles = diff.currentList[position].articles
            if (articles.isNotEmpty()) {
                holder.binding.flex.removeAllViews()
                articles.forEachIndexed { index, naviChild ->
                    val flex = LayoutInflater.from(holder.itemView.context)
                        .inflate(R.layout.item_flex_navi, null, false)
                    val tvTitle = flex.findViewById<TextView>(R.id.tv_title)
                    tvTitle.text = naviChild.title

                    flex.setOnClickListener {
                        onClickFlexListener?.invoke(naviChild.title,naviChild.link)
                    }
                    holder.binding.flex.addView(flex)
                }
            }
        }
    }

    private var onClickFlexListener: ((title:String, link:String) -> Unit)? = null

    fun setOnClickFlexListener(listener:(title:String,link:String)->Unit){
        this.onClickFlexListener = listener
    }

    fun setData(naviList: List<Navi>) {
        diff.submitList(ArrayList(naviList))
    }

    class NaviViewHolder(val binding: ItemNaviListBinding) : RecyclerView.ViewHolder(binding.root)
}