package com.haoshi.rxjava.example3.bean;

/**
 * Created by yugu on 2017/1/16.
 */

public class BaseResponse {
    
    private int result;
    private String msg;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
