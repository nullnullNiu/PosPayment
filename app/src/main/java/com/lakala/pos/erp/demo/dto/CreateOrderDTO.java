package com.lakala.pos.erp.demo.dto;


import com.lakala.pos.erp.demo.dto.base.RequestHeader;
import com.lakala.pos.erp.demo.dto.base.TradeBaseDTO;

/**
 * 下单参数
 * @author ouqf
 * @date 2020/7/14 9:58
 */


public class CreateOrderDTO {

    /** 请求头参数 */
    private RequestHeader header;

    /** 参数体 */
    private TradeBaseDTO body;

    /** 签名 */
    private String sign;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public TradeBaseDTO getBody() {
        return body;
    }

    public void setBody(TradeBaseDTO body) {
        this.body = body;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
