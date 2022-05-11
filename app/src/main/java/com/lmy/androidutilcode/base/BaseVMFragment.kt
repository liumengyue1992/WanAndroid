package com.lmy.androidutilcode.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseVMFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(), BaseBinding<V> {
    protected lateinit var binding: V
        private set
    protected lateinit var viewModel: VM
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = getViewBinding(layoutInflater, container)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        binding.setVariable(initVariableId(), viewModel)
        binding.lifecycleOwner = this
        binding.initView()
        binding.initData()
    }
    
    abstract fun initVariableId(): Int
    
    private fun createViewModel() {
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
    
    private fun <VB : ViewDataBinding> Any.getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
        val inflate = vbClass[0].getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        return inflate.invoke(null, inflater, container, false) as VB
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // 取消绑定
        if (::binding.isInitialized) {
            binding.unbind()
        }
    }
}