package com.lmy.androidutilcode.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.blankj.utilcode.util.SizeUtils
import com.lmy.androidutilcode.R

/**
 * @description：联系人同步状态自定义View
 * @author：Mengyue.Liu
 * @time： 2022/4/25 15:12
 */
class ContactsSyncView : View {
    
    // 没有网络＞云空间不足＞电量不足(todo 是否需要区分异常状态UI)
    companion object {
        const val STATE_SYNC_NOT = 0 //未开启同步  网络异常  云空间已满  电量不足
        const val STATE_SYNCING_FIRST = 1 // 正在同步--第一段（缩放--准备过程）
        const val STATE_SYNCING_SECOND = 2 // 正在同步--第二段（旋转--同步过程）
        const val STATE_SYNC_COMPLETE = 3 // 同步完成
        const val local = "本地"
        const val cloud = "云端"
        const val contacts = "联系人"
    }
    
    private val syncCompleteTextColor by lazy { Color.parseColor("#6373FF") }
    
    /**
     * 同步状态
     */
    private var syncState: Int = STATE_SYNC_NOT
    
    /**
     * 圆的paint
     */
    private lateinit var circlePaint: Paint
    
    /**
     * 左侧圆的渐变色
     */
    private lateinit var leftLg: LinearGradient
    
    /**
     * 右侧圆的渐变色
     */
    private lateinit var rightLg: LinearGradient
    
    /**
     * 两个圆的半径
     */
    private var radius = SizeUtils.dp2px(49f).toFloat()
    
    /**
     * 左侧圆初始的圆心x坐标（未做平移前）
     */
    private var leftCircleXStart = 0f
    
    /**
     * 右侧圆初始的圆心x坐标（未做平移前）
     */
    private var rightCircleXStart = 0f
    
    /**
     * 左侧圆的圆心x---左边文字的基线x
     */
    private var leftCircleX = 0f
    
    /**
     * 右侧圆的圆心x---右边文字的基线x
     */
    private var rightCircleX = 0f
    
    /**
     * 两个圆圆心y
     */
    private var circleY = 0.0f
    
    /**
     * 进度圆环两段圆弧的paint
     */
    private lateinit var progressArcPaint: Paint
    
    /**
     * 进度圆弧的半径
     */
    private val progressArcRadius = SizeUtils.dp2px(67f).toFloat()
    
    /**
     * 进度圆弧的弧宽度
     */
    private val strokeWidth = SizeUtils.dp2px(20f).toFloat()
    
    /**
     * 第一段圆弧的画笔shader
     */
    private lateinit var mSweepGradientFirst: SweepGradient
    
    /**
     * 第二段圆弧的画笔shader
     */
    private lateinit var mSweepGradientSecond: SweepGradient
    
    /**
     * 第一段圆弧的渐变色
     */
    private lateinit var mGradientColorsFirst: IntArray
    
    /**
     * 第二段圆弧的渐变色
     */
    private lateinit var mGradientColorsSecond: IntArray
    
    /**
     * 进度圆弧尾部的小半圆paint
     */
    private lateinit var smallCirclePaint: Paint
    
    /**
     * 进度圆弧尾部的小半圆的半径
     */
    private var smallCircleRadius = 0f
    
    /**
     * 绘制文字paint
     */
    private lateinit var textPaint: Paint
    
    /**
     * 上面一行文字的基线y
     */
    private var textTopY = 0f
    
    /**
     * 下面一行文字的基线y
     */
    private var textBottomY = 0f
    
    /**
     * 上下两行文字的间距
     */
    private val textSpacing = SizeUtils.dp2px(12f).toFloat()
    
    /**
     * 两个圆之间的间距
     */
    private val spacing = SizeUtils.dp2px(36f).toFloat()
    
    /**
     * 控件宽
     */
    private var width = 0f
    
    /**
     * 控件高
     */
    private var height = 0f
    
    /**
     * 测量文字高度的rect
     */
    private val rect = Rect()
    
    /**
     * 进度圆弧的rectF
     */
    private val arcRect = RectF()
    
    /**
     * 覆盖第一个分段小圆弧的半圆的rect(初始靠右边的)
     */
    private val arcRectFirst = RectF()
    
    /**
     * 覆盖第二个分段小圆弧的半圆的rect(初始靠左边的)
     */
    private val arcRectSecond = RectF()
    
    /**
     * 中间的小箭头(两圆未重叠时)
     */
    private var scaleArrowBitmapBlack: Bitmap? = null
    
    /**
     * 中间的小箭头(两圆重叠时)
     */
    private var scaleArrowBitmapWhite: Bitmap? = null
    
    /**
     * 中间的小箭头的宽度
     */
    private val bitmapWidth = SizeUtils.dp2px(12f)
    
    /**
     * 旋转动画
     */
    private var rotateAnimation: RotateAnimation? = null
    
    /**
     * 平移动画
     */
    private var translateAnimator: ValueAnimator? = null
    
    /**
     * 本地联系人数量
     */
    private var contactsNumLocal: String = "0"
    
    /**
     * 云端联系人数量
     */
    private var contactsNumCloud: String = "0"
    
    /**
     * 缩放动画的进度值0-->1
     */
    private var scaleAnimatedValue = 0F
    
    constructor(context: Context) : super(context) {
        init()
    }
    
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init()
    }
    
    private fun init() {
        circlePaint = Paint()
        circlePaint.isAntiAlias = true
        circlePaint.style = Paint.Style.FILL
        // circlePaint.alpha = 200
        
        progressArcPaint = Paint()
        progressArcPaint.isAntiAlias = true
        progressArcPaint.style = Paint.Style.STROKE
        progressArcPaint.strokeWidth = strokeWidth
        progressArcPaint.strokeCap = Paint.Cap.BUTT //设置画笔的线冒样式：BUTT  SQUARE  ROUND
        
        mGradientColorsFirst = intArrayOf(Color.WHITE, Color.parseColor("#03A8FE"))
        mGradientColorsSecond = intArrayOf(Color.WHITE, Color.parseColor("#6574FE"))
        
        textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        
        smallCirclePaint = Paint()
        smallCirclePaint.isAntiAlias = true
        smallCirclePaint.style = Paint.Style.FILL
        smallCirclePaint.color = Color.parseColor("#6574FE")  //"#03A8FE"
        
        smallCircleRadius = strokeWidth / 2
        
        val arrowBitmapBlack = BitmapFactory.decodeResource(resources, R.drawable.ic_sync_arrow)
        val arrowBitmapWhite =
            BitmapFactory.decodeResource(resources, R.drawable.ic_sync_arrow_white)
        scaleArrowBitmapBlack =
            Bitmap.createScaledBitmap(arrowBitmapBlack, bitmapWidth, bitmapWidth, true)
        scaleArrowBitmapWhite =
            Bitmap.createScaledBitmap(arrowBitmapWhite, bitmapWidth, bitmapWidth, true)
    }
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()
        leftLg = LinearGradient(w / 2 - spacing / 2 - 2 * radius,
            h / 2.toFloat(),
            w / 2 - spacing / 2,
            h / 2.toFloat(),
            Color.parseColor("#6574FE"),
            Color.parseColor("#A8B0FA"),
            Shader.TileMode.MIRROR) //边缘拉伸模式
        
        leftCircleX = w / 2.toFloat() - spacing / 2 - radius
        leftCircleXStart = w / 2.toFloat() - spacing / 2 - radius
        circleY = h / 2.toFloat()
        
        textTopY = circleY - textSpacing / 2
        
        rightLg = LinearGradient(w / 2 + spacing / 2 + 2 * radius,
            h / 2.toFloat(),
            w / 2 + spacing / 2,
            h / 2.toFloat(),
            Color.parseColor("#03A8FE"),
            Color.parseColor("#8BDBF6"),
            Shader.TileMode.MIRROR)
        
        rightCircleX = w / 2.toFloat() + spacing / 2 + radius
        rightCircleXStart = w / 2.toFloat() + spacing / 2 + radius
        
        val positionsFirst = floatArrayOf(0f, 0.5f)
        val positionsSecond = floatArrayOf(0.5f, 1f)
        // 扫描着色器  cx：中心点x坐标  cy：中心点y坐标
        mSweepGradientFirst = SweepGradient(w / 2.toFloat(), h / 2.toFloat(),
            mGradientColorsFirst,
            positionsFirst)
        
        mSweepGradientSecond = SweepGradient(w / 2.toFloat(), h / 2.toFloat(),
            mGradientColorsSecond,
            positionsSecond)
        
        arcRect.left = w / 2 - progressArcRadius
        arcRect.top = h / 2 - progressArcRadius
        arcRect.right = w / 2 + progressArcRadius
        arcRect.bottom = h / 2 + progressArcRadius
        
        arcRectFirst.left = w / 2 + progressArcRadius - strokeWidth / 2
        arcRectFirst.top = h / 2 - strokeWidth / 2
        arcRectFirst.right = w / 2 + progressArcRadius + strokeWidth / 2
        arcRectFirst.bottom = h / 2 + strokeWidth / 2
        
        
        arcRectSecond.left = w / 2 - progressArcRadius - strokeWidth / 2
        arcRectSecond.top = h / 2 - strokeWidth / 2
        arcRectSecond.right = w / 2 - progressArcRadius + strokeWidth / 2
        arcRectSecond.bottom = h / 2 + strokeWidth / 2
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (syncState == STATE_SYNC_NOT || syncState == STATE_SYNCING_FIRST) {
            circlePaint.shader = leftLg
            canvas.drawCircle(leftCircleX, circleY, radius, circlePaint)
            circlePaint.shader = rightLg
            canvas.drawCircle(rightCircleX, circleY, radius, circlePaint)
            
            textPaint.color = Color.WHITE
            textPaint.alpha = ((1f - scaleAnimatedValue) * 255).toInt()
            textPaint.textSize = SizeUtils.sp2px(24f).toFloat() - SizeUtils.sp2px(20f).toFloat() * scaleAnimatedValue
            
            canvas.drawText(contactsNumLocal, leftCircleX, textTopY, textPaint)
            canvas.drawText(contactsNumCloud, rightCircleX, textTopY, textPaint)
            
            textPaint.textSize = SizeUtils.sp2px(14f).toFloat()- SizeUtils.sp2px(11f).toFloat() * scaleAnimatedValue
            
            //测量文字高度
            textPaint.getTextBounds(local, 0, local.length, rect)
            val textHeight = rect.height()
            textBottomY = circleY + textSpacing / 2 + textHeight
            
            canvas.drawText(local, leftCircleX, textBottomY, textPaint)
            canvas.drawText(cloud, rightCircleX, textBottomY, textPaint)
            
            if (rightCircleX - leftCircleX > 2 * radius - bitmapWidth) {
                scaleArrowBitmapBlack?.let {
                    canvas.drawBitmap(it,
                        width / 2 - scaleArrowBitmapBlack!!.width / 2,
                        height / 2 - scaleArrowBitmapBlack!!.height / 2,
                        null)
                }
            } else {
                scaleArrowBitmapWhite?.let {
                    canvas.drawBitmap(it,
                        width / 2 - scaleArrowBitmapWhite!!.width / 2,
                        height / 2 - scaleArrowBitmapWhite!!.height / 2,
                        null)
                }
            }
            drawProgressCircle(canvas)
        } else if (syncState == STATE_SYNCING_SECOND) {
            drawProgressCircle(canvas)
        } else if (syncState == STATE_SYNC_COMPLETE) {
            drawProgressCircle(canvas)
            drawSyncCompleteText(canvas)
        }
    }
    
    /**
     * 绘制本地及云端联系人未同步状态页面
     * @param canvas Canvas
     */
    private fun drawProgressCircle(canvas: Canvas) {
        progressArcPaint.shader = mSweepGradientFirst
        canvas.drawArc(arcRect, 0f, 180f, false, progressArcPaint)
        progressArcPaint.shader = mSweepGradientSecond
        canvas.drawArc(arcRect, 180f, 180f, false, progressArcPaint)
        
        smallCirclePaint.color = Color.parseColor("#6574FE")
        canvas.drawArc(arcRectFirst, 0f, 180f, false, smallCirclePaint)
        smallCirclePaint.color = Color.parseColor("#03A8FE")
        canvas.drawArc(arcRectSecond, 180f, 180f, false, smallCirclePaint)
    }
    
    /**
     * 绘制同步完成后页面状态
     * @param canvas Canvas
     */
    private fun drawSyncCompleteText(canvas: Canvas) {
        textPaint.color = syncCompleteTextColor
        textPaint.textSize = SizeUtils.sp2px(24f).toFloat()
        canvas.drawText(contactsNumLocal, width / 2.toFloat(), textTopY, textPaint)
        
        textPaint.textSize = SizeUtils.sp2px(14f).toFloat()
        
        textPaint.getTextBounds(contactsNumLocal, 0, contactsNumLocal.length, rect)
        val textHeight = rect.height()
        textBottomY = circleY + textSpacing / 2 + textHeight
        
        canvas.drawText(contacts, width / 2.toFloat(), textBottomY, textPaint)
    }
    
    /**
     * 设置同步状态
     * @param state Int
     */
    fun setSyncState(state: Int) {
        this.syncState = state
        when (this.syncState) {
            STATE_SYNCING_FIRST -> {
                // 先计算出两个圆要移动的总距离
                val moveWidth = radius + spacing / 2
                // 计算两个圆半径要缩小的距离
                val changeWidth = radius - strokeWidth / 2
                translateAnimator = ValueAnimator.ofFloat(0f, 1f)
                translateAnimator?.addUpdateListener { animation ->
                    //开启缩放
                    startTranslate(animation.animatedValue as Float, changeWidth)
                }
                translateAnimator?.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {
                    }
                    
                    override fun onAnimationEnd(p0: Animator?) {
                        // 平移动画结束后设置正在同步的状态
                        setSyncState(STATE_SYNCING_SECOND)
                        rotateAnimation =
                            RotateAnimation(0f, 360f, width / 2.toFloat(), height / 2.toFloat())
                        rotateAnimation?.duration = 3000
                        rotateAnimation?.repeatCount = Animation.INFINITE
                        rotateAnimation?.repeatMode = Animation.RESTART
                        rotateAnimation?.interpolator = LinearInterpolator()
                        startAnimation(rotateAnimation)
                    }
                    
                    override fun onAnimationCancel(p0: Animator?) {
                    }
                    
                    override fun onAnimationRepeat(p0: Animator?) {
                    }
                })
                translateAnimator?.duration = 1000
                translateAnimator?.start()
            }
            STATE_SYNC_COMPLETE -> {
                rotateAnimation?.cancel()
                translateAnimator?.cancel()
                invalidate()
            }
            STATE_SYNC_NOT -> {
                // 重置初始值
                leftCircleX = width / 2.toFloat() - spacing / 2 - radius
                rightCircleX = width / 2.toFloat() + spacing / 2 + radius
                
                invalidate()
            }
        }
    }
    
    /**
     * 开启平移动画
     * @param animatedValue Float
     */
    private fun startTranslate(animatedValue: Float, changeWidth: Float) {
        // 计算出偏移量
        // leftCircleX = leftCircleXStart + changeWidth * animatedValue
        // rightCircleX = rightCircleXStart - changeWidth * animatedValue
        scaleAnimatedValue = animatedValue
        
        // 圆的半径缩小
        radius = SizeUtils.dp2px(49f).toFloat() - changeWidth * animatedValue
        
        Log.e("lmy", "changeWidth = $changeWidth")
        Log.e("lmy", "animatedValue = $animatedValue")
        Log.e("lmy", "radius = $radius")
        invalidate()
    }
    
    /**
     * 设置联系人同步完成后，联系人的数量
     * @param contactsNum Int
     */
    private fun setSyncCompleteContactsNum(contactsNum: String) {
        this.syncState = STATE_SYNC_COMPLETE
        this.contactsNumLocal = contactsNum
        this.contactsNumCloud = contactsNum
        invalidate()
    }
    
    /**
     * 设置联系人未同步时，本地和云端的联系人数量
     * @param localNum String
     * @param cloudNum String
     */
    private fun setSyncNotContactsNum(localNum: String, cloudNum: String) {
        this.syncState = STATE_SYNC_NOT
        this.contactsNumLocal = localNum
        this.contactsNumCloud = cloudNum
        invalidate()
    }
    
    /**
     * 更新本地联系人数量
     * @param localNum String
     */
    private fun updateLocalContactsNum(localNum: String) {
        this.syncState = STATE_SYNC_NOT
        this.contactsNumLocal = localNum
        invalidate()
    }
}