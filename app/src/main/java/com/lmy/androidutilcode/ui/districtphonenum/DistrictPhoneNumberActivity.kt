package com.lmy.androidutilcode.ui.districtphonenum

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lmy.androidutilcode.BR
import com.lmy.androidutilcode.R
import com.lmy.androidutilcode.adapter.DistrictPhoneAdapter
import com.lmy.androidutilcode.base.BaseVMActivity
import com.lmy.androidutilcode.databinding.ActivityDistrictPhoneNumberBinding
import com.lmy.androidutilcode.util.Constant
import com.lmy.androidutilcode.view.LetterBar
import com.lmy.androidutilcode.view.StickHeaderDecoration
import com.upuphone.account.ui.login.adapter.DistrictPhoneChildSelectAdapter

/**
 * @Description: 选择国家地区的手机区号
 * @Author: liheng.cai
 * @Date: 2022/2/14 10:39
 * @Email: liheng.cai@upuphone.com
 */
class DistrictPhoneNumberActivity : BaseVMActivity<ActivityDistrictPhoneNumberBinding, DistrictPhoneViewModel>(),
    LetterBar.OnLetterChangeListener {
    
    override fun getLayoutId(): Int = R.layout.activity_district_phone_number
    
    override fun initVariableId(): Int = BR.vm
    
    private val districtPhoneAdapter by lazy {
        DistrictPhoneAdapter().apply {
            onItemClickListener = {
                setResult(RESULT_OK, Intent().putExtra("code", it?.code))
                finish()
            }
        }
    }
    private val districtPhoneChildSelectAdapter by lazy {
        DistrictPhoneChildSelectAdapter().apply {
            itemClickListener = {
                viewModel.onDistrictPhonePosition(binding.selectDistrictPhoneFloatLl.letter ?: "", it)
                handle.removeCallbacksAndMessages(null)
                handle.postDelayed(dismissFloatLetterRunnable, Constant.INT_DISMISS_TIME)
            }
        }
    }
    private val handle by lazy { Handler(Looper.getMainLooper()) }
    
    private fun initView() {
        //国家区号
        binding.districtPhoneAllRv.layoutManager = LinearLayoutManager(this)
        binding.districtPhoneAllRv.addItemDecoration(StickHeaderDecoration(this))
        binding.districtPhoneAllRv.adapter = districtPhoneAdapter
        binding.districtPhoneAllRv.clearOnScrollListeners()
        binding.districtPhoneAllRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
                if (layoutManager != null) {
                    val firstItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                    val letter = districtPhoneAdapter.itemList?.get(firstItemPosition)?.initial
                    if (TextUtils.isEmpty(letter)) return
                    binding.selectDistrictLetterBar.setLetter(letter!!)
                }
            }
        })
        //字母监听
        binding.selectDistrictLetterBar.setOnLetterChangeListener(this)
        //选择字母后的悬浮试图
        binding.selectDistrictPhoneFloatLl.selectChildItemRv.layoutManager = LinearLayoutManager(this)
        binding.selectDistrictPhoneFloatLl.selectChildItemRv.adapter = districtPhoneChildSelectAdapter
        binding.selectDistrictPhoneFloatLl.selectChildItemRv.clearOnScrollListeners()
        binding.selectDistrictPhoneFloatLl.selectChildItemRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    handle.postDelayed(dismissFloatLetterRunnable, Constant.INT_DISMISS_TIME)
                } else {
                    handle.removeCallbacksAndMessages(null)
                }
            }
        })
    }
    
    override fun initData() {
        binding.itemRadius = resources.getDimension(R.dimen.district_phone_item_radius)
        initView()
        viewModel.onGetDistrictPhoneInfo()
        viewModel.districtPhoneList.observe(this, Observer {
            districtPhoneAdapter.itemList = it
        })
        viewModel.letterKeyList.observe(this, Observer {
            binding.selectDistrictLetterBar.setLetters(it)
        })
        viewModel.selectLetterFirstIndexKey.observe(this, Observer {
            if (it == null) return@Observer
            val scrollByY = resources.getDimensionPixelSize(R.dimen.district_phone_item_height) * it.childIndex * -1
            districtPhoneScrollToPosition(it.index, scrollByY)
        })
    }
    
    override fun onLetterChanged(letter: String?) {
        binding.selectDistrictPhoneFloatLl.root.visibility = View.VISIBLE
        binding.selectDistrictPhoneFloatLl.root.visibility = View.VISIBLE
        binding.selectDistrictPhoneFloatLl.letter = letter
        binding.selectDistrictPhoneFloatLl.executePendingBindings()
        //选中字母后，悬浮视图的adapter 绑定数据
        districtPhoneChildSelectAdapter.itemList = districtPhoneAdapter.getFirstNameByLetter(letter ?: "")
        //国家地区跟随滚动
        districtPhoneScrollToPosition(districtPhoneAdapter.getLocationByLetter(letter ?: ""))
        handle.postDelayed(dismissFloatLetterRunnable, Constant.INT_DISMISS_TIME)
    }
    
    override fun onLetterChoose(letter: String?) {
    }
    
    private val dismissFloatLetterRunnable: Runnable = Runnable {
        binding.selectDistrictPhoneFloatLl.root.visibility = View.GONE
    }
    
    private fun districtPhoneScrollToPosition(index: Int, offset: Int = 0) {
        val linearLayoutManager = binding.districtPhoneAllRv.layoutManager as? LinearLayoutManager
        linearLayoutManager?.scrollToPositionWithOffset(index, offset)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        handle.removeCallbacksAndMessages(null)
        binding.districtPhoneAllRv.clearOnScrollListeners()
        binding.selectDistrictPhoneFloatLl.selectChildItemRv.clearOnScrollListeners()
    }
}