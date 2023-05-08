package com.lmy.module_project.ui

import android.os.Bundle
import com.lmy.base.BaseVMFragment
import com.lmy.module_project.R
import com.lmy.module_project.adapter.ProjectAdapter
import com.lmy.module_project.databinding.FragmentProjectListBinding
import com.lmy.module_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author: mengyue.liu
 * @date: 2023/5/8
 * @description：
 */
class ProjectListFragment : BaseVMFragment<FragmentProjectListBinding>() {

    private val projectViewModel: ProjectViewModel by viewModel()
    private val currentPage = 1
    private lateinit var projectAdapter: ProjectAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_project_list
    }

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

    override fun initData() {
        arguments.let {
            val cid = it?.getInt(KEY_CID) ?: 0
            projectViewModel.getProjectList(currentPage, cid = cid)
        }

        projectAdapter = ProjectAdapter().apply {
            binding.projectRec.adapter = this
        }
    }

    override fun initObserver() {
        projectViewModel.projectList.observe(this) {
            projectAdapter.setData(it.datas)
        }
    }
}