package com.xuebinduan.customviewnestedscroll.nest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xuebinduan.customviewnestedscroll.R

class NestedParentLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent2 {

    private val nestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private var imageView: ImageView? = null
    private var imageHeight: Int = 0
    private var recyclerView: RecyclerView? = null
    private val linearLayoutManager =  LinearLayoutManager(context)

    init {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        obtainImageViewInfo()
        initChildRecyclerView()
    }

    private fun obtainImageViewInfo() {
        imageView = findViewById<ImageView>(R.id.avator)
        imageView?.let {
            post {
                imageHeight = it.height
            }
        }
    }

    private fun initChildRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = MyRecyclerAdapter()
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        nestedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    /**
     * dy ???????????????????????????
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.e("TAG", "dx$dx,dy$dy")
        if (dy<0){
            //????????????????????????recyclerView????????????????????????????????????????????????
            if (linearLayoutManager.findFirstCompletelyVisibleItemPosition()!=0){
                return
            }
        }
        imageView?.let { view ->
            var height = (view.height + (-dy))
            if (height < 0) {
                height = 0
            } else if (height > imageHeight){
                height = imageHeight
            } else {
                consumed[1] = dy
            }
            view.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height)
        }
        //todo ?????????????????????RecyclerView?????????????????????RecyclerView??????dy?????????????????????????????????????????????View??????????????????????????????move?????????dy??????????????????????????????????????????
        this@NestedParentLayout.requestLayout()
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
    }

}