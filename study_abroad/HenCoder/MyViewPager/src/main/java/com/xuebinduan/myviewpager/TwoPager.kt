package com.xuebinduan.myviewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

class TwoPager(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0f
    private var scrolling = false
    private val overScroller: OverScroller = OverScroller(context)
    private val viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain()
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        var childRight = width
        var childBottom = width
        for (child in children) {
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)
        var result = false
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> if (!scrolling) {
                val dx = downX - ev.x
                if (abs(dx) > pagingSlop) {
                    scrolling = true
                    parent.requestDisallowInterceptTouchEvent(true)
                    result = true
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (downX - event.x + downScrollX).toInt()
                    .coerceAtLeast(0)//上下限
                    .coerceAtMost(width)
                scrollTo(dx, 0)
            }
            MotionEvent.ACTION_UP -> {
                //1000毫秒移动的像素数
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (abs(vx) < minVelocity) {
                    if (scrollX > width / 2) 1 else 0
                } else {
                    if (vx < 0) 1 else 0
                }
                val scrollDistance = if (targetPage == 1) {
                    width - scrollX
                } else {
                    -scrollX
                }
                overScroller.startScroll(getScrollX(),0,scrollDistance,0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        if(overScroller.computeScrollOffset()){
            scrollTo(overScroller.currX,overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}