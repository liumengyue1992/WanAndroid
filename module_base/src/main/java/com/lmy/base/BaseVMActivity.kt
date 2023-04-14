package com.lmy.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lmy.BaseApplication
import com.lmy.annotation.PageId
import com.lmy.uitl.ScreenAdaptationUtil
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
        ScreenAdaptationUtil.setDensityByWidth(this, BaseApplication.mContext)
        initViewModel()
        initDataBinding()
        initData()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
    
    protected fun initViewModel() {
        val modelClass: Class<BaseViewModel>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            // 如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }
        viewModel = ViewModelProvider(this)[modelClass] as VM
        viewModel.setCacheKey(getPageId())
    }
    
    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        // tip：dataBinding和viewModel进行绑定。参考：<README-关于dataBinding生成的BR文件.md>
        binding.setVariable(initVariableId(), viewModel)
        binding.lifecycleOwner = this
    }
    
    /**
     * 获取布局id
     * @return Int
     */
    abstract fun getLayoutId(): Int
    
    /**
     * 初始化ViewModel的id，可以在父类直接填充默认的BR.vm，但是为了子类可以自定义viewModel的名字(如首页我想在xml中引用的variable的name属性为homeViewModel)
     * @return Int
     */
    abstract fun initVariableId(): Int
    
    abstract fun initData()
}
