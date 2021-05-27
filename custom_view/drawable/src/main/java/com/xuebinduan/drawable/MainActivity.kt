package com.xuebinduan.drawable

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startActivity(Intent(this,ShowTextCursorDrawableActivity::class.java))

        val view = View(this)
        Log.e("TAG","view toString:"+view.toString())


    }
}