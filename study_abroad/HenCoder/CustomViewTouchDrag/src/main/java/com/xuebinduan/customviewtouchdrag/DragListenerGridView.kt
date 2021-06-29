package com.xuebinduan.customviewtouchdrag

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.core.view.children

private const val COLUMNS = 2
private const val ROWS = 3

/**
 * 半透明，全局，跨进程的
 * 针对内容的移动，可以附加数据，关注点最主要的是数据
 */
class DragListenerGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private var viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private val dragListener = HenDragListener()
    private val orderedChildren = mutableListOf<View>()
    private lateinit var draggedView:View

    init {
        isChildrenDrawingOrderEnabled = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for(child in children){
            orderedChildren.add(child)
            child.setOnLongClickListener { v ->
                draggedView = v
                //todo 第一个参数data不是随时随地都能拿到（松手的时候才能拿到），它可以跨进程（场景例子：微信和照片软件分屏，可以将图片往微信里拖，前提是照片软件支持拖拽）；
                //todo 第二个参数DragShadowBuilder是定义拖起来那个物体的样子
                //todo 第三个参数localState是个本地数据，随时随地都能拿到的数据
                v.startDrag(null, DragShadowBuilder(v),v,0)
                false
            }
            child.setOnDragListener(dragListener)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY))
        setMeasuredDimension(specWidth,specHeight)
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft:Int
        var childTop : Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for((index,child) in children.withIndex()){
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(0,0, childWidth, childHeight)
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }
    private inner class HenDragListener:OnDragListener{
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when(event.action){
                DragEvent.ACTION_DRAG_STARTED -> if (event.localState === v){
                    v.visibility = View.INVISIBLE
                }
                DragEvent.ACTION_DRAG_ENTERED -> if (event.localState !== v){
                    sort(v)
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    v.visibility = View.VISIBLE
                }
            }
            return true
        }
    }
    private fun sort(targetView:View){
        var draggedIndex = -1
        var targetIndex = -1
        for((index,child) in orderedChildren.withIndex()){
            if(targetView === child){
                targetIndex = index
            }else if(draggedView === child){
                draggedIndex = index
            }
        }
        orderedChildren.removeAt(draggedIndex)
        orderedChildren.add(targetIndex,draggedView!!)
        var childLeft:Int
        var childTop:Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for((index,child) in orderedChildren.withIndex()){
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat())
                .setDuration(150)
        }
    }
}