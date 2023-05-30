package com.lmy.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.lmy.annotation.PageId

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseVMActivity<V : ViewDataBinding> : BaseActivity<V>() {
    
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
        initObserver()
    }
    
    override fun onResume() {
        super.onResume()
    }
    
    abstract fun initObserver()
}
