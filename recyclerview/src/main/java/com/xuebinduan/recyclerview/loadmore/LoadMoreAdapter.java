package com.xuebinduan.recyclerview.loadmore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuebinduan.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreAdapter extends RecyclerView.Adapter<LoadMoreAdapter.ViewHolder> implements ShowLoadMoreUI{
    private boolean loadMore;

    private int size;

    private int end;

    private final int DEFAULT_TYPE = 0;
    private final int LOAD_MORE_TYPE = 1;

    public LoadMoreAdapter() {
    }

    public LoadMoreAdapter(boolean loadMore) {
        this.loadMore = loadMore;
    }

    private List<Object> list = new ArrayList<>();
    public void setData(List<Object> list){
        this.list = list;
        size = list.size();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one,parent,false);
        if (viewType == LOAD_MORE_TYPE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadMoreAdapter.ViewHolder holder, int position) {
        if (position < list.size()){
            Object datum = list.get(position);
            //todo do something
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == end){
            return LOAD_MORE_TYPE;
        }
        return DEFAULT_TYPE;
    }

    @Override
    public int getItemCount() {
        end = size;
        if (loadMore) {
            return size + 1;
        }else {
            return size;
        }
    }

    @Override
    public void showLoadMoreUI() {
        loadMore = true;
        notifyDataSetChanged();
    }

    @Override
    public void hideLoadMoreUI() {
        loadMore = false;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
