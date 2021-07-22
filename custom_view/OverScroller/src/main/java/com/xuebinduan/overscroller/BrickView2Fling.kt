package com.xuebinduan.overscroller

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.OverScroller

class BrickView2Fling @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val scroller = OverScroller(context)
    private val velocityTracker = VelocityTracker.obtain()
    private var downX = 0f
    private var downY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        velocityTracker.addMovement(event)
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (event.x - downX).toInt()
                val dy = (event.y - downY).toInt()

                offsetLeftAndRight(dx)
                offsetTopAndBottom(dy)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000)
                val vX = velocityTracker.xVelocity
                val vY = velocityTracker.yVelocity
                Log.e("TAG","xVelocity:${vX},yVelocity:${vY}")
                scroller.fling(left,top,0,10,0,0,800,1000)
            }
        }

        return true
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            val currY = scroller.currY

            translationY = -currY.toFloat()

            Log.e("TAG", "x=${scroller.currX}, y=${-scroller.currY}");
            postInvalidate()
        }
    }
}