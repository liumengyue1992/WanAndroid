package com.lmy.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lmy.BaseApplication
import com.lmy.annotation.PageId
import com.lmy.uitl.ScreenAdaptationUtil

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {
    
    protected lateinit var binding: V
    
    /**
     * 获取页面唯一标记ID，用于做页面数据缓存
     */
    private fun getPageId(): String {
        val cls: Class<*> = this.javaClass
        if (cls.isAnnotationPresent(PageId::class.java)) {
            val annotation: PageId = cls.getAnnotation(PageId::class.java)
            return annotation.value
        }
        return cls.simpleName
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 屏幕适配
        ScreenAdaptationUtil.setDensityByWidth(this, BaseApplication.mContext)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        initData()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
    
    /**
     * 获取布局id
     * @return Int
     */
    abstract fun getLayoutId(): Int
    
    open fun initData(){}
}
