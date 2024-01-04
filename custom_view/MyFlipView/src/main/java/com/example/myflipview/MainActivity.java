package com.example.myflipview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlipView flipView = findViewById(R.id.flip_view);
        flipView.setScrollForwardDirection(false);
        flipView.flipTo(2);

        View button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            flipView.flipTo(5);
        });
    }
}