package com.xuebinduan.recyclerview;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuebinduan.recyclerview.loadmore.LoadMoreAdapter;
import com.xuebinduan.recyclerview.multilayout.DataProvider;
import com.xuebinduan.recyclerview.multilayout.DataType;
import com.xuebinduan.recyclerview.multilayout.MyAdapter;
import com.xuebinduan.recyclerview.multilayout.data.DataOne;
import com.xuebinduan.recyclerview.multilayout.data.DataTwo;
import com.xuebinduan.recyclerview.util.EmptyViewUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        MyAdapter myAdapter = new MyAdapter();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int textSize = 60;
        int itemDecorationHeight = 140;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setTextSize(textSize);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int restore = c.save();
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View view = parent.getChildAt(i);
//                    c.drawText("你好", 0, view.getTop()-itemDecorationHeight/2+textSize/2, paint);
                    c.drawRect(0, view.getBottom(), view.getRight(), view.getBottom() + 140, paint);
                }
                c.restoreToCount(restore);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 140;
            }
        });
        recyclerView.setAdapter(myAdapter);
        EmptyViewUtil.bindEmptyView(recyclerView,R.layout.empty_view);

        List<DataProvider> list = new ArrayList<>();
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE, new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO, new DataTwo()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.setData(list);
                    }
                });
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.removeAllData();
                    }
                });
            }
        }).start();

        */

        LoadMoreAdapter adapter = new LoadMoreAdapter(true);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 10;
            }
        });
        recyclerView.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.hideLoadMoreUI();
                    }
                });
            }
        }).start();


    }
}