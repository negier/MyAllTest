package com.xuebinduan.gradle_build;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BuildConfig构建后就会生成
        String applicationId = BuildConfig.APPLICATION_ID;
        boolean debug = BuildConfig.DEBUG;

    }
}