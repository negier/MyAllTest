package com.xuebinduan.recyclerview.multilayout.viewholder;

import android.view.View;

import com.xuebinduan.recyclerview.multilayout.data.DataOne;
import com.xuebinduan.recyclerview.multilayout.DataProvider;

public class DataOneViewHolder extends BaseViewHolder {
    public DataOneViewHolder( View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(DataProvider dataProvider) {
        DataOne dataOne = dataProvider.getDataOne();

    }
}
