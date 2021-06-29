package com.xuebinduan.pieadashboardview

import android.content.res.Resources
import android.util.TypedValue

//val Float.dp2px
//get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this, Resources.getSystem().getDisplayMetrics());


fun Float.dp2px():Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)
}