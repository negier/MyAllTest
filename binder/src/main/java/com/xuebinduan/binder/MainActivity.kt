package com.xuebinduan.binder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * RPC : Across Processes Communication
 * Binder的作用：提供一个全局服务，所谓的全局就是系统中的所有应用程序都可以访问。
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}