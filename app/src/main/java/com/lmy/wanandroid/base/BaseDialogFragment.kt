package com.lmy.wanandroid.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lmy.wanandroid.R

/**
 * @description：
 * @author：Mengyue.Liu
 * @time： 2022/5/10 11:26
 */
abstract class BaseDialogFragment<V : ViewDataBinding> : DialogFragment() {
    protected lateinit var binding: V
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFragmentStyle);
    }
    
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
    
    override fun onStart() {
        super.onStart()
        dialog?.window?.attributes?.gravity = Gravity.BOTTOM
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.dimAmount = 0.15f
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        //dialog?.setCancelable(false)
    }
    
    private fun initDataBinding() {
        binding.lifecycleOwner = this
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
    
    override fun show(manager: FragmentManager, tag: String?) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commitAllowingStateLoss()
            super.show(manager, tag)
        } catch (e: Exception) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace();
        }
    }
    
    abstract fun getLayoutId(): Int
    
    abstract fun initData()
}