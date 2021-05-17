package com.xuebinduan.drawable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xuebinduan.drawable.text_cursor_drawable.ShowTextCursorDrawableActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this,ShowTextCursorDrawableActivity::class.java))

    }
}