package com.xuebinduan.data_binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.databinding.DataBindingUtil
import com.xuebinduan.data_binding.databinding.ActivityMainBinding
import kotlin.concurrent.thread

/**
 * databinding并没有明显区分variable和view的id的名字，一般是variable和id重名了，优先表示id。（不过这时一般不需要在使用id了）
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        //演示的单向绑定
        //todo 双向的有空可以去看：https://developer.android.google.cn/topic/libraries/data-binding/two-way
        //todo 也可以看看view_model项目，里面有双向绑定内容
        thread {
            SystemClock.sleep(3000)
            runOnUiThread {
                // 数据一更新，布局视图也会自动的更新
                binding.user = User("Test", "Mars")
                binding.text = "我心未定，只能勇往无前"
            }
        }

    }
}