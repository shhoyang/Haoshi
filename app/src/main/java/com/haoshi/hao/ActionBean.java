package com.haoshi.hao;

/**
 * @author Haoshi
 */

public class ActionBean {

    private String action;
    private Class cls;

    public ActionBean(String action, Class cls) {
        this.action = action;
        this.cls = cls;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }
}
