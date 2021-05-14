package com.xuebinduan.koin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * koin的作用：
 * 1 委托创建ViewModel；解耦
 * 2
 */
class MainActivity : AppCompatActivity() {

    //1
    private val myViewModel by viewModel<MyViewModel>()
    //2
    //it's lazy inject Koin instance
    private val user:User by inject()
    //or, can eager inject Koin instance
    private val user2:User = get()
    private val data:Data = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel.name.observe(this,{
            println("名字1：${it}")
        })

        println("名字2_1：${user.getName()}")
        println("名字2_2：${user2.getName()}")
    }


}