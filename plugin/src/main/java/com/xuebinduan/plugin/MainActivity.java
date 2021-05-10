package com.xuebinduan.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File apk = new File(getCacheDir()+"/plugin.apk");
        if (!apk.exists()){
            try (Source source = Okio.source(getAssets().open("plugin/plugin.apk"));
                 BufferedSink sink = Okio.buffer(Okio.sink(apk))){
                sink.writeAll(source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (apk.exists()) {
            try {
                DexClassLoader classLoader = new DexClassLoader(apk.getPath(),getCacheDir().getPath(),null,null);
                Class utilsClass = classLoader.loadClass("com.xuebinduan.myalltest.Utils");
                Constructor utilsConstructor = utilsClass.getDeclaredConstructors()[0];
                utilsConstructor.setAccessible(true);
                Object utils = utilsConstructor.newInstance();
                Method sayMethod = utilsClass.getDeclaredMethod("say");
                //为了调用私有方法
                sayMethod.setAccessible(true);
                String str = (String) sayMethod.invoke(utils);

                ((TextView) findViewById(R.id.text_view)).setText(str);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

}