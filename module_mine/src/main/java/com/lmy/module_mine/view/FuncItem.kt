package com.lmy.module_mine.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import com.lmy.ext.setGone
import com.lmy.module_mine.R
import com.lmy.module_mine.databinding.ItemFuncBinding


/**
 * @author: mengyue.liu
 * @date: 2023/5/19
 * @description：
 */
class FuncItem : LinearLayout {

    private lateinit var binding: ItemFuncBinding

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_func,
                this,
                true // 是否将inflate出来的View添加到父容器中。
            )
//        addView(binding.root)
    }

    fun initFuncItem(@DrawableRes icon: Int, title: String): FuncItem {
        binding.ivFunc.setImageResource(icon)
        binding.tvTitle.text = title

        setOnClickListener {
            onClickFuncItem?.invoke()
        }
        return this
    }

    fun updateDes(des: String) {
        binding.tvDes.setGone(true)
        binding.tvDes.text = des
    }

    private var onClickFuncItem: (() -> Unit)? = null

    fun addOnClickListener(listener: () -> Unit): FuncItem {
        onClickFuncItem = listener
        return this
    }


}