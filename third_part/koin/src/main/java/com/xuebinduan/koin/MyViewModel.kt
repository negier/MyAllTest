package com.xuebinduan.koin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class MyViewModel:ViewModel() {

    var name = MutableLiveData<String>()

    init{
        thread {
            Thread.sleep(3000)
            name.postValue("你好")
        }
    }

}