package com.example.consumerproducer;

import java.util.Vector;

public class SyncStack {

    private Vector buffer = new Vector(400,200);

    public synchronized char pop(){
        char c;

        while(buffer.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

        c = (Character) buffer.remove(buffer.size() - 1);
        return c;
    }

    public synchronized void push(char c){
        Character charObj = new Character(c);
        buffer.addElement(charObj);
        this.notify();
    }

}
