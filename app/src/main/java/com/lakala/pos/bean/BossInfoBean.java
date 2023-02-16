package com.lakala.pos.bean;

import java.util.List;

public class BossInfoBean {


    private int code;
    private String message;
    private List<Data> data;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }


    public class Data {
        private String deviceCode;
        private String userCode;
        private String createTime;
        private String updateTime;
        private String bossName;
        private String bossPhone;
        private String password;

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setBossName(String bossName) {
            this.bossName = bossName;
        }

        public String getBossName() {
            return bossName;
        }

        public void setBossPhone(String bossPhone) {
            this.bossPhone = bossPhone;
        }

        public String getBossPhone() {
            return bossPhone;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

    }

}
