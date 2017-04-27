package com.haoshi.shopcart.bean;

import java.util.List;

/**
 * @author HaoShi
 */
public class CategoryBean {

    private String kind;
    private List<GoodsBean> list;
    private int count;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<GoodsBean> getList() {
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
