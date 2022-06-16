package com.lmy.androidutilcode.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseVMFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    
    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding()
        initData()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
    
    private fun initDataBinding() {
        createViewModel()
        binding.setVariable(initVariableId(), viewModel)
        binding.lifecycleOwner = this
    }
    
    protected fun createViewModel() {
        val modelClass: Class<BaseViewModel>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            // 如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }
        viewModel = ViewModelProvider(this).get(modelClass) as VM
    }
    
    abstract fun getLayoutId(): Int
    
    abstract fun initVariableId(): Int
    
    abstract fun initData()
    
    
}
