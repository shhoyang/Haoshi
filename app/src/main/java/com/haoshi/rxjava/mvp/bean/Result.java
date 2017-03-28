package com.haoshi.rxjava.mvp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qihuang on 16-11-6.
 */

public class Result implements Serializable {
    private String stat;
    private List<Data> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> dataList) {
        this.data = dataList;
    }
}
