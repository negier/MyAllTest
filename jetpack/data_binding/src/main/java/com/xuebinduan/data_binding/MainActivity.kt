package com.xuebinduan.data_binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.databinding.DataBindingUtil
import com.xuebinduan.data_binding.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        //演示的单向绑定
        //todo 双向的有空可以去看：https://developer.android.google.cn/topic/libraries/data-binding/two-way
        thread {
            SystemClock.sleep(3000)
            runOnUiThread {
                // 数据一更新，布局视图也会自动的更新
                binding.user = User("Test", "Mars")
            }
        }

    }
}