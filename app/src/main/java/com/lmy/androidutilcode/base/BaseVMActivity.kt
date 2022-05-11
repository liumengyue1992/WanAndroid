package com.lmy.androidutilcode.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseVMActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), BaseBinding<V> {
    
    protected lateinit var viewModel: VM
    
    protected val binding: V by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createViewModel()
        binding.setVariable(initVariableId(), viewModel)
        binding.lifecycleOwner = this
        binding.initView()
        binding.initData()
    }
    
    // 初始化viewModelId
    abstract fun initVariableId(): Int
    
    // 创建viewModel
    private fun createViewModel() {
        val modelClass: Class<BaseViewModel>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            BaseViewModel::class.java
        }
        viewModel = ViewModelProvider(this).get(modelClass) as VM
    }
    
    // 通过反射获取ViewDataBinding
    private fun <V : ViewDataBinding> Any.getViewBinding(inflater: LayoutInflater): V {
        // 拿到想要的ViewDataBinding
        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<V>>()
        // 拿到inflate方法
        val inflate = vbClass[0].getDeclaredMethod("inflate", LayoutInflater::class.java)
        // 调用ViewDataBinding的inflate方法获取对应的ViewDataBinding对象
        return inflate.invoke(null, inflater) as V
    }
    
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}