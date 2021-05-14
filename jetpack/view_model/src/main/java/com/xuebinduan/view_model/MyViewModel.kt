package com.xuebinduan.view_model

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread


class MyViewModel: ViewModel() {
    val userName:MutableLiveData<String> = MutableLiveData()
    val gender:MediatorLiveData<Boolean> = MediatorLiveData() //这个可以观察其它LiveData，其它数据改变了会得到更新
    init {
        thread {
            Thread.sleep(3000)
            userName.postValue("嘿嘿")
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}