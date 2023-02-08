package com.lakala.pos.bean;

public class QueryOrderBean {


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

        private String pay_order_no;
        private String out_order_no;
        private String trans_merchant_no;
        private String trans_term_no;
        private String merchant_no;
        private String term_no;
        private String order_info;
        private String trade_type;
        private String log_no;
        private int total_amount;
        private String order_create_time;
        private String order_efficient_time;
        private String pay_status;
        private String extend_field;
        private String remark;
        private String channel_id;
        private String notify_status;
        private String order_status;
        private String pay_time;
        private String user_id1;
        private String user_id2;
        private String crd_flg;
        private String sm_crd_flg;

        public void setPay_order_no(String pay_order_no) {
            this.pay_order_no = pay_order_no;
        }

        public String getPay_order_no() {
            return pay_order_no;
        }

        public void setOut_order_no(String out_order_no) {
            this.out_order_no = out_order_no;
        }

        public String getOut_order_no() {
            return out_order_no;
        }

        public void setTrans_merchant_no(String trans_merchant_no) {
            this.trans_merchant_no = trans_merchant_no;
        }

        public String getTrans_merchant_no() {
            return trans_merchant_no;
        }

        public void setTrans_term_no(String trans_term_no) {
            this.trans_term_no = trans_term_no;
        }

        public String getTrans_term_no() {
            return trans_term_no;
        }

        public void setMerchant_no(String merchant_no) {
            this.merchant_no = merchant_no;
        }

        public String getMerchant_no() {
            return merchant_no;
        }

        public void setTerm_no(String term_no) {
            this.term_no = term_no;
        }

        public String getTerm_no() {
            return term_no;
        }

        public void setOrder_info(String order_info) {
            this.order_info = order_info;
        }

        public String getOrder_info() {
            return order_info;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setLog_no(String log_no) {
            this.log_no = log_no;
        }

        public String getLog_no() {
            return log_no;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public int getTotal_amount() {
            return total_amount;
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

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setExtend_field(String extend_field) {
            this.extend_field = extend_field;
        }

        public String getExtend_field() {
            return extend_field;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setNotify_status(String notify_status) {
            this.notify_status = notify_status;
        }

        public String getNotify_status() {
            return notify_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setUser_id1(String user_id1) {
            this.user_id1 = user_id1;
        }

        public String getUser_id1() {
            return user_id1;
        }

        public void setUser_id2(String user_id2) {
            this.user_id2 = user_id2;
        }

        public String getUser_id2() {
            return user_id2;
        }

        public void setCrd_flg(String crd_flg) {
            this.crd_flg = crd_flg;
        }

        public String getCrd_flg() {
            return crd_flg;
        }

        public void setSm_crd_flg(String sm_crd_flg) {
            this.sm_crd_flg = sm_crd_flg;
        }

        public String getSm_crd_flg() {
            return sm_crd_flg;
        }

    }
}
