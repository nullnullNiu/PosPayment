package com.lakala.pos.erp.demo.dto.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;

/**
 * 订单推送参数
 * @author ouqf
 * @date 2020/7/14 10:36
 */


public class TradeBaseDTO {
    /** 商户号 */
    @JSONField(name = "mch_id")
    private String mchId;

    /** 终端序列号 */
    @JSONField(name = "term_seq")
    private String termSeq;

    /** 订单金额: 0.01 */
    private BigDecimal amount;

    /** 交易类型 */
    @JSONField(name = "trade_type")
    private Integer tradeType;

    /** 商户订单号 */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /** 商户退款订单号 */
    @JSONField(name = "out_refund_no")
    private String outRefundNo;

    /** 回调地址 */
    @JSONField(name = "notify_url")
    private String notifyUrl;

    /** 其他数据 */
    private String attach;

    /** 订单信息 */
    @JSONField(name = "order_info")
    private String orderInfo;

    /** 打印信息 */
    @JSONField(name = "print_info")
    private String printInfo;

    /** 扩展参数 */
    @JSONField(name = "extension_params")
    private JSONObject extensionParams;


    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getTermSeq() {
        return termSeq;
    }

    public void setTermSeq(String termSeq) {
        this.termSeq = termSeq;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPrintInfo() {
        return printInfo;
    }

    public void setPrintInfo(String printInfo) {
        this.printInfo = printInfo;
    }

    public JSONObject getExtensionParams() {
        return extensionParams;
    }

    public void setExtensionParams(JSONObject extensionParams) {
        this.extensionParams = extensionParams;
    }
}
