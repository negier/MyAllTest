package com.xuebinduan.overscroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//https://developer.android.com/training/gestures/scroll
/**
 * OverScroller：
 * startScroll：从指定位置滚动一段指定的距离然后停下，滚动效果与设置的滚动距离、滚动时间、插值器有关，跟离手速度没有关系。一般用于控制 View 滚动到指定的位置
 * fling：从指定位置滑动一段位置然后停下，滚动效果只与离手速度以及滑动边界有关，不能设置滚动距离、滚动时间和插值器。一般用于触摸抬手后继续让 View 滑动一会
 * springBack：从指定位置回弹到指定位置，一般用于实现拖拽后的回弹效果，不能指定回弹时间和插值器
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fling)
    }
}