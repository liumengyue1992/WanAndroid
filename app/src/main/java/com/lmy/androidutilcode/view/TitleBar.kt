package com.lmy.androidutilcode.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.databinding.LayoutTitlebarBinding

/**
 * @Desc: 通用标题栏
 * @Author: Yan.Zhang
 * @Date: 2022/4/19
 * @Email: yan.zhang@upuphone.com
 */
class TitleBar(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    private val mBinding =
        DataBindingUtil.inflate<LayoutTitlebarBinding>(LayoutInflater.from(context),
            R.layout.layout_titlebar,
            this,
            true)
    
    // 标题
    private var mTitle: String? = null
    
    // 右侧图标
    private var mMenuIcon: Int? = null
    
    // 右侧图标是否可见
    private var mMenuIconVisible: Boolean = true
    
    private var mOnMenuListener: OnMenuListener? = null
    
    private var mIvBackListener: (() -> Unit)? = null
    
    init {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleBar)
        mTitle = typeArray.getString(R.styleable.TitleBar_title)
        mMenuIcon =
            typeArray.getResourceId(R.styleable.TitleBar_menuIcon, R.mipmap.ic_titlebar_setting)
        mMenuIconVisible = typeArray.getBoolean(R.styleable.TitleBar_menuIconVisible, true)
        typeArray.recycle()
        
        initView()
    }
    
    /**
     * 设置MenuIcon
     * @param icon Int?
     */
    fun setMenuIcon(icon: Int) {
        mBinding.ivMenu.setImageResource(icon)
    }
    
    /**
     * 设置MenuIcon是否可用
     * @param enable Boolean
     */
    fun setMenuIconEnable(enable: Boolean) {
        mBinding.ivMenu.isEnabled = enable
    }
    
    /**
     * MenuIcon是否可用
     * @return Boolean
     */
    fun isMenuIconEnable(): Boolean {
        return mBinding.ivMenu.isEnabled
    }
    
    private fun initView() {
        mBinding.ivBack.setOnClickListener {
            if (mIvBackListener == null) {
                (context as Activity)?.finish()
            } else {
                mIvBackListener?.invoke()
            }
        }
        
        mTitle?.let {
            mBinding.tvTitle.text = it
        }
        
        mMenuIcon?.let {
            mBinding.ivMenu.apply {
                setImageResource(it)
                setOnClickListener {
                    mOnMenuListener?.onMenuClick()
                }
            }
        }
        
        mBinding.ivMenu.visibility = if (mMenuIconVisible) VISIBLE else GONE
        
        /**
         * 设置标题
         */
        fun setTitle(@StringRes title: Int) {
            mBinding.tvTitle.text = resources.getString(title)
        }
        
        /**
         * 设置标题
         */
        fun setTitle(title: String) {
            mBinding.tvTitle.text = title
        }
    }
    
    /**
     * 右侧菜单点击事件
     */
    interface OnMenuListener {
        /**
         * 菜单点击回调
         */
        fun onMenuClick() {}
    }
    
    /**
     * 设置菜单点击事件
     * @param listener OnMenuListener
     */
    fun setOnMenuClickListener(listener: OnMenuListener) {
        mOnMenuListener = listener
    }
    
    /**
     * 返回
     * @param listener Function0<Unit>?
     */
    fun setClickBackListener(listener: (() -> Unit)? = null) {
        this.mIvBackListener = listener
    }
}
