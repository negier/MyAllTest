package com.example.consumerproducer;

import android.util.Log;

public class Producer implements Runnable{

    private SyncStack theStack;
    private int num;
    private static int count = 1;

    public Producer(SyncStack s) {
        theStack = s;
        num = count++;
    }

    @Override
    public void run() {
        char c;
        for(int i = 0; i < 200; i++){
            c = (char)(Math.random()*26 + 'A');
            theStack.push(c);
            Log.e("TAG","Producer" + num + ":" + c);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
        }
    }

}
