package com.lmy.wanandroid.view

/**
 * @Description: TODO
 * @Author: liheng.cai
 * @Date: 2022/2/16 16:21
 * @Email: liheng.cai@upuphone.com
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.lmy.wanandroid.R
import com.lmy.wanandroid.adapter.DistrictPhoneAdapter

/**
 * @Description: 自定义装饰器（实现分组+吸顶效果）
 * @Author: liheng.cai
 * @Date: 2022/2/14 10:39
 * @Email: liheng.cai@upuphone.com
 */
class StickHeaderDecoration(context: Context) : ItemDecoration() {
    // 头部的高
    var mItemHeaderHeight: Int = context.resources.getDimensionPixelOffset(R.dimen.district_phone_letter_height)
    private val mTextPaddingLeft: Int = context.resources.getDimensionPixelOffset(R.dimen.district_phone_letter_padding_left)
    
    // 画笔，绘制头部和分割线
    private val mItemHeaderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextPaint: Paint
    private val mTextRect: Rect = Rect()
    
    init {
        mItemHeaderPaint.color = Color.parseColor("#fff3f3f5")
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.textSize = context.resources.getDimension(R.dimen.district_phone_letter_size)
        mTextPaint.color = Color.parseColor("#80000000")
    }
    
    /**
     * 绘制Item的分割线和组头
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter as? DistrictPhoneAdapter
        adapter?.let {
            val count = parent.childCount // 获取可见范围内Item的总数
            for (i in 0 until count) {
                val view = parent.getChildAt(i)
                val position = parent.getChildLayoutPosition(view)
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight
                c.drawRect(
                    left.toFloat(),
                    (view.top - mItemHeaderHeight).toFloat(),
                    right.toFloat(),
                    view.top.toFloat(),
                    mItemHeaderPaint
                )
                mTextPaint.getTextBounds(
                    it.itemList?.get(position)?.initial,
                    0,
                    it.itemList?.get(position)?.initial?.length ?: 0,
                    mTextRect
                )
                c.drawText(
                    it.itemList?.get(position)?.initial ?: "",
                    (left + mTextPaddingLeft).toFloat(),
                    (view.top - mItemHeaderHeight + mItemHeaderHeight / 2 + mTextRect.height() / 2).toFloat(),
                    mTextPaint
                )
            }
        }
    }
    
    /**
     * 绘制Item的顶部布局（吸顶效果）
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter as? DistrictPhoneAdapter
        adapter?.let {
            val position =
                (parent.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            val view = parent.findViewHolderForAdapterPosition(position)?.itemView ?: return
            
            val top = parent.paddingTop
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            
            val bottom = mItemHeaderHeight.coerceAtMost(view.bottom)
            c.drawRect(
                left.toFloat(),
                (top + view.top - mItemHeaderHeight).toFloat(),
                right.toFloat(),
                (top + bottom).toFloat(),
                mItemHeaderPaint
            )
            mTextPaint.getTextBounds(
                it.itemList?.get(position)?.initial,
                0,
                it.itemList?.get(position)?.initial?.length ?: 0,
                mTextRect
            )
            c.drawText(
                it.itemList?.get(position)?.initial ?: "",
                (left + mTextPaddingLeft).toFloat(),
                (top + mItemHeaderHeight / 2 + mTextRect.height() / 2 - (mItemHeaderHeight - bottom)).toFloat(),
                mTextPaint
            )
            c.save()
        }
    }
    
    /**
     * 设置Item的间距
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        outRect.top = mItemHeaderHeight
    }
}
