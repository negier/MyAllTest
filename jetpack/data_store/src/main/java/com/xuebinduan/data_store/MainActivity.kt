package com.xuebinduan.data_store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.edit

/**
 * https://developer.android.google.cn/topic/libraries/architecture/datastore
 *
 * todo 连个demo我暂时都写不了
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }

    suspend fun putStrInDS(){
        dataStore.edit {
        }
    }

}