package com.xuebinduan.recyclerview.multilayout.viewholder;

import android.view.View;

import com.xuebinduan.recyclerview.multilayout.DataProvider;
import com.xuebinduan.recyclerview.multilayout.data.DataTwo;

public class DataTwoViewHolder extends BaseViewHolder {
    public DataTwoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(DataProvider dataProvider) {
        DataTwo dataTwo = dataProvider.getDataTwo();

    }
}
