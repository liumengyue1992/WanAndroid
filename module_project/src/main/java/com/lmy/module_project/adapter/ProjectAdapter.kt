package com.lmy.module_project.adapter

import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lmy.module_project.R
import com.lmy.module_project.bean.ProjectDetail
import com.lmy.module_project.databinding.ItemProjectBinding
import okio.blackholeSink

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
class ProjectAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var diff = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ProjectDetail>() {
        override fun areItemsTheSame(oldItem: ProjectDetail, newItem: ProjectDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProjectDetail, newItem: ProjectDetail): Boolean {
            return oldItem.title == newItem.title && oldItem.niceDate == newItem.niceDate;
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemProjectBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_project,
            parent,
            false
        )
        return ProjectViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProjectViewHolder) {
            val data = diff.currentList[position]
            holder.binding.ivProject.load(data.envelopePic)
            holder.binding.tvTitle.text = data.title
            holder.binding.tvDes.text = data.desc
            holder.binding.tvAuthor.text = data.author
            holder.binding.tvTime.text = data.niceDate
            holder.itemView.setOnClickListener {
                onClickProjectItem?.invoke(data.link, data.title)
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


    private var onClickProjectItem: ((link: String, title: String) -> Unit)? = null

    fun setOnClickProjectItem(onClickProjectItemListener: (link: String, title: String) -> Unit) {
        this.onClickProjectItem = onClickProjectItemListener
    }

    fun setData(projectData: List<ProjectDetail>) {
        diff.submitList(ArrayList(projectData))
    }

    class ProjectViewHolder(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)
}