package com.xuebinduan.kotlin_grammer.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job1 = launch {
        delay(1_000)
        println("Hello ")
    }
    job1.join()
    val job2 = launch {
        println("World!")
    }
    GlobalScope.launch {
    }

}