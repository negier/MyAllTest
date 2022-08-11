package com.example.consumerproducer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SyncStack stack = new SyncStack();

        Producer p1 = new Producer(stack);
        Thread proT1 = new Thread(p1);
        proT1.start();

        Producer p2 = new Producer(stack);
        Thread proT2 = new Thread(p2);
        proT2.start();

        Consumer c1 = new Consumer(stack);
        Thread consT1 = new Thread(c1);
        consT1.start();

        Consumer c2 = new Consumer(stack);
        Thread consT2 = new Thread(c2);
        consT2.start();
    }

}