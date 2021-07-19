package com.xuebinduan.customviewnestedscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper

class NestedParentLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent {
    private val scrollingParentHelper = NestedScrollingParentHelper(this)

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return true
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.e("TAG", "onNestedPreScroll...")

        val child = target

        val relativeParentChildRight = child.right + dx
        val relativeParentChildLeft = child.left + dx
        val relativeParentChildTop = child.top + dy
        val relativeParentChildBottom = child.bottom + dy

        if (dx > 0) {
            if (relativeParentChildRight > width) {
                val dx = relativeParentChildRight - width
                offsetLeftAndRight(dx)
                consumed[0] += dx
            }
        } else {
            if (relativeParentChildLeft < 0) {
                val dx = relativeParentChildLeft
                offsetLeftAndRight(dx)
                consumed[0] += dx
            }
        }

        if (dy > 0) {
            if (relativeParentChildBottom > height) {
                val dy = relativeParentChildBottom - height
                offsetTopAndBottom(dy)
                consumed[1] += dy
            }
        } else {
            if (relativeParentChildTop < 0) {
                val dy = relativeParentChildTop
                offsetTopAndBottom(dy)
                consumed[1] += dy
            }
        }
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {

        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        scrollingParentHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onStopNestedScroll(target: View) {
        scrollingParentHelper.onStopNestedScroll(target)
    }


}