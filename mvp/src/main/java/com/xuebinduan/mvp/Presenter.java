package com.xuebinduan.mvp;

import java.util.List;

public class Presenter {
    private IView view;

    public Presenter(IView view) {
        this.view = view;
    }

    public void init(){
        List<String> data = DataCenter.getData();
        view.showData(data);
    }

    interface IView{
        void showData(List<String> data);
    }
}
