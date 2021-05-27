package com.xuebinduan.io;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    public String SOURCE_FILE_PATH ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SOURCE_FILE_PATH = getExternalFilesDir("").getAbsolutePath()+File.separator+"text.txt";
        Log.e("TAG","查看路径："+new File(SOURCE_FILE_PATH).getAbsolutePath());



    }
}