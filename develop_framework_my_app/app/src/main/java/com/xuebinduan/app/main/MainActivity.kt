package com.xuebinduan.app.main

import android.os.Bundle
import android.os.Looper
import com.xuebinduan.app.R
import com.xuebinduan.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Looper.prepare()
    }
}