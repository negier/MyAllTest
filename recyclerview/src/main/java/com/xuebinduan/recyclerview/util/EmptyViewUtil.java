package com.xuebinduan.recyclerview.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class EmptyViewUtil {
    public static void bindEmptyView(RecyclerView recyclerView, @LayoutRes int emptyView){
        View view = LayoutInflater.from(recyclerView.getContext()).inflate(emptyView, ((ViewGroup) recyclerView.getParent()), false);
        bindEmptyView(recyclerView,view);
    }

    public static void bindEmptyView(RecyclerView recyclerView, View emptyView){
        ViewGroup parentViewGroup = (ViewGroup) recyclerView.getParent();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter!=null){
            parentViewGroup.addView(emptyView);
            if (recyclerView.getLayoutParams() instanceof ConstraintLayout.LayoutParams){
                //todo
                Log.e("TAG","EmptyView可能显示会有问题，目前不太支持父布局为ConstraintLayout和Constraint.LayoutParams");
            }else{
                emptyView.setLayoutParams(recyclerView.getLayoutParams());
            }
            emptyView.setLayoutParams(recyclerView.getLayoutParams());
            RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    showEmptyViewOrNot(adapter,recyclerView,emptyView);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    showEmptyViewOrNot(adapter,recyclerView,emptyView);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                    showEmptyViewOrNot(adapter,recyclerView,emptyView);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    showEmptyViewOrNot(adapter,recyclerView,emptyView);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    showEmptyViewOrNot(adapter,recyclerView,emptyView);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    showEmptyViewOrNot(adapter,recyclerView,emptyView);
                }
            };
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }else{
            throw new RuntimeException("This RecyclerView has no adapter, you must call setAdapter first!");
        }
    }

    private static void showEmptyViewOrNot(RecyclerView.Adapter adapter, RecyclerView recyclerView, View emptyView){
        if (adapter.getItemCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}
