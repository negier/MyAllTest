package com.xuebinduan.recyclerview.multilayout;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.xuebinduan.recyclerview.R;
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

    public void removeAllData(){
        this.list.clear();
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        switch (viewType){
            case DataType.DATA_ONE:
                holder = new DataOneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one,parent,false));
                break;
            case DataType.DATA_TWO:
                holder = new DataTwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_two,parent,false));
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
