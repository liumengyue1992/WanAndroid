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
import com.lmy.module_home.databinding.ItemHomeFooterBinding
import com.lmy.module_home.databinding.ItemHomeNomoredataBinding

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
            content -> {
                val inflate = DataBindingUtil.inflate<ItemHomeContentBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_content,
                    parent,
                    false
                )
                ContentViewHolder(inflate)
            }

            footer -> {
                val inflate = DataBindingUtil.inflate<ItemHomeFooterBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_footer,
                    parent,
                    false
                )
                FooterViewHolder(inflate)
            }

            else -> {
                val inflate = DataBindingUtil.inflate<ItemHomeNomoredataBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_nomoredata,
                    parent,
                    false
                )
                NoMoreDataViewHolder(inflate)
            }
        }
    }

    override fun getItemCount(): Int {
//        return if (diff.currentList.size == 0) 1 else diff.currentList.size + 1
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

    class ContentViewHolder(val binding: ItemHomeContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class FooterViewHolder(val binding: ItemHomeFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class NoMoreDataViewHolder(val binding: ItemHomeNomoredataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}