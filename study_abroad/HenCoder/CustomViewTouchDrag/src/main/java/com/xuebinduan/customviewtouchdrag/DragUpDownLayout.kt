package com.xuebinduan.customviewtouchdrag

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

class DragUpDownLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private lateinit var draggedView:View
    private var dragListener:ViewDragHelper.Callback = DragCallback()
    private var dragHelper : ViewDragHelper = ViewDragHelper.create(this,dragListener)
    private var viewConfiguration:ViewConfiguration = ViewConfiguration.get(context)

    override fun onFinishInflate() {
        super.onFinishInflate()
        draggedView = findViewById(R.id.draggedView)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if(dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    internal inner class DragCallback : ViewDragHelper.Callback(){
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child === draggedView
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (Math.abs(yvel) > viewConfiguration.scaledMinimumFlingVelocity) {
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height);
                } else {
                    dragHelper.settleCapturedViewAt(0, 0);
                }
            } else {
                if (releasedChild.top < height - releasedChild.bottom) {
                    dragHelper.settleCapturedViewAt(0, 0);
                } else {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height);
                }
            }
            postInvalidateOnAnimation();
        }
    }
}