package com.xuebinduan.app;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xuebinduan.app.databinding.ItemMainRecyclerBinding;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final ItemBean[] itemBeans;

    public RecyclerViewAdapter(ItemBean[] itemBeans) {
        this.itemBeans = itemBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ItemBean itemBean = itemBeans[position];
        holder.mBinding.image.setImageResource(itemBean.getImage());
        holder.mBinding.textDes.setText(itemBean.getTextDes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), itemBean.getTargetActivity()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemBeans.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMainRecyclerBinding mBinding;
        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
