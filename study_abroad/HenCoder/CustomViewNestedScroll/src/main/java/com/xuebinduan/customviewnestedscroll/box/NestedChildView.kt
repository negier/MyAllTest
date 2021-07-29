package com.xuebinduan.customviewnestedscroll.box

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import java.util.*

class NestedChildView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), NestedScrollingChild {
    private var mDownX = 0f
    private var mDownY = 0f
    private var mLastX = 0f
    private var mLastY = 0f

    private val consumed = IntArray(2) //消耗的距离
    private val offsetInWindow = IntArray(2) //窗口偏移

    private val scrollingChildHelper:NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    init {
        isNestedScrollingEnabled = true
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val x: Float = ev.x
        val y: Float = ev.y

        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = x
                mDownY = y
                mLastX = x
                mLastY = y
                //当开始滑动的时候，告诉父view
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL or ViewCompat.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_MOVE -> {
                /*
                mDownY:293.0
                mDownX:215.0
                 */
                var dy = (y - mDownY).toInt()
                var dx = (x - mDownX).toInt()

                //分发触屏事件给父类处理
                if (dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)) {
                    //减掉父类消耗的距离。因为父View移动了，其子View也会相应的跟着移动，所以这里减去就避免子View移动的情况，所以这里是特定于需求的，不是范例。
                    dx -= consumed[0]
                    dy -= consumed[1]
                    Log.e("TAG", Arrays.toString(offsetInWindow))
                }
                offsetLeftAndRight(dx)
                offsetTopAndBottom(dy)
            }
            MotionEvent.ACTION_UP -> {
                stopNestedScroll()
            }
        }
        mLastX = x
        mLastY = y

        return true
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        scrollingChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return scrollingChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return scrollingChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        scrollingChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return scrollingChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return scrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return scrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return scrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return scrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

}