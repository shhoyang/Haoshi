package com.haoshi.rxjava.example3.bean;

/**
 * Created by yugu on 2017/1/16.
 */

public class Login extends BaseResponse {
    
    private String id;
    private String username;
    private String userpass;
    private String usersex;
    private String useremail;
    private String nickname;
    private String birthday;
    private String portrait;
    private String signature;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", userpass='" + userpass + '\'' +
                ", usersex='" + usersex + '\'' +
                ", useremail='" + useremail + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", portrait='" + portrait + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
