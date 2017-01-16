package com.haoshi.rxjava.example3.bean;

/**
 * Created by yugu on 2017/1/16.
 */

public class Register extends BaseResponse {

    private String username;
    private String userpass;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    @Override
    public String toString() {
        return "Register{" +
                "username='" + username + '\'' +
                ", userpass='" + userpass + '\'' +
                '}';
    }
}
