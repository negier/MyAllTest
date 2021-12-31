package com.negier.loop.bestinfiniteloop;

public class ImageData {
    private int imageUrl;
    private String desc;

    public ImageData(int imageUrl, String desc) {
        this.imageUrl = imageUrl;
        this.desc = desc;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
