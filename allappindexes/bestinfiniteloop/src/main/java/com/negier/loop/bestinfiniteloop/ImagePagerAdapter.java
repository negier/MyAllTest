package com.negier.loop.bestinfiniteloop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private List<ImageData> imageDataList = new ArrayList<>();

    public ImagePagerAdapter(List<ImageData> imageDataList) {
        //重组数据
        this.imageDataList = imageDataList;

    }

    @Override
    public int getCount() {
        return imageDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageData imageData = imageDataList.get(position);

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_loop_page, null, false);
        ImageView image = view.findViewById(R.id.image);
        image.setImageResource(imageData.getImageUrl());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
