package com.lmy.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseVMFragment<V : ViewDataBinding> : BaseFragment<V>() {
    private var isFirstLoad: Boolean = true
    
    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            lazyLoad()
        }
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }
    
    
    open fun lazyLoad() {}
    
    abstract fun initObserver()
}
