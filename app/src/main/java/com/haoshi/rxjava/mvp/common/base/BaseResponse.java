package com.haoshi.rxjava.mvp.common.base;


import com.haoshi.rxjava.mvp.bean.Result;

/**
 * @author HaoShi
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
