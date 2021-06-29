package com.xuebinduan.measurelayout

import android.content.Context
import android.util.AttributeSet
import android.view.View

class OneHundredView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(100,100)
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, l+100, t+100)
    }
}