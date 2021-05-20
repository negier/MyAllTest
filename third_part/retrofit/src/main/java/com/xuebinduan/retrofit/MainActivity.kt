package com.xuebinduan.retrofit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.Logger
import com.ihsanbal.logging.LoggingInterceptor
import com.xuebinduan.retrofit.GlobalConstants.NET_MSG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val USER_ID = "negier"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accessInternet()
    }

    private fun accessInternet(){
        val ERROR:Int = 6
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BASIC).tag(NET_MSG_TAG).log(ERROR).build())
//            .addInterceptor(ShowRawEntityInterceptor()) todo
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
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

