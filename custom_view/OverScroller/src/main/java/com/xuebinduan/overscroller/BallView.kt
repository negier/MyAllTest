package com.xuebinduan.overscroller

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.OverScroller

class BallView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mDownX = 0f
    private var mDownY = 0f
    private var mLastX = 0f
    private var mLastY = 0f

    private var velocityTracker: VelocityTracker? = null

    private val scroller = OverScroller(context)

    private val minimumFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (velocityTracker == null){
            velocityTracker = VelocityTracker.obtain()
        }

        if (velocityTracker != null) {
            velocityTracker!!.addMovement(event);
        }

        val x: Float = event.x
        val y: Float = event.y

        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = x
                mDownY = y
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                var dy = (y - mDownY).toInt()
                var dx = (x - mDownX).toInt()

                offsetLeftAndRight(dx)
                offsetTopAndBottom(dy)

                fixCoordinate()
            }
            MotionEvent.ACTION_UP -> {
                val initialVelocity = velocityTracker!!.yVelocity
                if (Math.abs(initialVelocity) > minimumFlingVelocity) {
                    // 由于坐标轴正方向问题，要加负号。
                    doFling(-initialVelocity.toInt())
                }
            }
        }
        mLastX = x
        mLastY = y

        return true
    }

    private fun fixCoordinate() {
        if (parent is BoundaryLayout) {
            val boundary = (parent as BoundaryLayout).getBoundary()
            if (left < boundary.left) {
                offsetLeftAndRight((boundary.left - left).toInt())
            }
            if (right > boundary.right) {
                offsetLeftAndRight((boundary.right - right).toInt())
            }
            if (top < boundary.top) {
                offsetTopAndBottom((boundary.top - top).toInt())
            }
            if (bottom > boundary.bottom) {
                offsetTopAndBottom((boundary.bottom - bottom).toInt())
            }
//            Log.e("TAG", "left:${left},right:${right},top:${top},bottom:${bottom}")
        }
    }

    private fun doFling(speed: Int) {
        if (scroller == null) {
            return
        }
        scroller.fling(0, scrollY, 0, speed, 0, 0, -500, 10000)
        invalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

}