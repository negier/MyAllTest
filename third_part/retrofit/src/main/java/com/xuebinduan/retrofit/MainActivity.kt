package com.xuebinduan.retrofit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val USER_ID = "negier"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accessInternet()
    }

    private fun accessInternet(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service: WebService = retrofit.create(WebService::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val user = service.getUser(USER_ID)
            withContext(Dispatchers.Main){
                findViewById<TextView>(R.id.text_login).text = "用户名：${user.login}"
                findViewById<TextView>(R.id.text_location).text = " 地址：${user.location}"
            }
        }
    }
}

