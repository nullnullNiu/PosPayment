package com.lakala.pos.bean;

import java.util.List;

public class LoginInfo {


    private int code;
    private String message;
    private Data data;

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

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }


    public class Data {

        private String token;
        private String access_token;
        private List<String> permitCodes;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setPermitCodes(List<String> permitCodes) {
            this.permitCodes = permitCodes;
        }

        public List<String> getPermitCodes() {
            return permitCodes;
        }

    }


}
