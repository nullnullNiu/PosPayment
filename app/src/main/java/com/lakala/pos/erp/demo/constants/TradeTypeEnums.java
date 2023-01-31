package com.lakala.pos.erp.demo.constants;


/**
 * 交易类型枚举
 * @author ouqf
 * @date 2020-07-14
 */


public enum TradeTypeEnums {

    // 订单交易类型编码

    UNKNOWN(0, "暂不指定交易类型"),
    CONSUME(1, "消费"),
    CONSUME_CANCEL(2, "消费撤销"),
    SCAN_PAY(3, "扫码支付"),
    SCAN_REVOCATION(4, "扫码撤销"),
    //SCAN_SUPPLEMENT(5, "扫码补单"),
    PAYMENT_CODE_PAY(6, "付款码支付"),
    FACE_CONSUME(7, "人脸消费"),
    FACE_CONSUME_REVOCATION(8, "人脸消费撤销"),
    //settlement(9, "结算"),
    PRE_AUTH(10, "预授权"),
    PRE_AUTH_REVOCATION(11, "预授权撤销"),
    PRE_AUTH_OK(12, "预授权完成"),
    PRE_AUTH_OK_REVOCATION(13, "预授权完成撤销"),
    SCAN_RETURN(14, "扫码退货"),
    PAYMENT_CODE_RETURN(15, "付款码退货"),
    //PAYMENT_CODE_CLOSE(16,"付款码取消"),
    ;

    private Integer code;
    private String desc;

    TradeTypeEnums(int i, String 暂不指定交易类型) {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
