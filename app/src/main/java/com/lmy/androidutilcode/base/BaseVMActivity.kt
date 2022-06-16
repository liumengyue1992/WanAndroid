package com.lmy.androidutilcode.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lmy.BaseApplication
import com.lmy.androidutilcode.util.ScreenAdaptationUtil
import com.upuphone.cloudservice.annotation.PageId
import java.lang.reflect.ParameterizedType

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseVMActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    
    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    
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
        ScreenAdaptationUtil.setDensityByWidth(this, BaseApplication.instance)
        initDataBinding()
        initData()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
    
    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
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
        viewModel.setCacheKey(getPageId())
    }
    
    /**
     * 获取布局id
     * @return Int
     */
    abstract fun getLayoutId(): Int
    
    /**
     * 初始化ViewModel的id
     * @return Int
     */
    abstract fun initVariableId(): Int
    
    abstract fun initData()
}
