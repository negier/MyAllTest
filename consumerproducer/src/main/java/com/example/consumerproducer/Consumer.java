package com.example.consumerproducer;

import android.util.Log;

public class Consumer implements Runnable {

    private SyncStack theStack;
    private int num;
    private static int counter = 1;

    public Consumer(SyncStack s) {
        theStack = s;
        num = counter++;
    }

    @Override
    public void run() {
        char c;
        for (int i = 0; i < 200; i++) {
            c = theStack.pop();
            Log.e("TAG","Consumer"+num+":"+c);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
        }
    }
}
