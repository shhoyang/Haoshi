package com.haoshi.rxjava.mvp.common.basebean;


import com.haoshi.rxjava.mvp.bean.Result;

/**
 * Created by qihuang on 16-11-6.
 */

public class BaseResponse<T> {
    public String reason;
    public T result;

    public boolean isStatus() {
        return ((Result) result).getStat().equals("1");
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
