package com.xuebinduan.keyboardimeoptions.extensions

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes

/**
 * 四处搜罗的好用的扩展，来源有ANKO
 */

inline fun <reified T : View> Activity.findOptional(@IdRes id: Int): T? = findViewById(id) as? T

inline val Activity.contentView: View?
    get() = findOptional<ViewGroup>(android.R.id.content)?.getChildAt(0)