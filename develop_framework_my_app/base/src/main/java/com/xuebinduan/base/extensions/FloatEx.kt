package com.xuebinduan.base.extensions

import kotlin.math.floor

fun Float.format(): String{
    val integer = floor(this)
    return if (this - integer == 0f) {
        toInt().toString()
    } else {
        toString()
    }
}