package com.lmy.module_project.ui

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.lmy.base.BaseVMFragment
import com.lmy.constant.PATH_WEB
import com.lmy.constant.WEB_LINK
import com.lmy.constant.WEB_TITLE
import com.lmy.module_project.R
import com.lmy.module_project.adapter.ProjectAdapter
import com.lmy.module_project.bean.ProjectDetail
import com.lmy.module_project.databinding.FragmentProjectListBinding
import com.lmy.module_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
class ProjectListFragment : BaseVMFragment<FragmentProjectListBinding>() {
    private val tag = ProjectListFragment::class.java.simpleName
    private val projectViewModel: ProjectViewModel by viewModel()
    private var currentPage = 1
    private lateinit var projectAdapter: ProjectAdapter
    private var cid: Int = 0
    private var projectDetails = mutableListOf<ProjectDetail>()

    override fun getLayoutId(): Int = R.layout.fragment_project_list

    companion object {
        private const val KEY_CID = "cid"
        fun newInstance(cid: Int): ProjectListFragment {
            val fragment = ProjectListFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun lazyLoad() {
        arguments.let {
            cid = it?.getInt(KEY_CID) ?: 0
            projectViewModel.getProjectList(currentPage, cid = cid)
        }
    }

    override fun initData() {
        projectAdapter = ProjectAdapter().apply {
            binding.projectRec.adapter = this
            setOnClickProjectItem { link, title ->
                ARouter.getInstance().build(PATH_WEB)
                    .withString(WEB_LINK, link)
                    .withString(WEB_TITLE, title)
                    .navigation()
            }
        }

        binding.smartRefresh.setOnRefreshListener {
            currentPage = 1
            it.setEnableLoadMore(true)
            projectDetails.clear()
            projectViewModel.getProjectList(currentPage, cid = cid)
        }
        binding.smartRefresh.setOnLoadMoreListener {
            currentPage++
            projectViewModel.getProjectList(currentPage, cid = cid)
        }
    }

    override fun initObserver() {
        // Setting the fragment as the LifecycleOwner might cause memory leaks because views lives shorter than the Fragment.
        // Consider using Fragment's view lifecycle
        projectViewModel.projectList.observe(viewLifecycleOwner) {
            if (it.over) {
                binding.smartRefresh.finishLoadMoreWithNoMoreData()
            } else {
                binding.smartRefresh.finishLoadMore()
            }
            if (binding.smartRefresh.isRefreshing) {
                binding.smartRefresh.finishRefresh()
            }
            if (it.datas.isNotEmpty()) {
                projectDetails.addAll(it.datas)
                projectAdapter.setData(projectDetails)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        projectViewModel.projectList.removeObservers(viewLifecycleOwner)
    }
}