package com.xuebinduan.base64;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;

/**
 * 能后退的基类Activity
 */
public class BackActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolBar().setDisplayHomeAsUpEnabled(true);//系统的返回箭头
        getToolBar().setDisplayShowHomeEnabled(true);//系统的返回箭头是否可见
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
