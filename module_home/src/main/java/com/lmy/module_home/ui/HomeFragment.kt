package com.lmy.module_home.ui

import com.alibaba.android.arouter.launcher.ARouter
import com.lmy.base.BaseVMFragment
import com.lmy.constant.PATH_WEB
import com.lmy.constant.WEB_LINK
import com.lmy.constant.WEB_TITLE
import com.lmy.module_home.R
import com.lmy.module_home.adapter.ArticleAdapter
import com.lmy.module_home.adapter.BannerAdapter
import com.lmy.module_common.bean.ArticleDetail
import com.lmy.module_home.databinding.FragmentHomeBinding
import com.lmy.module_home.viewmodel.HomeViewModel
import com.lmy.uitl.LogUtil
import com.lmy.uitl.ToastUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @description：Home
 * @author：mengyue.liu
 * @time： 2023/4/4 10:54
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var articleAdapter: ArticleAdapter
    private var currentPage = 0
    private var articleList: MutableList<ArticleDetail> = arrayListOf()
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initData() {
        homeViewModel.getBannerData()
        homeViewModel.getHomeTopArticle()
        homeViewModel.getHomeArticle(currentPage)

        binding.smartRefresh.setOnRefreshListener {
            currentPage = 0
            it.setEnableLoadMore(true)
            articleList.clear()
            articleAdapter.setData(null)
            homeViewModel.getHomeArticle(currentPage)
            homeViewModel.getHomeTopArticle()
        }
        binding.smartRefresh.setOnLoadMoreListener {
            currentPage += 1
            homeViewModel.getHomeArticle(currentPage)
        }

        binding.banner.apply {
            setAdapter(BannerAdapter())
            registerLifecycleObserver(lifecycle)
        }.create()

        articleAdapter = ArticleAdapter().apply {
            binding.homeRec.adapter = this
            setOnItemClickListener {
                LogUtil.d("link = " + it.link)
                // 跳转并携带参数
                ARouter.getInstance().build(PATH_WEB)
                    .withString(WEB_LINK, it.link)
                    .withString(WEB_TITLE, it.title)
                    .navigation()
            }
            setOnClickCollectListener { id, position ->
                if (articleList[position].collect) {
                    homeViewModel.cancelCollect(id)
                } else {
                    homeViewModel.collect(id)
                }
                articleList[position].collect = !articleList[position].collect
                articleAdapter.notifyItemChanged(position)
            }
        }
    }

    override fun initObserver() {
        homeViewModel.bannerList.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.banner.refreshData(it)
            }
        }
        homeViewModel.homeArticle.observe(this) {
            updateRefreshUI(it.over)
            if (it.over) {
                binding.smartRefresh.setEnableLoadMore(false)
            }
            if (it.datas.isNotEmpty()) {
                LogUtil.d("currentPage = " + it.curPage + ", pageSize = " + it.size)
                articleList.addAll(it.datas)
                articleAdapter.setData(articleList)
            }
        }
        homeViewModel.homeTopArticle.observe(this) {
            if (!it.isNullOrEmpty()) {
                articleList.addAll(0, it)
                articleAdapter.setData(articleList)
            }
        }
        homeViewModel.collectResult.observe(this) {
            ToastUtil.showShort("已收藏文章")
        }
        homeViewModel.cancelCollectResult.observe(this) {
            ToastUtil.showShort("已取消收藏")
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


