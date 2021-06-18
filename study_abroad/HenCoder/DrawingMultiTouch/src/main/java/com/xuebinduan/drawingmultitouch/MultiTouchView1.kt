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

class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvator(resources,200.dp.toInt())
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var trackingPointerId = 0

    init{
        Log.e("TAG","tag:"+getTag().toString())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap,offsetX,offsetY,paint)
    }

    //事件序列是按照顺序的，全都是按照顺序的，我们看东西也都要按照序列去看事件，不能去看单个事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN->{
                trackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN->{
                //当你在ACTION_POINTER_DOWN和ACTION_POINTER_UP的时候，你用这个getActionIndex可以拿到那个活动（也就是按下或者抬起）的手指的index 序号
                val actionIndex = event.actionIndex
                trackingPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getX(actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP->{
                //接棒问题，这根手指是不是我正在跟踪的手指，如果不是，什么都不用管，如果是，就需要放弃这个手指，去寻找一个新的手指来替换，来接棒
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                /*if (pointerId == trackingPointerId){
                    //这里getPointerCount()是包含正在抬起的这只手指的
                    val newIndex = event.pointerCount - 1
                }*/
                if (pointerId == trackingPointerId){
                    val newIndex = if (actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    }else{
                        event.pointerCount - 1
                    }
                    trackingPointerId = event.getPointerId(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getX(newIndex)
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }

            }
            MotionEvent.ACTION_MOVE->{
                val index = event.findPointerIndex(trackingPointerId)
                offsetX = event.getX(index) - downX + originalOffsetX
                offsetY = event.getY(index) - downY + originalOffsetY
                invalidate()
            }
        }
        return true
    }
}