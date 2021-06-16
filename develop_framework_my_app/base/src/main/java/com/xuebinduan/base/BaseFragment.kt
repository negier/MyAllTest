package com.xuebinduan.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG","当前类名："+javaClass.simpleName+"，完整："+javaClass.canonicalName)
    }
}