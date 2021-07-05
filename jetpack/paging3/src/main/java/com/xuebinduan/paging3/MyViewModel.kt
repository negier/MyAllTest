package com.xuebinduan.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class MyViewModel : ViewModel() {


    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        ExamplePagingSource()
    }.flow.cachedIn(viewModelScope)


}