package com.lmy.module_mine.ui

import com.lmy.base.BaseVMActivity
import com.lmy.module_mine.R
import com.lmy.module_mine.adapter.RankAdapter
import com.lmy.module_mine.databinding.ActivityRankBinding
import com.lmy.module_mine.viewmodel.MineViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author: mengyue.liu
 * @date: 2023/5/29
 * @description：
 */
class RankActivity : BaseVMActivity<ActivityRankBinding>() {

    private val mineViewModel: MineViewModel by viewModel()
    private lateinit var rankAdapter: RankAdapter

    override fun getLayoutId(): Int = R.layout.activity_rank

    override fun initData() {
        rankAdapter = RankAdapter().apply {
            binding.recRank.adapter = this
        }
        mineViewModel.getRank()
    }

    override fun initObserver() {
        mineViewModel.rankBean.observe(this) {
            if (it != null && it.datas.isNotEmpty()) {
                rankAdapter.setData(it.datas)
            }
        }
    }


}