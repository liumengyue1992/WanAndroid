package com.lmy.module_common.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.lmy.module_common.R
import com.lmy.module_common.databinding.TitlebarBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/19
 * @description：通用的TitleBar 根据需求可扩展
 */
class TitleBar(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private var binding: TitlebarBinding

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.titlebar,
            this,
            true
        )

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        val title = typeArray.getString(R.styleable.TitleBar_bar_title)
        val content = typeArray.getString(R.styleable.TitleBar_bar_content)
        val funcIcon = typeArray.getDrawable(R.styleable.TitleBar_bar_func_icon)
        typeArray.recycle()

        initView(title, content, funcIcon)

    }

    private fun initView(title: String?, content: String?, funcIcon: Drawable?) {
        binding.ivBack.setOnClickListener {
            (context as Activity).finish()
        }

        title?.let {
            binding.tvTitle.text = it
        }
        funcIcon?.let {
            binding.ivFunc.setImageDrawable(it)
            binding.ivFunc.setOnClickListener {
                onClickFuncListener?.invoke()
            }
        }
    }

    private var onClickFuncListener: (() -> Unit)? = null
    fun setOnClickFuncListener(listener: () -> Unit) {
        this.onClickFuncListener = listener
    }

    /**
     * 设置标题
     */
    fun setTitle(@StringRes title: Int) {
        binding.tvTitle.text = resources.getString(title)

        openMarquee()
    }

    /**
     *  TextView动态赋值时，需要在java代码中设置跑马灯效果才能生效
     */
    private fun openMarquee() {
        binding.tvTitle.isSingleLine = true
        binding.tvTitle.isSelected = true
        binding.tvTitle.isFocusable = true
        binding.tvTitle.isFocusableInTouchMode = true
    }


    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        binding.tvTitle.text = title

        openMarquee()
    }

}