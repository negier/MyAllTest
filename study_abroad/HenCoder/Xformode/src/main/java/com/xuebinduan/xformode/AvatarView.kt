package com.xuebinduan.xformode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

private val IMAGE_WIDTH = 200f.dp2px()
private val IMAGE_PADDING = 20f.dp2px()
private val bounds = RectF(IMAGE_PADDING, IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING)

class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    override fun onDraw(canvas: Canvas) {
        // 这里离屏缓冲很重要。不然原来有一个白色的地板view，不是透明的，所以不会有效果；因此我们需要使用离屏缓冲单独开辟一个透明区域
        val count = canvas.saveLayer(bounds,null)
        canvas.drawOval(IMAGE_PADDING, IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING,paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING,paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }
    fun getAvatar(width:Int):Bitmap{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,R.drawable.avatar,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,R.drawable.avatar,options)
    }
}