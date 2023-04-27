package com.lmy.module_home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.module_home.R
import com.lmy.module_home.bean.ArticleDetail

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/26 14:42
 */
class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var content = 0
    private var footer = 1
    private var noMoreData = 2
    
    // AsyncListDiffer是一个工具类，可以帮助我们异步更新RecyclerView列表
    private var diff = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ArticleDetail>() {
        override fun areItemsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean {
            // 判断列表项是否是同一个item，常常使用item的id来判断
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean {
            // 判断列表项的内容是否相同
            return oldItem.title == newItem.title && oldItem.niceDate == newItem.niceDate;
        }
    })
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            content -> ContentViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_content, parent, false))
            footer -> ContentViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_footer, parent, false))
            else -> NoMoreDataViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_nomoredata, parent, false))
        }
    }
    
    override fun getItemCount(): Int {
        return if (diff.currentList.size == 0) 1 else diff.currentList.size + 1
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
    
    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    }
    
    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    }
    
    class NoMoreDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    }
}