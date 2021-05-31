package com.xuebinduan.app.main

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import com.xuebinduan.app.R
import com.xuebinduan.base.BaseActivity
import com.xuebinduan.base.extensions.dipToPx

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("TAG","3dp的像素:"+ dipToPx(3f))
    }
}