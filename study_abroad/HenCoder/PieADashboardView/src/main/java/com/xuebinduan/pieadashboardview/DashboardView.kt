package com.xuebinduan.pieadashboardview


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

private const val OPEN_ANGLE = 120
private val DASH_WIDTH = 5f.dp2px()
private val DASH_LENGTH = 10f.dp2px()
private val RADIUS = 150f.dp2px()
private val LENGTH = 120f.dp2px()
private val INDEXES = 20f

class DashboardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dash = Path()
    private val path = Path()
    private lateinit var pathEffect: PathEffect

    init {
        paint.strokeWidth = 3f.dp2px()
        paint.style = Paint.Style.STROKE
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        var rectF = RectF(
            width / 2f - RADIUS,
            height / 2 - RADIUS,
            width / 2f + RADIUS,
            height / 2 + RADIUS
        )
        path.addArc(rectF, 90f + OPEN_ANGLE / 2, 360f - OPEN_ANGLE)
        val pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(
            dash,
            (pathMeasure.length - DASH_WIDTH) / INDEXES,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null
        //0..INDEXES
        var index = 0
        canvas.drawLine(
            width/2f,height/2f,
            width/2f + LENGTH * cos(Math.toRadians((90 + OPEN_ANGLE/2f + (360 - OPEN_ANGLE) / INDEXES * index).toDouble()).toFloat()),
            height/2f+ LENGTH * sin(Math.toRadians((90 + OPEN_ANGLE/2f + (360 - OPEN_ANGLE) / INDEXES * index).toDouble()).toFloat()),
            paint
        )
    }
}