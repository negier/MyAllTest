package com.xuebinduan.xformode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class XfermodeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val bounds = RectF(150f.dp2px(),50f.dp2px(),300f.dp2px(),200f.dp2px())

    private val circleBitmap = Bitmap.createBitmap(150f.dp2px().toInt(),150f.dp2px().toInt(),Bitmap.Config.ARGB_8888)
    private val squareBitmap = Bitmap.createBitmap(150f.dp2px().toInt(),150f.dp2px().toInt(),Bitmap.Config.ARGB_8888)

    init {
        // 这里是准备两个一样宽高的透明Bitmap，好用在onDraw里面进行Xfermode的变换。因为变换需要两个视图需要有一个重叠，也就是宽高位置需要有重叠才能有效果
        val canvas = Canvas();
        canvas.setBitmap(circleBitmap)
        paint.color = Color.parseColor("#E61A5A")
        canvas.drawOval(50f.dp2px(),0f.dp2px(),150f.dp2px(),100f.dp2px(),paint)
        paint.color = Color.parseColor("#1E8AF4")
        canvas.setBitmap(squareBitmap)
        canvas.drawRect(0f.dp2px(),50f.dp2px(),100f.dp2px(),150f.dp2px(),paint)
    }

    override fun onDraw(canvas: Canvas) {
        // 这里离屏缓冲很重要。不然原来有一个白色的地板view，不是透明的，所以不会有效果；因此我们需要使用离屏缓冲单独开辟一个透明区域
        val count = canvas.saveLayer(bounds,null)
        canvas.drawBitmap(circleBitmap,150f.dp2px(),50f.dp2px(),paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(squareBitmap,150f.dp2px(),50f.dp2px(),paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }

}