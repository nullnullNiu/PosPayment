package com.lakala.pos.bean;

public class RevokeInfoBean {

    String merchant_no; //结算商户号
    String term_no; //终端号
    String origin_out_trade_no;//商户订单号
    String origin_trade_no;//支付流水号
    String requestIp;//ip，必送

    public String getMerchant_no() {
        return merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getTerm_no() {
        return term_no;
    }

    public void setTerm_no(String term_no) {
        this.term_no = term_no;
    }

    public String getOrigin_out_trade_no() {
        return origin_out_trade_no;
    }

    public void setOrigin_out_trade_no(String origin_out_trade_no) {
        this.origin_out_trade_no = origin_out_trade_no;
    }

    public String getOrigin_trade_no() {
        return origin_trade_no;
    }

    public void setOrigin_trade_no(String origin_trade_no) {
        this.origin_trade_no = origin_trade_no;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

}
