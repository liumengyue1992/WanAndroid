package com.lmy.wanandroid.view

import android.util.TypedValue
import android.view.MotionEvent
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.lmy.wanandroid.R

/**
 * @Description: TODO
 * @Author: liheng.cai
 * @Date: 2022/1/12 14:33
 * @Email: liheng.cai@upuphone.com
 */
class LetterBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private val mPaint: Paint
    private var mLetterSpace = 0//文字间距
    private var mColor = Color.BLACK
    private var mHighlightColor = Color.RED
    private var mHighlightBackground = Color.TRANSPARENT
    private var mSize = sp2px(14)
    private var mLetters = ENGLISH_LETTERS
    private var mLetterIndex = -1
    private var showHighlightBg = false//显示高亮背景色


    init {
        //获取属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterBar)
        if (typedArray != null) {
            mColor = typedArray.getColor(R.styleable.LetterBar_android_textColor, mColor)
            mHighlightColor =
                typedArray.getColor(R.styleable.LetterBar_highlight_color, mHighlightColor)
            mSize = typedArray.getDimensionPixelSize(
                R.styleable.LetterBar_android_textSize,
                mSize.toInt()
            ).toFloat()
            mLetterSpace = typedArray.getDimensionPixelSize(R.styleable.LetterBar_letter_space, 0)
            mHighlightBackground =
                typedArray.getColor(R.styleable.LetterBar_highlightBackground, mHighlightBackground)
            typedArray.recycle()
        }
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.color = mColor
        mPaint.textSize = mSize
        mPaint.isAntiAlias = true
    }


    /**
     * 是否显示高亮背景色
     */
    fun isShowHighlightBg(showHighlightBg: Boolean) {
        this.showHighlightBg = showHighlightBg
    }

    private var onLetterChangeListener: OnLetterChangeListener? = null  //选中字母后的回调

    fun setLetters(letters: List<String>?) {
        mLetters = if (letters == null) {
            arrayListOf("#")
        } else {
            if (TextUtils.equals(letters.firstOrNull(), "#")) {
                letters
            } else {
                letters.toMutableList().apply {
                    add(0, "#")
                }.toList()
            }
        }
        requestLayout()
        postInvalidate()
    }

    private fun sp2px(sp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp.toFloat(),
            resources.displayMetrics
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //只需要测量一个字母所需的宽度即可,M较胖，就选它
        val letterWidth = mPaint.measureText(mLetters.first()).toInt() + paddingLeft + paddingRight
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = 0
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //给定一个最小值
            height = (mLetters.size * (mSize + mLetterSpace) + paddingTop + paddingBottom).toInt()
        }
        setMeasuredDimension(letterWidth, height)
    }

    override fun onDraw(canvas: Canvas) {
        //绘制背景色
//        if (showHighlightBg) {
//            mPaint.color = mHighlightBackground
//            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
//        }
        //绘制字母，需要先计算baseline
        val letterHeight = (height - paddingTop - paddingBottom) / mLetters.size
        val fontMetrics = mPaint.fontMetricsInt
        val base = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        for (i in mLetters.indices) {
            val dx = width / 2 - mPaint.measureText(mLetters[i]) / 2
            val dy = paddingTop + i * letterHeight + letterHeight / 2
            val baseline = dy + base
            if (mLetterIndex == i) {
                mPaint.color = mHighlightColor
                canvas.drawText(mLetters[i]!!, dx, baseline.toFloat(), mPaint)
            } else {
                mPaint.color = mColor
                canvas.drawText(mLetters[i]!!, dx, baseline.toFloat(), mPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val y = event.y
        //计算该坐标对应的字母索引
        mLetterIndex =
            ((y - paddingTop) / (height - paddingTop - paddingBottom) * mLetters.size).toInt()
        if (mLetterIndex < 0) {
            mLetterIndex = 0
        }
        if (mLetterIndex >= mLetters.size) {
            mLetterIndex = mLetters.size - 1
        }
        val letter = mLetters[mLetterIndex]
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                //开始触摸
                showHighlightBg = true
                //手指滑动
                onLetterChangeListener?.onLetterChanged(letter)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                //手指抬起
//                mLetterIndex = -1
//                showHighlightBg = false
                onLetterChangeListener?.onLetterChoose(letter)
                invalidate()
            }
        }
        return true
    }


    fun setLetter(letter: String) {
        mLetterIndex = mLetters?.indexOf(letter)
        invalidate()
    }

    fun setOnLetterChangeListener(listener: OnLetterChangeListener?) {
        onLetterChangeListener = listener
    }

    interface OnLetterChangeListener {
        fun onLetterChanged(letter: String?)
        fun onLetterChoose(letter: String?)
    }

    companion object {
        val ENGLISH_LETTERS = listOf(
            "#",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
        )
    }


}
