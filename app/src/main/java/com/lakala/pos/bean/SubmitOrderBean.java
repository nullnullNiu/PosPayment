package com.lakala.pos.bean;

public class SubmitOrderBean {

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

        private String url;
        private String id;
        private String randomCode;
        private String endingDate;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setRandomCode(String randomCode) {
            this.randomCode = randomCode;
        }

        public String getRandomCode() {
            return randomCode;
        }

        public void setEndingDate(String endingDate) {
            this.endingDate = endingDate;
        }

        public String getEndingDate() {
            return endingDate;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "url='" + url + '\'' +
                    ", id='" + id + '\'' +
                    ", randomCode='" + randomCode + '\'' +
                    ", endingDate='" + endingDate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SubmitOrderBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
