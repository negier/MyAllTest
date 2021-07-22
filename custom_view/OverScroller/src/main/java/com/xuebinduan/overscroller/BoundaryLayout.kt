package com.xuebinduan.overscroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout

class BoundaryLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var boundary = RectF()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f.dp2px()
    }

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val iOffset = 10f.dp2px()
        val left = left + iOffset
        val right = right - iOffset
        val top = top + iOffset
        val bottom = bottom - iOffset

        boundary.set(left, top, right, bottom)

//        Log.e("TAG", "left:${left},right:${right},top:${top},bottom:${bottom}")
//        Log.e("TAG", "left:${boundary.left},right:${boundary.right},top:${boundary.top},bottom:${boundary.bottom}")
//        Log.e("TAG", "boundary width:${boundary.width()},height:${boundary.height()}")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(boundary, paint)
    }

    fun getBoundary() = boundary

}