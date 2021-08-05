package com.xuebinduan.design_pattern.visitor;

import java.util.Random;

public class ManagerStaff extends Staff{
    public ManagerStaff(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitManager(this);
    }

    public int getProducts(){
        return new Random().nextInt(10);
    }
}
