package com.xuebinduan.pieadashboardview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.dp2px()
private val ANGLES = listOf(60f,90f,150f,60f)
private val COLORS = intArrayOf(Color.parseColor("#256262"),Color.parseColor("#a15451"),Color.parseColor("#b15451"),Color.parseColor("#c15451"))
private val OFFSET_LENGTH = 20f.dp2px()

class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        var rectF = RectF(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height/2 + RADIUS)
        var startAngle = 0f;
        for((index,angle) in ANGLES.withIndex()){
            if (index == 2){
                canvas.save()
                canvas.translate(OFFSET_LENGTH * cos(Math.toRadians(startAngle + angle/2f.toDouble()).toFloat()),
                    OFFSET_LENGTH * sin(Math.toRadians(startAngle + startAngle/2f.toDouble()).toFloat())
                )
            }

            paint.color = COLORS[index]
            canvas.drawArc(rectF,startAngle,angle,true,paint)
            startAngle += angle

            if (index == 2){
                canvas.restore()
            }
        }
    }

}