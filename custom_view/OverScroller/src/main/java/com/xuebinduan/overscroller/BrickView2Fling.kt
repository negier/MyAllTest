package com.xuebinduan.overscroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.OverScroller

/**
 * todo
 * 学到的教训：侦测手势速度时，我们不要移动View本身（即不要操作left、right、top、bottom），如果移动了手势数据就不准
 * 了，不准的原因很有可能是VelocityTracker侦测手势速度时用的坐标不是屏幕坐标getRawX，而是相对于View的坐标getX，你
 * 后面的fling操作自然也就不对了，但我们可以操作内容（即通过scrollTo或scrollBy去操作mScrollX和mScrollY属性）
 */
class BrickView2Fling @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val scroller = OverScroller(context)
    private val velocityTracker = VelocityTracker.obtain()
    private var lastDownX = 0F
    private var lastDownY = 0F
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private var boundaryPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 1f.dp2px()
    }
    private var offsetX = 0f
    private var offsetY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        velocityTracker.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scroller.abortAnimation()
                lastDownX = event.rawX
                lastDownY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (event.rawX - lastDownX).toInt()
                val dy = (event.rawY - lastDownY).toInt()

                offsetX += dx.toFloat()
                offsetY += dy.toFloat()

                lastDownX = event.rawX
                lastDownY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000)
                val vX = velocityTracker.xVelocity
                val vY = velocityTracker.yVelocity
                /*
                 * 向上Y值的范围 startY .. maxY；
                 * 向下Y值的范围 startY .. minY；
                 * 向左X值的范围 startX .. minX；
                 * 向右X值的范围 startX .. maxX。
                 * 这里边界就是centerX±500，centerY±500，碰到边界值不会再增加了，即会停下来。
                 */
                scroller.fling(
                    offsetX.toInt(), offsetY.toInt(), vX.toInt(), vY.toInt(), -500, 500, -500, 500
                )
            }
        }

        invalidate()

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(getBallCenterX(), getBallCenterY(), getBallSize(), paint)
        //绘制Fling的边界，因为普通的移动我们并没有限制，但在松手fling的时候会限制到，但我们肯定会松手嗒，所以那个圆会始终在边界框内。
        canvas.drawRect(
            width / 2f - 500,
            height / 2f - 500,
            width / 2f + 500,
            height / 2f + 500,
            boundaryPaint
        )
    }

    private fun getBallSize(): Float = 10.dp2px()
    private fun getBallCenterX() = width / 2f + offsetX
    private fun getBallCenterY() = height / 2f + offsetY

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            val currX = scroller.currX
            val currY = scroller.currY

            offsetX = currX.toFloat()
            offsetY = currY.toFloat()

            Log.e("TAG", "x=${scroller.currX}, y=${scroller.currY}");
            postInvalidateOnAnimation()
        }
    }
}