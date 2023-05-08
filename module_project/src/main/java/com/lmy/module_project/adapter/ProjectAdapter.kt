package com.lmy.module_project.adapter

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
import com.lmy.uitl.LogUtil

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
            LogUtil.d("link=========================="+data.link)
            holder.binding.tvTitle.text = data.title
            holder.binding.tvDes.text = data.desc
            holder.binding.tvAuthor.text = data.author
            holder.binding.tvTime.text = data.niceDate
        }
    }

    fun setData(projectData:List<ProjectDetail>){
        diff.submitList(ArrayList(projectData))
    }


    class ProjectViewHolder(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)
}