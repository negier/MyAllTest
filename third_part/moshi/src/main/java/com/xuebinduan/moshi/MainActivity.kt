package com.xuebinduan.moshi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.xuebinduan.moshi.data.Node
import com.xuebinduan.moshi.data.Root
import com.xuebinduan.moshi.data.node.AudioNode


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //java object to json
        var root = Root()
        root.data.add(Node())
        root.data.add(AudioNode())

        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter = moshi.adapter(Root::class.java)
        val json = jsonAdapter.toJson(root)
        println(json)


    }
}