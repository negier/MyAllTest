package com.study.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.study.coroutine.lua.Coroutine
import com.study.coroutine.lua.Dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            testLuaImitate()
        }

    }

    private suspend fun testLuaImitate(){
        val producer = Coroutine.create<Unit,Int>(Dispatcher()){
            for(i in 0..3){
                Log.e("TAG","${Thread.currentThread().name} send $i")
                yield(i)
            }
            200
        }
        val consumer = Coroutine.create<Int,Unit>(Dispatcher()) {param:Int ->
            Log.e("TAG","${Thread.currentThread().name} start $param")
            for(i in 0..3){
                val value = yield(Unit)
                Log.e("TAG","${Thread.currentThread().name} receive $value")
            }
        }
        //循环搭的引擎
        while(producer.isActive && consumer.isActive){
            val result = producer.resume(Unit)
            consumer.resume(result)
        }
    }

}