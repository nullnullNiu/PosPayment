package com.lakala.pos.erp.demo.dto;

import com.lakala.pos.erp.demo.dto.base.QueryBaseDTO;
import com.lakala.pos.erp.demo.dto.base.RequestHeader;



/**
 * @author ouqf
 * @date 2020/7/15 9:40
 */


public class QueryOrderDTO {

    /** 请求头参数 */
    private RequestHeader header;

    /** 参数体 */
    private QueryBaseDTO body;

    /** 签名 */
    private String sign;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public QueryBaseDTO getBody() {
        return body;
    }

    public void setBody(QueryBaseDTO body) {
        this.body = body;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
