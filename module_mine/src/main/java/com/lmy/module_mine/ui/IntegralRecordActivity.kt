package com.lmy.module_mine.ui

import com.alibaba.android.arouter.launcher.ARouter
import com.lmy.base.BaseVMActivity
import com.lmy.constant.PATH_WEB
import com.lmy.constant.WEB_LINK
import com.lmy.constant.WEB_TITLE
import com.lmy.module_mine.R
import com.lmy.module_mine.adapter.IntegralRecordAdapter
import com.lmy.module_mine.bean.IntegralListBean
import com.lmy.module_mine.databinding.ActivityIntegralRecordBinding
import com.lmy.module_mine.viewmodel.MineViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author: mengyue.liu
 * @date: 2023/5/30
 * @description：个人积分记录
 */
class IntegralRecordActivity : BaseVMActivity<ActivityIntegralRecordBinding>() {
    private val mineViewModel: MineViewModel by viewModel()
    private var pageNo = 1
    private lateinit var integralRecordAdapter: IntegralRecordAdapter
    private var integralRecordList: MutableList<IntegralListBean> = arrayListOf()

    override fun getLayoutId(): Int = R.layout.activity_integral_record

    override fun initData() {
        integralRecordAdapter = IntegralRecordAdapter().apply {
            binding.recIntegral.adapter = this
        }
        binding.smartRefresh.setOnRefreshListener {
            pageNo = 1
            it.setEnableLoadMore(true)
            integralRecordList.clear()
            mineViewModel.getIntegralRecord(pageNo)
        }
        binding.smartRefresh.setOnLoadMoreListener {
            pageNo += 1
            mineViewModel.getIntegralRecord(pageNo)
        }
        binding.titleBar.setOnClickFuncListener {
            ARouter.getInstance().build(PATH_WEB)
                .withString(WEB_TITLE,"积分规则")
                .withString(WEB_LINK,"https://www.wanandroid.com/blog/show/2653")
                .navigation()
        }
        mineViewModel.getIntegralRecord(pageNo)
    }

    override fun initObserver() {
        mineViewModel.integralRecordList.observe(this) {
            updateRefreshUI(it.over)
            if (it != null && it.datas.isNotEmpty()) {
                integralRecordList.addAll(it.datas)
                integralRecordAdapter.setData(integralRecordList)
            }
        }
    }

    private fun updateRefreshUI(over: Boolean) {
        binding.smartRefresh.apply {
            if (this.isRefreshing) {
                this.finishRefresh()
            }
            if (over) {
                this.finishLoadMoreWithNoMoreData()
            } else if (this.isLoading) {
                this.finishLoadMore()
            }
        }
    }
}