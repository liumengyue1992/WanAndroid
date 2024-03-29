package com.lmy.module_navigation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.base.BaseApplication
import com.lmy.module_common.bean.ArticleDetail
import com.lmy.module_navigation.R
import com.lmy.module_navigation.databinding.ItemSysArticleBinding

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
        val inflate = DataBindingUtil.inflate<ItemSysArticleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_sys_article,
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
            if (data.author.isNotEmpty()) {
                holder.binding.tvArticleAuthor.text =
                    BaseApplication.mContext.getString(R.string.author, data.author)
            } else if (data.shareUser.isNotEmpty()) {
                holder.binding.tvArticleAuthor.text =
                    BaseApplication.mContext.getString(R.string.shareUser, data.shareUser)
            }
            holder.binding.tvArticleDate.text = data.niceDate
            holder.binding.tvArticleTitle.text = data.title
            holder.binding.tvCategory.text = BaseApplication.mContext.getString(R.string.article_category,data.superChapterName,data.chapterName)

            holder.itemView.setOnClickListener {
                mOnItemClickListener?.invoke(diff.currentList[position])
            }
            if (data.collect){
                holder.binding.ivCollect.setImageResource(com.lmy.module_common.R.drawable.ic_like)
            }else{
                holder.binding.ivCollect.setImageResource(com.lmy.module_common.R.drawable.ic_like_not)
            }
            holder.binding.ivCollect.setOnClickListener {
                onClickCollectListener?.invoke(data.id,position)
            }
        }
    }
    private var onClickCollectListener: ((id: Int, position: Int) -> Unit)? = null
    fun setOnClickCollectListener(listener: (id: Int, position: Int) -> Unit) {
        this.onClickCollectListener = listener
    }

    // 条目点击事件
    private var mOnItemClickListener: ((articleDetail: ArticleDetail) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: (articleDetail: ArticleDetail) -> Unit) {
        this.mOnItemClickListener = onItemClickListener
    }


    fun setData(article: List<ArticleDetail>) {
        // AsyncListDiffer需要一个新数据，不然添加无效
        diff.submitList(ArrayList(article))
    }

    class ContentViewHolder(val binding: ItemSysArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
}