package com.lmy.module_navigation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.lmy.base.BaseVMFragment
import com.lmy.module_common.PATH_WEB
import com.lmy.module_common.WEB_LINK
import com.lmy.module_common.WEB_TITLE
import com.lmy.module_navigation.R
import com.lmy.module_navigation.adapter.NaviAdapter
import com.lmy.module_navigation.bean.Navi
import com.lmy.module_navigation.databinding.FragmentNaviContentBinding

/**
 * @author: mengyue.liu
 * @date: 2023/5/15
 * @description：导航
 */
class NaviContentFragment : BaseVMFragment<FragmentNaviContentBinding>() {
    private lateinit var navigationFragment: NavigationFragment
    private lateinit var naviAdapter: NaviAdapter
    override fun getLayoutId(): Int {
        return R.layout.fragment_navi_content
    }

    override fun initData() {
        navigationFragment = parentFragment as NavigationFragment

        naviAdapter = NaviAdapter().apply {
            binding.recyclerView.adapter = this
            setOnClickFlexListener { title, link ->
                ARouter.getInstance().build(PATH_WEB)
                    .withString(WEB_TITLE, title)
                    .withString(WEB_LINK, link)
                    .navigation()
            }
        }

        navigationFragment.navigationViewModel.getNaviList()
    }

    override fun initObserver() {
        navigationFragment.navigationViewModel.naviList.observe(this) {
            addView(it)
            if (!it.isNullOrEmpty()) {
                naviAdapter.setData(it)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun addView(navis: List<Navi>) {
        navis.forEachIndexed { index, navi ->
            val view = LayoutInflater.from(context).inflate(R.layout.item_navi, null, false)
            val tvTitle = view.findViewById<TextView>(R.id.tv_title)
            tvTitle.text = navi.name
            tvTitle.setOnClickListener {
                // binding.recyclerView.scrollToPosition(index)
                // 需要指定的条目滚动到顶部位置
                (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    index,
                    0
                )
                updateSelectItemBg(index, tvTitle)
            }
            // 默认选中第一个
            if (index == 0) {
                updateSelectItemBg(0, tvTitle)
            }

            binding.llCategory.addView(view)
        }
    }

    private var oldPosition = -1
    private var oldTvTitle: TextView? = null

    /**
     * 更新选中项背景
     */
    private fun updateSelectItemBg(position: Int, tvTitle: TextView) {
        if (position == oldPosition) {
            return
        }
        tvTitle.background = ResourcesCompat.getDrawable(resources, R.drawable.navi_bg_grey, null)
        oldTvTitle?.background =
            ResourcesCompat.getDrawable(resources, R.drawable.navi_bg_white, null)
        oldPosition = position
        oldTvTitle = tvTitle
    }
}