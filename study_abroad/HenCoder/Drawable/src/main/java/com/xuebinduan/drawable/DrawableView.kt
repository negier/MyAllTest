package com.xuebinduan.drawable

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val drawable = MeshDrawable()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(50.dp2px().toInt(),80.dp2px().toInt(),width,height)
        drawable.draw(canvas)
    }
}