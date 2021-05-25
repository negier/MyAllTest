package com.xuebinduan.recyclerview.multilayout;

import com.xuebinduan.recyclerview.multilayout.data.DataOne;
import com.xuebinduan.recyclerview.multilayout.data.DataTwo;

/**
 * 数据提供类：专门提供各个布局所需的数据
 */
public class DataProvider {
    private int type;

    private DataOne dataOne = new DataOne();
    private DataTwo dataTwo = new DataTwo();

    public DataProvider(@DataType int type, Object data) {
        this.type = type;
        if (data instanceof DataOne){
            this.dataOne = (DataOne) data;
        }
        if (data instanceof DataTwo){
            this.dataTwo = (DataTwo) data;
        }
    }

    public int getType() {
        return type;
    }

    public DataOne getDataOne() {
        return dataOne;
    }

    public DataTwo getDataTwo() {
        return dataTwo;
    }
}
