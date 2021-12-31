package com.xuebinduan.keyboardimeoptions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * singleLine = true，会自动带上点击键盘换到下一行的效果
 */
class IMEOptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imeoptions)
    }
}