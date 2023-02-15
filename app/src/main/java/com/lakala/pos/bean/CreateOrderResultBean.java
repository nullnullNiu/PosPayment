package com.lakala.pos.bean;

import java.io.Serializable;

public class CreateOrderResultBean  {


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

        private String payOrderNo;
        private String createTime;
        private String merchantOrderNo;
        private String channelId;
        private String merchantNo;

        public void setPayOrderNo(String payOrderNo) {
            this.payOrderNo = payOrderNo;
        }

        public String getPayOrderNo() {
            return payOrderNo;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setMerchantOrderNo(String merchantOrderNo) {
            this.merchantOrderNo = merchantOrderNo;
        }

        public String getMerchantOrderNo() {
            return merchantOrderNo;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getMerchantNo() {
            return merchantNo;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "payOrderNo='" + payOrderNo + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", merchantOrderNo='" + merchantOrderNo + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", merchantNo='" + merchantNo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CreateOrderResultBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
