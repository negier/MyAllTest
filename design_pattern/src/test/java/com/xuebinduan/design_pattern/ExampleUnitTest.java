package com.xuebinduan.design_pattern;

import org.junit.Test;

import static org.junit.Assert.*;

import com.xuebinduan.design_pattern.visitor.CEOVisitor;
import com.xuebinduan.design_pattern.visitor.EngineerStaff;
import com.xuebinduan.design_pattern.visitor.ManagerStaff;
import com.xuebinduan.design_pattern.visitor.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testVisitor(){
        List<Staff> staffList = new ArrayList<>();
        staffList.add(new EngineerStaff("小王"));
        staffList.add(new EngineerStaff("小陈"));
        staffList.add(new ManagerStaff("大军"));
        staffList.add(new ManagerStaff("大梁"));

        for(Staff staff:staffList){
            staff.accept(new CEOVisitor());
        }
    }
}