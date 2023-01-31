package com.lakala.pos.erp.demo;


import com.alibaba.fastjson.JSONObject;
import com.lakala.pos.erp.demo.constants.KeyConstant;
import com.lakala.pos.erp.demo.constants.RequestUrl;
import com.lakala.pos.erp.demo.constants.TradeTypeEnums;
import com.lakala.pos.erp.demo.dto.CreateOrderDTO;
import com.lakala.pos.erp.demo.dto.base.RequestHeader;
import com.lakala.pos.erp.demo.dto.base.TradeBaseDTO;
import com.lakala.pos.erp.demo.utils.BaseUtil;
import com.lakala.pos.erp.demo.utils.HttpUtil;
import com.lakala.pos.erp.demo.utils.SignUtil;

import java.math.BigDecimal;

/**
 * 订单推送示例
 *
 * @author ouqf
 * @date 2020/7/18 15:15
 */
public class CreateOrderApplication {
    public static void main(String[] args) {
        // 1. 请求头
        RequestHeader header = new RequestHeader();
        header.setAppId("1");
        header.setVersion("01");
        header.setNonce(BaseUtil.generateString(8));
        header.setTs(String.valueOf(System.currentTimeMillis() / 1000));

        // 2 请求体
        TradeBaseDTO body = new TradeBaseDTO();

        // 必填
        //body.setTradeType(TradeTypeEnums.SCAN_REVOCATION.getCode());body.setOutRefundNo("erp_demo_1605761684680");
        body.setTradeType(TradeTypeEnums.SCAN_PAY.getCode());
        body.setAmount(new BigDecimal("0.01"));
        //我的生产POS
        body.setMchId("822451058120006");body.setTermSeq("00001104YP610000400827");
        body.setOutTradeNo("erp_demo_" + System.currentTimeMillis());


        // 根据需要填
        body.setAttach("附加数据: ERP-DEMO测试(Nio)");
        body.setPrintInfo("付款码支付");
        body.setExtensionParams(null);
        body.setNotifyUrl("https://test.wsmsd.cn/MP_verify/ccmis_pro/notify");
        body.setOrderInfo("");
//        body.setOutTradeNo("erp_refund_demo_" + System.currentTimeMillis());

        // 创建订单参数
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setHeader(header);
        createOrderDTO.setBody(body);

        // 3 签名
        String signStr = SignUtil.createLinkString(SignUtil.getAllParams(createOrderDTO));
        String sign = SignUtil.signWithPrivateKey(signStr, KeyConstant.PRIVATE_KEY_STR);
        createOrderDTO.setSign(sign);

        // 订单推送请求
        String url = RequestUrl.URL + RequestUrl.ORDER_PUSH;
        System.out.println("请求接口：" + url);
        System.out.println("请求参数：" + JSONObject.toJSONString(createOrderDTO));
//        String responseStr = HttpUtil.doPostJson(url, JSONObject.toJSONString(createOrderDTO));
//        System.out.println("请求结果：" + responseStr);

//        // 结果校验
//        String responseLinkStr = SignUtil.createLinkString(SignUtil.getAllParamsByJson(responseStr));
//        String responseSign = JSONObject.parseObject(responseStr).getString("sign");
//        System.out.println("待签拼接字符串：" + responseLinkStr);
//        boolean res = SignUtil.verifyByPublicKey(responseLinkStr, responseSign, KeyConstant.PLATFORM_CRT);
//        System.err.println("请求结果签名校验：" + res);
    }
}
