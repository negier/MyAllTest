package com.negier.coordinatorlayoutdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ==========================================================
 *
 * 功能描述：
 *  TwoActivity.class
 *  AppBarLayout
 *      常见的就是给它设置app:layout_scrollFlags="scroll|enterAlways"
 *      呈现出来的结果是我们在上拉的时候View会隐藏，下拉的时候View会出来
 *
 * @author： NEGIER
 *
 * @date： 2017/11/17 16:21
 *
 * ==========================================================
 */
public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rc_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new TwoAdapter());
    }
}
