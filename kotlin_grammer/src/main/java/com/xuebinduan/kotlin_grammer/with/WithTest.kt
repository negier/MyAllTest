package com.xuebinduan.kotlin_grammer.with

import android.graphics.Color
import android.view.View
import android.widget.TextView

class WithTest {
    companion object{
        fun test(view: TextView){
            //with和apply咋选呀
            //with的最后一行是返回值；apply的返回值是调用者它的本身
            val text = with(view){
                text = "with"
                setTextColor(Color.RED)
                "这是返回值"
            }
            view.apply {

            }
        }
    }
}