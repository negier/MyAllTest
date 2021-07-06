package com.xuebinduan.paging3

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagingAdapter = UserAdapter(UserComparator())
        //todo 注意这儿，它会返回一个新的Adapter
        val newAdapter = pagingAdapter.withLoadStateFooter(ExampleLoadStateFooterAdapter())
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = newAdapter

        // Activities can use lifecycleScope directly, but Fragments should instead use
        // viewLifecycleOwner.lifecycleScope.
        lifecycleScope.launchWhenCreated {
            myViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
                //todo submit之后的代码不会被执行
            }
        }

//        lifecycleScope.launch {
//            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
//                progressBar.isVisible = loadStates.refresh is LoadState.Loading
//                retry.isVisible = loadState.refresh !is LoadState.Loading
//                errorMsg.isVisible = loadState.refresh is LoadState.Error
//            }
//        }

        //添加监听
        pagingAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    Log.e("TAG", "is NotLoading")
                }
                is LoadState.Loading -> {
                    Log.e("TAG", "is Loading")
                }
                is LoadState.Error -> {
                    Log.e("TAG", "is Error:")
                    when ((it.refresh as LoadState.Error).error) {
                        is IOException -> {
                            Log.e("TAG", "IOException")
                        }
                        else -> {
                            Log.e("TAG", "others exception")
                        }
                    }
                }
            }
        }


    }

}