package com.xuebinduan.view_model

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val paramViewModel by viewModels<ParamViewModel>{
            ParamViewModelFactory("my awesome param")
        }

//            val paramViewModel = ViewModelProvider(this, ParamViewModelFactory("my awesome param")).get(ParamViewModel::class.java)

        Log.e("TAG","查看："+paramViewModel.param)

    }
}