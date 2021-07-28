package com.xuebinduan.intent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xuebinduan.intent.pictures.PictureChooseActivity;


/**
 * todo registerForActivityResult 使用这个需要引入最新的appcompat包，同时它需要在Started生命周期之前如onCreate中被注册。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, PictureChooseActivity.class));
    }


}