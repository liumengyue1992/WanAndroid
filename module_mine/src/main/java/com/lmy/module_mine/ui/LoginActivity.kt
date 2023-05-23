package com.lmy.module_mine.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.lmy.base.BaseVMActivity
import com.lmy.constant.PATH_LOGIN
import com.lmy.module_mine.R
import com.lmy.module_mine.databinding.ActivityLoginBinding
import com.lmy.module_mine.viewmodel.MineViewModel
import com.lmy.network.CACHE_USERNAME
import com.lmy.wanandroid.util.DataStoreUtil
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @author: mengyue.liu
 * @date: 2023/5/19
 * @description：
 */
@Route(path = PATH_LOGIN)
class LoginActivity : BaseVMActivity<ActivityLoginBinding>() {

    private val mineViewModel: MineViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        binding.btnLogin.setOnClickListener {
            val userName = "liumengyue1992"
            val passWord = "123456"
            mineViewModel.login(userName,passWord)
        }

        binding.tvRegister.setOnClickListener {

        }

    }

    override fun initObserver() {
        mineViewModel.loginBean.observe(this){
            DataStoreUtil.putData(CACHE_USERNAME,it.username)
            finish()
        }

    }

}