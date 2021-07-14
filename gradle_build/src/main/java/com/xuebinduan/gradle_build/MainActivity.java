package com.xuebinduan.gradle_build;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Gradle是基于任务task的，它是gradle的最小执行单元，任务之间可以依赖，任务可以做一些操作
 */
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