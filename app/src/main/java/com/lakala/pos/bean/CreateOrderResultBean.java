package com.lakala.pos.bean;

public class CreateOrderResultBean {


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

        private String merchant_no;
        private String channel_id;
        private String out_order_no;
        private String order_create_time;
        private String order_efficient_time;
        private String pay_order_no;

        public void setMerchant_no(String merchant_no) {
            this.merchant_no = merchant_no;
        }

        public String getMerchant_no() {
            return merchant_no;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setOut_order_no(String out_order_no) {
            this.out_order_no = out_order_no;
        }

        public String getOut_order_no() {
            return out_order_no;
        }

        public void setOrder_create_time(String order_create_time) {
            this.order_create_time = order_create_time;
        }

        public String getOrder_create_time() {
            return order_create_time;
        }

        public void setOrder_efficient_time(String order_efficient_time) {
            this.order_efficient_time = order_efficient_time;
        }

        public String getOrder_efficient_time() {
            return order_efficient_time;
        }

        public void setPay_order_no(String pay_order_no) {
            this.pay_order_no = pay_order_no;
        }

        public String getPay_order_no() {
            return pay_order_no;
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
