package com.xuebinduan.animation.view

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.xuebinduan.animation.dp2px
val provinces = listOf(
    "北京市",
    "天津市",
    "河北省",
    "山西省",
    "内蒙古自治区",
    "辽宁省",
    "吉林省",
    "黑龙江省",
    "上海市",
    "江苏省",
    "浙江省",
    "安徽省",
    "福建省",
    "江西省",
    "山东省",
    "宁夏回族自治区",
    "新疆维吾尔自治区"
)
class ProvinceView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 80.dp2px()
        textAlign = Paint.Align.CENTER
    }
    var province = "北京市"
    set(value) {
        field = value
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(province,width/2f,height/2f,paint)
    }
}

class ProvinceEvaluator: TypeEvaluator<String>{
    override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
        val startIndex = provinces.indexOf(startValue)
        val endIndex = provinces.indexOf(endValue)
        val currentIndex = startIndex + ((endIndex - startIndex) * fraction).toInt()
        return provinces[currentIndex]
    }

}