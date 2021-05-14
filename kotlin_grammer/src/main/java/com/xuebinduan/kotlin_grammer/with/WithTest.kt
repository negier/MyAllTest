package com.xuebinduan.kotlin_grammer.with

import android.graphics.Color
import android.view.View
import android.widget.TextView

fun main(){
    //最末返回返回值
    val str = with(StringBuilder()){
        append("hi")
        append(" world")
        toString()
    }

    if (str is String){
        println(str)
    }
}