package com.lakala.pos.erp.demo.dto.base;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 请求头
 * @author ouqf
 * @date 2020/7/14 10:36
 */

public class RequestHeader {

    /** 应用id */
    @JSONField(name = "app_id")
    private String appId;

    /** 随机数 */
    private String nonce;

    /** 发送请求的时间 */
    private String ts;

    /** 调用的接口版本 */
    private String version;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
