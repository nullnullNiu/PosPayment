package com.lakala.pos.bean;

public class UserInfoBean {

    private int type;
    private String loginName;
    private String password;

    public UserInfoBean() {
    }

    public UserInfoBean(int type, String loginName, String password) {
        this.type = type;
        this.loginName = loginName;
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "type=" + type +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
