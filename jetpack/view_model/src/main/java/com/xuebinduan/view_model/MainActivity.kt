package com.xuebinduan.view_model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {

    lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MyViewModel::class.java)
        myViewModel.userName.observe(this,{
            println("数据改变了userName：${it}")
        })

        myViewModel.gender.addSource(myViewModel.userName,{
            //userName数据更新了，gender设为false
            myViewModel.gender.postValue(false)
        })
        myViewModel.gender.observe(this,{
            println("数据改变了gender：${it}")
        })

    }

}