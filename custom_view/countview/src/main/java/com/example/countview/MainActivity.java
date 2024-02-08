package com.example.countview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountView mCountView = findViewById(R.id.count_view);
        mCountView.setText("需在",10,"天前完成  12-21");

        View buttonAdd = findViewById(R.id.button_add);
        View buttonSub = findViewById(R.id.button_sub);
        buttonAdd.setOnClickListener(v -> {
            mCountView.changeCount(2);
        });
        buttonSub.setOnClickListener(v -> {
            mCountView.changeCount(-2);
        });

    }
}