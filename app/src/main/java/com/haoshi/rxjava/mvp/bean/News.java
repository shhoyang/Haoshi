package com.haoshi.rxjava.mvp.bean;

import java.io.Serializable;

/**
 * Created by qihuang on 16-11-5.
 */

public class News implements Serializable {
    
    private String reason;
    private Result result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
