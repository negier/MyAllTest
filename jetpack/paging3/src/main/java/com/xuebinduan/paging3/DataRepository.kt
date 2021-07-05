package com.xuebinduan.paging3

import kotlinx.coroutines.delay

class DataRepository {
    suspend fun getData(currentPage:Int):List<User>{
        delay(3000)
        val data = mutableListOf<User>()
        for (i in 0..20) {
            data.add(
                User(
                    System.currentTimeMillis(),
                    "你好${System.currentTimeMillis()}"
                )
            )
        }
        return data
    }
}