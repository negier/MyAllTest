package com.xuebinduan.kotlin_grammer.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.NullPointerException

/**
 * 只要flow一emit数据，我这面就能collect到数据
 *
 * 无限数据流应用：可以收到比如数据库的实时更新
 */
fun main(){
    var data = mutableListOf<String>()

    // 创建数据流
    val flow = flow {
        while (true) {
            emit("Hi ${System.currentTimeMillis()}")
        }
    }
    // 修改数据流
    .map {
        //转换：变换前的值->变换后的值。会改变流
        it->it.removeRange(4,it.length)
    }.onEach {
        //遍历流中的值。不会改变流
        data.add(it)
    }.flowOn(Dispatchers.IO)
    // 捕获异常
    .catch{ exception -> println("异常发生了，信息：$exception") }

    // 收集数据流
    GlobalScope.launch {
        flow.collect {
            println(it)
        }
    }

    Thread.sleep(50000)
    println("数据量：${data.size}")
}