package com.xuebinduan.touchevent.motioneventtest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> Log.e("TAG","子-ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.e("TAG","子-ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.e("TAG","子-ACTION_UP")
            MotionEvent.ACTION_CANCEL -> Log.e("TAG","子-ACTION_CANCEL")
        }
        return true
    }
}