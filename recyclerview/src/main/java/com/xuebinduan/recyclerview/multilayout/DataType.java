package com.xuebinduan.recyclerview.multilayout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@IntDef({DataType.DATA_ONE, DataType.DATA_TWO})
@Retention(RetentionPolicy.SOURCE)
public @interface DataType {
    int DATA_ONE = 1;
    int DATA_TWO = 2;
}
