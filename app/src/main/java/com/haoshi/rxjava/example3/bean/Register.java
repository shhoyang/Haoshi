package com.haoshi.rxjava.example3.bean;

/**
 * Created by yugu on 2017/1/16.
 */

public class Register extends BaseResponse {

    private String username;
    private String userpass;
    private String nickname;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Register{" +
                "username='" + username + '\'' +
                ", userpass='" + userpass + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
