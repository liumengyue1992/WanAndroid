package com.lmy.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/20 16:28
 */
abstract class BaseFragment<V : ViewDataBinding> : Fragment() {
    
    lateinit var binding: V
    protected lateinit var mContext: Context
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }
    
    
    abstract fun getLayoutId(): Int
    
    open fun initData(){}
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
   
}