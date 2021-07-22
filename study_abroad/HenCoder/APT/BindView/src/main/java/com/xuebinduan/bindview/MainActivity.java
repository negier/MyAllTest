package com.xuebinduan.bindview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.xuebinduan.lib.Binding;
import com.xuebinduan.lib_annotations.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_view)
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView = findViewById(R.id.text_view);
        Binding.bind(this);

        textView.setText("Hi,YOU!");

    }
}