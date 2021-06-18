package com.xuebinduan.multitouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.scalableimageviewtest.dp
import com.example.scalableimageviewtest.getAvator

class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvator(resources,200.dp.toInt())
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    init{
        Log.e("TAG","tag:"+getTag().toString())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap,offsetX,offsetY,paint)
    }

    //事件序列是按照顺序的，全都是按照顺序的，我们看东西也都要按照序列去看事件，不能去看单个事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var pointerCount = event.pointerCount
        var sumX = 0f
        var sumY = 0f
        //抬起的时候还没有去掉手指的这种特殊情况
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for( i in 0 until pointerCount){
            if (!(isPointerUp && i == event.actionIndex)){
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        if(isPointerUp){
            pointerCount--
        }
        val focusX:Float = sumX / pointerCount
        val focusY:Float = sumY / pointerCount
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN,MotionEvent.ACTION_POINTER_UP->{
                downX = focusX
                downY = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE->{
                offsetX = focusX - downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY
                invalidate()
            }
        }
        return true
    }
}