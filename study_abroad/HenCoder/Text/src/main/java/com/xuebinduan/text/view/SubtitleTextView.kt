package com.xuebinduan.text.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.xuebinduan.text.dp2px

// 自己的练习
class SubtitleTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var text:String = "默认文字。字幕黑边白底"

    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 36.dp2px()
    }
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        paint.getFontMetrics(fontMetrics)
        var start = 0
        var count : Int
        var verticalOffset = -fontMetrics.top
        while(start < text.length){
            count = paint.breakText(text,start,text.length,true,width.toFloat(),null)

            //画描边
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1.dp2px()
            canvas.drawText(text,start,start+count,0f,verticalOffset,paint)
            //画文字
            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas.drawText(text,start,start+count,0f,verticalOffset,paint)

            verticalOffset += paint.fontSpacing
            start += count
        }
    }


}