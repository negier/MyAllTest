package com.xuebinduan.touchevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //测试不消耗触摸事件，Activity的onTouchEvent会不会被调用？结论是当然会。（todo 记得用手点下触摸屏）
        window.decorView.setOnTouchListener { v, event -> false }
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("TAG","Activity onTouchEvent")
        return super.onTouchEvent(event)
    }
}