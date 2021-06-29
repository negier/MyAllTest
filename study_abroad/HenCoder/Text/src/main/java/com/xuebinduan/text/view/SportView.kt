package com.xuebinduan.text.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.xuebinduan.text.dp2px

private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
private val RING_WIDTH = 20.dp2px()
private val RADIUS = 150.dp2px()

class SportView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // 这里没用sp的原因是因为：sp受屏幕密度影响，也受系统的字体设置影响，是用来方便一些视力群体；但我们这儿主要是用来运动界面的一行文字显示，不需要受系统的字体设置影响
        textSize = 100.dp2px()
//        typeface = ResourcesCompat.getFont(context,R.font.font)
        textAlign = Paint.Align.CENTER
    }

    private val bounds = Rect()
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        //绘制圆环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f,
            RADIUS, paint)
        //绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )
        //绘制文字（练习文字的垂直居中）
        paint.style = Paint.Style.FILL
        /*
        paint.getTextBounds("abab", 0, "abab".length, bounds)
        //设置针对baseline的偏移y轴
        canvas.drawText("abab", width / 2f, height / 2f - (bounds.bottom + bounds.top) / 2, paint)
        以上适用于静态的文字↑，以下适用于动态文字会变化的↓
         */
        paint.getFontMetrics(fontMetrics)
        canvas.drawText("abab", width / 2f, height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2, paint)
        //绘制文字2，居左顶对齐（这里练习文字的贴边）
        paint.textAlign = Paint.Align.LEFT
        paint.getFontMetrics(fontMetrics)
        paint.getTextBounds("abab", 0, "abab".length, bounds)
        canvas.drawText("abab",-bounds.left.toFloat(),-bounds.top.toFloat(),paint)

    }
}