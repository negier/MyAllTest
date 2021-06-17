package com.xuebinduan.view_model

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.xuebinduan.view_model.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        myViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MyViewModel::class.java)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        binding.viewModel = myViewModel
        // todo 需要设置这个，才能双向绑定，ViewModel的数据变了才会自动更新布局，布局的数据变了才会自动反应到ViewModel
        binding.lifecycleOwner = this

        myViewModel.userName.observe(this,{
            println("数据改变了userName：${it}")
        })
        thread {
            SystemClock.sleep(9000)
            runOnUiThread {
                binding.textName.text = "哈哈哈"
            }
        }

        myViewModel.gender.addSource(myViewModel.userName,{
            //userName数据更新了，gender设为false
            myViewModel.gender.postValue(false)
        })
        myViewModel.gender.observe(this,{
            println("数据改变了gender：${it}")
        })


        //todo livedata是可以单独使用的
        val individualLiveData = MutableLiveData<String>()
        individualLiveData.observe(this,{
            println("LiveData是可以不用在ViewModel里的，是可以单独使用的，数据改变了：${it}")
        })
        thread {
            SystemClock.sleep(3000)
            individualLiveData.postValue("我爱你")
        }

    }

}