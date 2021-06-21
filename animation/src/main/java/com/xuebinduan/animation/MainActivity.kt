package com.xuebinduan.animation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null) as ViewGroup
        setContentView(contentView)

        thread {
            SystemClock.sleep(3000)

            val view = View(this)
            view.setBackgroundColor(Color.BLUE)
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500)
            runOnUiThread {
                contentView.addView(view)
            }
        }
    }
}