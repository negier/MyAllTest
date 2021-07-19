package com.xuebinduan.customviewnestedscroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//https://www.jianshu.com/p/e333f11f29aa
//https://github.com/szn0212/NestedScrollingExample/tree/cecc194a0a0407793c9a44b6efeed997e789d6bc\
//不是HenCoder的，它根本没讲，只是提了下。 todo 父View继承Parent，子View继承Child。两次，先问父View，再问子View，再问父View，再问子View。
//这是HenCoder的代码地址：https://github.com/rengwuxian/HenCoderPlus/tree/master/15_drag_nestedscroll/src/main/java/com/hencoder/a15_drag_nestedscroll/view/nested_scroll
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}