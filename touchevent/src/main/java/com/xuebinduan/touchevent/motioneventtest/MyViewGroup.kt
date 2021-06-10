package com.xuebinduan.touchevent.motioneventtest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * todo 这里主要验证一件事：父ViewGroup突然拦截事件，子View会收到Cancel事件
 */
class MyViewGroup(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var downX = 0f
    private var result = false
    private var lastResult = !result
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        result = false
        if (ev.action == MotionEvent.ACTION_DOWN){
            downX = ev.getX()
        }
        if (ev.action == MotionEvent.ACTION_MOVE){
            if ((ev.getX() - downX)>100){
                result = true
            }
        }
        // 打印信息
        if (lastResult != result) {
            if (result) {
                Log.e("TAG", "父-拦截")
            } else {
                Log.e("TAG", "父-不拦截")
            }
            lastResult = result
        }
        if (ev.action == MotionEvent.ACTION_UP){
            lastResult = !result
        }
        return result
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> Log.e("TAG","父-ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.e("TAG","父-ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.e("TAG","父-ACTION_UP")
            MotionEvent.ACTION_CANCEL -> Log.e("TAG","父-ACTION_CANCEL")
        }
        return true
    }
}