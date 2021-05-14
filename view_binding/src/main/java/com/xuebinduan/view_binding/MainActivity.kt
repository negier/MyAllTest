package com.xuebinduan.view_binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xuebinduan.view_binding.databinding.ActivityMainBinding


/**
 * ViewBinding还是要通过setContentView来设置视图；
 * 唯一的方便点就是：我们可以不用findViewById，直接通过binding.id就可以获取到它
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val contentView = binding.root
        setContentView(contentView)

        binding.textView.text = "This is ViewBinding"
    }
}