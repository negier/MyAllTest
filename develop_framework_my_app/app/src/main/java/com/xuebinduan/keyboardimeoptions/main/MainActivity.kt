package com.xuebinduan.keyboardimeoptions.main

import android.os.Bundle
import android.util.Log
import com.xuebinduan.keyboardimeoptions.R
import com.xuebinduan.base.BaseActivity
import com.xuebinduan.base.extensions.dipToPx

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("TAG","3dp的像素:"+ dipToPx(3f))
    }
}