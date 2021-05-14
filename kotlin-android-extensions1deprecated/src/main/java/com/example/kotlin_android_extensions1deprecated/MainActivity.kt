package com.example.kotlin_android_extensions1deprecated

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 已经过时，推荐使用ViewBinding。这是下面Build的输出：
 * The 'kotlin-android-extensions' Gradle plugin is deprecated. Please use this migration guide (https://goo.gle/kotlin-android-extensions-deprecation) to start working with View Binding (https://developer.android.com/topic/libraries/view-binding) and the 'kotlin-parcelize' plugin.
 * 反正它的原理还是findViewById，都是一些上层封装。唯一好奇的点是，它怎么就知道这个id它是什么类型的呢？
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_view.text = "This is Kotlin Android Extensions"
    }
}