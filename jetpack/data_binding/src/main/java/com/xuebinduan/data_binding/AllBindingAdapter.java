package com.xuebinduan.data_binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class AllBindingAdapter {
    @BindingAdapter("goneUnless")
    public static void goneUnless(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
