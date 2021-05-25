package com.xuebinduan.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.xuebinduan.recyclerview.multilayout.DataProvider;
import com.xuebinduan.recyclerview.multilayout.DataType;
import com.xuebinduan.recyclerview.multilayout.MyAdapter;
import com.xuebinduan.recyclerview.multilayout.data.DataOne;
import com.xuebinduan.recyclerview.multilayout.data.DataTwo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter myAdapter = new MyAdapter();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        List<DataProvider> list = new ArrayList<>();
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));
        list.add(new DataProvider(DataType.DATA_ONE,new DataOne()));
        list.add(new DataProvider(DataType.DATA_TWO,new DataTwo()));

        myAdapter.setData(list);


    }

}