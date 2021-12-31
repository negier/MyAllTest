package com.negier.coordinatorlayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ==========================================================
 *
 * 功能描述：
 *  MainActivity.class
 *  CoordinatorLayout最核心的是Behaivor，即行为
 *
 * @author： NEGIER
 *
 * @date： 2017/11/17 17:01
 *
 * ==========================================================
 */
public class CoordinatorLayoutMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_coordinator_layout);
    }

    /**
     * 最常见的用法
     */
    public void clickOne(View view){
        startActivity(new Intent(this,OneActivity.class));
    }

    /**
     * 和AppBarLayout配合使用
     * @param view
     */
    public void clickTwo(View view){
        startActivity(new Intent(this,TwoActivity.class));
    }

    /**
     * Collapse（折叠）标题栏
     * @param view
     */
    public void clickThree(View view){
        startActivity(new Intent(this,ThreeActivity.class));
    }
}
