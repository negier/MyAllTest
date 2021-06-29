package com.xuebinduan.camera

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val IMAGE_SIZE = 200.dp2px()
    private val IMAGE_PADDING = 100.dp2px()
    private val camera = Camera()

    init{
        camera.rotateX(30f)
        camera.setLocation(0f,0f,-6*resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        // 上半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE/2,IMAGE_PADDING + IMAGE_SIZE/2)
        canvas.rotate(-30f)
        canvas.clipRect(-IMAGE_SIZE,-IMAGE_SIZE,IMAGE_SIZE,0f)
        canvas.rotate(30f)
        canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE/2),-(IMAGE_PADDING + IMAGE_SIZE/2))
        canvas.drawBitmap(getAvatar(IMAGE_SIZE.toInt()),IMAGE_PADDING,IMAGE_PADDING,paint)
        canvas.restore()

        // 下半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_SIZE/2,IMAGE_PADDING + IMAGE_SIZE/2)
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-IMAGE_SIZE,0f,IMAGE_SIZE,IMAGE_SIZE)
        canvas.rotate(30f)
        canvas.translate(-(IMAGE_PADDING + IMAGE_SIZE/2),-(IMAGE_PADDING + IMAGE_SIZE/2))
        canvas.drawBitmap(getAvatar(IMAGE_SIZE.toInt()),IMAGE_PADDING,IMAGE_PADDING,paint)
        canvas.restore()
    }

    fun getAvatar(width:Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,R.drawable.avator,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,R.drawable.avator,options)
    }

}