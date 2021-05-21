package com.xuebinduan.windowmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 有三种窗口类型被定义在WindowManager类中：
 * 1 应用窗口(层值<99)，Activity的默认窗口为TYPE_APPLICATION
 * 2 子窗口(层值：1000~1999)
 * 3 系统窗口(层值：2000~2999)，有些系统窗口只能被添加一次否则用户会觉得很乱，比如输入法窗口，所以WmS接收到创建窗口的消息时会进行一定的检查。（需要申请权限）
 * WmS在进行窗口叠加时，会动态调整层值。
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}