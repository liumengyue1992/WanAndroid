package com.lmy.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description 通用rv binding adapter
 *
 * @author lei.yang
 * @date 2022/6/15
 * @email lei.yang@upuphone.com
 */
abstract class BaseBindingAdapter<T, V : ViewDataBinding>(private val layoutId: Int, private val data: MutableList<T> = mutableListOf()) :
    RecyclerView.Adapter<BaseBindingAdapter<T, V>.DefaultViewHolder>() {
    
    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val binding =
            DataBindingUtil.inflate<V>(LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false)
        return DefaultViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        holder.binding.setVariable(getItemVarId(), data[position])
        bindData(holder, position, data[position])
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }
    
    override fun getItemCount(): Int {
        return data.size
    }
    
    /**
     * 获取binding变量id
     * @return Int
     */
    abstract fun getItemVarId(): Int
    
    /**
     * 设置数据
     * @param holder DefaultViewHolder<T, V>
     * @param position Int
     * @param item T
     */
    abstract fun bindData(holder: DefaultViewHolder, position: Int, item: T)
    
    /**
     * 默认ViewHolder
     * @property binding V
     * @constructor
     */
    inner class DefaultViewHolder(val binding: V) : RecyclerView.ViewHolder(binding.root)
    
    /**
     * 设置点击监听
     * @param listener OnItemClickListener
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
    
    /**
     * 点击事件
     */
    fun interface OnItemClickListener {
        /**
         * 点击回调
         * @param position Int
         */
        fun onItemClick(position: Int)
    }
}
