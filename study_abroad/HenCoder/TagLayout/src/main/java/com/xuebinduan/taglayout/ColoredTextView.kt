package com.xuebinduan.taglayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

private val COLORS = intArrayOf(
    Color.parseColor("#E91E63"),
    Color.parseColor("#673AB7"),
    Color.parseColor("#3F51B5"),
    Color.parseColor("#2196F3"),
    Color.parseColor("#009688"),
    Color.parseColor("#FF9800"),
    Color.parseColor("#FF5722"),
    Color.parseColor("#795548")
)

private val TEXT_SIZE = intArrayOf(16,22,28)
private val CORNER_RADIUS = 4.dp2px()
private val X_PADDING = 16.dp2px().toInt()
private val Y_PADDING = 8.dp2px().toInt()

class ColoredTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val random = Random()

    init{
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZE[random.nextInt(TEXT_SIZE.size)].toFloat()
        paint.color = COLORS[random.nextInt(COLORS.size)]
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(0f,0f,width.toFloat(),height.toFloat(), CORNER_RADIUS, CORNER_RADIUS,paint)
        super.onDraw(canvas)
    }

}