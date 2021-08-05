package com.xuebinduan.design_pattern.visitor;

import java.util.Random;

public class EngineerStaff extends Staff{
    public EngineerStaff(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEngineer(this);
    }

    public int getCodeQuality(){
        return new Random().nextInt(100);
    }
}
