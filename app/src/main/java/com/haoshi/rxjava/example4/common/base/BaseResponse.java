package com.haoshi.rxjava.example4.common.base;


import com.haoshi.rxjava.example4.bean.News;

/**
 * @author HaoShi
 */

public class BaseResponse<T> {
    
    public String reason;
    public T result;

    public boolean isStatus() {
        return ((News.ResultBean) result).getStat().equals("1");
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
