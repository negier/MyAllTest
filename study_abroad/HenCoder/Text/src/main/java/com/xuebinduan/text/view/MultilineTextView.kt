package com.xuebinduan.text.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.xuebinduan.text.R
import com.xuebinduan.text.dp2px

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val text = "lorem ipsum dolor sit amet, ea eum labore oporteat interesset, est an omnes munere postea, eam ea partem invenire. In doming albucius menandri mel, dolor vitae labores ei cum. Audire oblique apeirian ex per, rebum prompta utroque cu pri, offendit insolens vim ei. An case ceteros temporibus eos. Ne tibique accusata nam. An case ceteros temporibus eos. Ne tibique accusata nam. An case ceteros temporibus eos. Ne tibique accusata nam. An case ceteros temporibus eos. Ne tibique accusata nam. An case ceteros temporibus eos. Ne tibique accusata nam."
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp2px()
    }
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp2px()
    }
    private val IMAGE_SIZE = 150.dp2px()
    private val IMAGE_TOP = 25.dp2px()
    private val fontMetrics = Paint.FontMetrics()
    override fun onDraw(canvas: Canvas) {
        /*val staticLayout = StaticLayout(text,textPaint,width, Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
        staticLayout.draw(canvas)*/
        canvas.drawBitmap(getAvatar(IMAGE_SIZE.toInt()),width-IMAGE_SIZE,IMAGE_TOP,paint)
        paint.getFontMetrics(fontMetrics)
        val measuredWidth = floatArrayOf(0f)
        
        var start = 0
        var count : Int
        var verticalOffset = -fontMetrics.top
        var maxWidth : Float
        while(start < text.length){
            if(verticalOffset + fontMetrics.bottom < IMAGE_TOP
                || verticalOffset + fontMetrics.top > IMAGE_TOP + IMAGE_SIZE){
                maxWidth = width.toFloat()
            }else{
                maxWidth = width-IMAGE_SIZE
            }
            count = paint.breakText(text,start,text.length,true,maxWidth,measuredWidth)
            canvas.drawText(text,start,start+count,0f,verticalOffset,paint)
            verticalOffset += paint.fontSpacing
            start += count
        }

    }

    fun getAvatar(width:Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avator,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,R.drawable.avator,options)
    }
}