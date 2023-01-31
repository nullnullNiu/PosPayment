package com.lakala.pos.erp.demo.dto.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author ouqf
 * @date 2020/7/15 9:41
 */

public class QueryBaseDTO {

    /** 商户号 */
    @JSONField(name = "mch_id")
    private String mchId;

    /** 终端序列号 */
    @JSONField(name = "term_seq")
    private String termSeq;

    /** 单个查询的商户订单号 */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /** 批量查询的商户订单号 */
    @JSONField(name = "out_trade_no_array")
    private List<String> outTradeNoArray;

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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public List<String> getOutTradeNoArray() {
        return outTradeNoArray;
    }

    public void setOutTradeNoArray(List<String> outTradeNoArray) {
        this.outTradeNoArray = outTradeNoArray;
    }
}
