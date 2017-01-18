package com.haoshi.rxjava.example4.bean;

/**
 * @author HaoShi
 */
public class BaseBean {

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
