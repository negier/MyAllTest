package com.xuebinduan.dynamicjni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.xuebinduan.jni.R;


/**
 * 动态注册貌似只能用C++，不然会报错：member reference base type 'JavaVM' (aka 'const struct JNIInvokeInterface *') is not a structure or union
 * 原因，目前找到的是：https://github.com/krngrvr09/NDKTest2
 * 它说的原因原话是：If you go further into jnni.h, you'll see that JNIEnv is defined differently for C and C++ and that is where the error comes from.。
 * 简单说就是在c和c++中定义不一样导致的。
 */
public class SecondActivity extends AppCompatActivity {

    static {
        System.loadLibrary("mytestdanamic");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.e("TAG","打印动态注册获取的值："+gainNumber());

    }

    private native int gainNumber();

}