package com.xuebinduan.overscroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.OverScroller

/**
 * 从底往上移动
 */
class BrickView1StartScroll @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val scroller = OverScroller(context)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scroller.startScroll(left, top, 0, 1000, 14000);
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            val currY = scroller.currY

            translationY = -currY.toFloat()

//            Log.e("TAG", "x=${currX}, y=${-currY}");
            postInvalidate()
        }
    }


}