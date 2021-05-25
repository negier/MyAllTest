package com.xuebinduan.recyclerview.multilayout.viewholder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.xuebinduan.recyclerview.multilayout.DataProvider;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindData(DataProvider dataProvider);
}
