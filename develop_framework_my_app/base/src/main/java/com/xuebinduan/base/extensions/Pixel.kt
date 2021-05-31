package com.xuebinduan.base.extensions

import android.content.res.Resources

//从ViewRootImpl上面趴的，略微改了点
fun dipToPx(dip:Float):Int{
    return ((Resources.getSystem().displayMetrics.density*dip)+0.5f).toInt()
}

val Float.dipToPx
    get() = dipToPx(this)

val Int.dipToPx
    get() = this.toFloat().dipToPx