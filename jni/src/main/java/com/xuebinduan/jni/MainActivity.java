package com.xuebinduan.jni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/**
 * JNI:Java Native Interface，Java本地接口。
 * 1，Java中访问C;
 * 2，C中访问Java.
 *
 * 在产生的C函数中至少会包含两个参数：
 * 1.JNIEnv对象是一个Java虚拟机所运行的环境，相当于Java虚拟机的管家，通过它可以访问Java虚拟机内部的各种对象。
 * 2.jobject是调用该函数的对象，本例中指的就是MainActivity
 */
public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("mytest");
    }

    private int number = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("TAG","神秘数字："+gainNumber());

    }

    public void printSome(){
        Log.e("TAG","C调用Java代码了");
    }

    private native int gainNumber();

}