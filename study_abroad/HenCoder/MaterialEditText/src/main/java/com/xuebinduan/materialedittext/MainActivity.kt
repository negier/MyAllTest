package com.xuebinduan.materialedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import androidx.core.view.postDelayed

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val met = findViewById<MaterialEditText>(R.id.met)
        met.postDelayed(3000){
            met.useFloatingLabel = false
        }

        Handler().post {

        }

    }
}