package com.xuebinduan.app;

import android.app.Activity;

public class ItemBean {
    private int image;
    private String textDes;
    private Class<? extends Activity> targetActivity;

    public ItemBean(int image, String textDes, Class<? extends Activity> targetActivity) {
        this.image = image;
        this.textDes = textDes;
        this.targetActivity = targetActivity;
    }

    public int getImage() {
        return image;
    }

    public String getTextDes() {
        return textDes;
    }

    public Class<? extends Activity> getTargetActivity() {
        return targetActivity;
    }
}
