package com.xuebinduan.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.negier.loop.bestinfiniteloop.BestInfiniteLoopActivity;
import com.xuebinduan.app.databinding.ActivityMainBinding;
import com.xuebinduan.base64.Base64Activity;
import com.xuebinduan.keyboardimeoptions.IMEOptionsActivity;
import com.xuebinduan.looknewaddfile2.SplashLookNewAddFileActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setTitle("主页");
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ItemBean[] itemBeans = loadData();
        mBinding.recyclerView.setAdapter(new RecyclerViewAdapter(itemBeans));
    }

    private ItemBean[] loadData() {
        ItemBean[] itemBeans = new ItemBean[]{
                new ItemBean(R.mipmap.keyboardimeoptions,getString(R.string.keyboardimeoptions), IMEOptionsActivity.class),
                new ItemBean(R.mipmap.base64,getString(R.string.base64), Base64Activity.class),
                new ItemBean(R.mipmap.looknewaddfile,getString(R.string.looknewaddfile), SplashLookNewAddFileActivity.class),
                new ItemBean(R.mipmap.bestinfiniteloop,getString(R.string.bestinfiniteloop), BestInfiniteLoopActivity.class)
        };
        return itemBeans;
    }

}