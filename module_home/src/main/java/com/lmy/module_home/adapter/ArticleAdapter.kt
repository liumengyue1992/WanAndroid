package com.lmy.module_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.BaseApplication
import com.lmy.ext.setVisible
import com.lmy.module_home.R
import com.lmy.module_home.bean.ArticleDetail
import com.lmy.module_home.databinding.ItemHomeContentBinding

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/26 14:42
 */
class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        val inflate = DataBindingUtil.inflate<ItemHomeContentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_home_content,
            parent,
            false
        )
        return ContentViewHolder(inflate)
    }


    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            val data = diff.currentList[position]
//            holder.binding.tvArticleTop.visibility = data.
            holder.binding.tvArticleFresh.setVisible(data.fresh)
            holder.binding.tvArticleTag.setVisible(data.tags.isNotEmpty())
            if (data.tags.isNotEmpty()) {
                holder.binding.tvArticleTag.text = data.tags[0].name
            }
            if (data.author.isNotEmpty()) {
                holder.binding.tvArticleAuthor.text =
                    BaseApplication.mContext.getString(R.string.author, data.author)
            } else if (data.shareUser.isNotEmpty()) {
                holder.binding.tvArticleAuthor.text =
                    BaseApplication.mContext.getString(R.string.shareUser, data.shareUser)
            }
            holder.binding.tvArticleDate.text = data.niceDate
            holder.binding.tvArticleTitle.text = data.title
            holder.binding.tvCategory.text = data.superChapterName + "/" + data.chapterName
        }
    }

    fun setData(article: List<ArticleDetail>) {
        // AsyncListDiffer需要一个新数据，不然添加无效
        diff.submitList(ArrayList(article))
    }

    class ContentViewHolder(val binding: ItemHomeContentBinding) : RecyclerView.ViewHolder(binding.root)
}