package com.lmy.module_mine.ui

import android.app.AlertDialog
import android.content.Intent
import com.lmy.base.BaseVMFragment
import com.lmy.module_mine.R
import com.lmy.module_mine.databinding.FragmentMineBinding
import com.lmy.module_mine.view.FuncItem
import com.lmy.module_mine.viewmodel.MineViewModel
import com.lmy.network.CACHE_USERNAME
import com.lmy.uitl.ToastUtil
import com.lmy.wanandroid.util.DataStoreUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2023/4/13 18:47
 */
class MineFragment : BaseVMFragment<FragmentMineBinding>() {
    private val mineViewModel: MineViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun onResume() {
        super.onResume()
        // todo 改成事件总线相关方式实现
        DataStoreUtil.getData(CACHE_USERNAME, "").let {
            if (it.isNotEmpty()) {
                binding.tvUserName.text = it
                binding.tvUserName.isClickable = false
            } else {
                binding.tvUserName.text = getString(R.string.please_login)
                binding.tvUserName.isClickable = true
            }
        }
    }

    override fun initData() {
        context?.let {
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_rank,
                    getString(R.string.rank)
                ).addOnClickListener {

                })
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_integral,
                    getString(R.string.my_integral)
                ).addOnClickListener {

                })
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_collect,
                    getString(R.string.my_collect)
                ).addOnClickListener {

                })
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_open_project,
                    getString(R.string.open_project)
                ).addOnClickListener {

                })
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_about,
                    getString(R.string.about_author)
                ).addOnClickListener {

                })
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_clean_cache,
                    getString(R.string.clean_cache)
                ).addOnClickListener {

                })
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_current_version,
                    getString(R.string.current_version)
                ).addOnClickListener {

                })
            binding.llFunc.addView(
                FuncItem(it).initFuncItem(
                    R.drawable.ic_logout,
                    getString(R.string.logout)
                ).addOnClickListener {
                    if (DataStoreUtil.getData(CACHE_USERNAME, "").isNotEmpty()) {
                        showLogoutDialog()
                    } else {
                        ToastUtil.showShort("请先登录！")
                    }
                })
        }

        binding.tvUserName.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }

    }

    private fun showLogoutDialog() {
        val dialog = AlertDialog.Builder(context)
            .setMessage("确定要退出吗？")
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("确定") { dialog, _ ->
                mineViewModel.logout()
                dialog.dismiss()
                ToastUtil.showShort("退出成功！")
                binding.tvUserName.text = getString(R.string.please_login)
                binding.tvUserName.isClickable = true
            }
            .create()
        dialog.show()

        // 巨坑：某些手机厂商改了AOSP导致Dialog不居中显示（比如一直垂直居中靠右），可以用以下方式
        // 放在show()之后，不然有些属性是没有效果的，比如height和width
//        dialog.window?.run {
//            attributes = attributes.apply {
//                width = (windowManager.defaultDisplay.width * 0.95).toInt()
//                gravity = Gravity.CENTER
//            }
//        }
    }

    override fun initObserver() {

    }
}