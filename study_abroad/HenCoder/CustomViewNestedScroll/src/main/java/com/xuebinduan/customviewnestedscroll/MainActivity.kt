package com.xuebinduan.customviewnestedscroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//https://www.jianshu.com/p/e333f11f29aa
//https://github.com/szn0212/NestedScrollingExample/tree/cecc194a0a0407793c9a44b6efeed997e789d6bc\

/**
 * NestedScrolling机制：
 * 当子view开始滚动之前，可以通知父view，让其先于自己进行滚动;
 * 子view滚动之后，还可以通知 父view 继续滚动。
 * 在这套交互机制中，child 是动作的发起者，parent 只是接受回调并作出响应。
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_nest)



    }
}