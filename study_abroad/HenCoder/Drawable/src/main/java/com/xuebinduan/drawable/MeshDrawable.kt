package com.xuebinduan.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.toColorInt


private val INTERVAL = 50.dp2px()
class MeshDrawable: Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color = "#F9A825".toColorInt()
        strokeWidth = 5.dp2px()
    }


    override fun draw(canvas: Canvas) {
        var x = bounds.left.toFloat()
        while(x <= bounds.right){
            canvas.drawLine(x,bounds.top.toFloat(),x,bounds.bottom.toFloat(),paint)
            x += INTERVAL
        }
        var y = bounds.top.toFloat()
        while( y <= bounds.bottom){
            canvas.drawLine(bounds.left.toFloat(),y,bounds.right.toFloat(),y,paint)
            y += INTERVAL
        }
    }

    // 设置透明度
    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    //获取不透明度
    override fun getOpacity(): Int {
        return when(paint.alpha){
            0 -> PixelFormat.TRANSPARENT //全透明
            0xff -> PixelFormat.OPAQUE //不透明
            else -> PixelFormat.TRANSLUCENT //半透明
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }
}