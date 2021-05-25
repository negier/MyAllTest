package com.xuebinduan.recyclerview.multilayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.xuebinduan.recyclerview.R;
import com.xuebinduan.recyclerview.multilayout.DataProvider;
import com.xuebinduan.recyclerview.multilayout.DataType;
import com.xuebinduan.recyclerview.multilayout.viewholder.BaseViewHolder;
import com.xuebinduan.recyclerview.multilayout.viewholder.DataOneViewHolder;
import com.xuebinduan.recyclerview.multilayout.viewholder.DataTwoViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<DataProvider> list = new ArrayList<>();

    public void setData(List<DataProvider> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one,parent,false);
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_two,parent,false);
        BaseViewHolder holder = null;
        switch (viewType){
            case DataType.DATA_ONE:
                holder = new DataOneViewHolder(view1);
                break;
            case DataType.DATA_TWO:
                holder = new DataTwoViewHolder(view2);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }
}
