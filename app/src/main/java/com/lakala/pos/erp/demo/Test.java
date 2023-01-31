package com.lakala.pos.erp.demo;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Guoqh on 20.11.18
 * for xxxxxxxx
 */
public class Test {
    public static void main(String[] args) {
        String str1="{\\\"couponInfo\\\":\\\"\\\",\\\"issAddnData\\\":\\\"\\\",\\\"respData\\\":{\\\"activityId\\\":\\\"\\\",\\\"amount\\\":\\\"000000000001\\\",\\\"batchNo\\\":\\\"500000\\\",\\\"clientDiscountAmt\\\":\\\"\\\",\\\"lklOrderNo\\\":\\\"20201117110113230266211368889895\\\",\\\"orderId\\\":\\\"6734486683951194112\\\",\\\"payAmt\\\":\\\"000000000001\\\",\\\"payMode\\\":\\\"ALIPAY\\\",\\\"payTime\\\":\\\"20201117232637\\\",\\\"platformDiscountAmount\\\":\\\"\\\",\\\"seqNo\\\":\\\"500163\\\",\\\"serverDiscountAmt\\\":\\\"\\\",\\\"settlementTotalFee\\\":\\\"1\\\",\\\"shopNo\\\":\\\"822290057340301\\\",\\\"srefno\\\":\\\"211368889895\\\",\\\"termId\\\":\\\"53813286\\\",\\\"tradeNo\\\":\\\"E90D3750D3004A19B21A20AC4599126B\\\",\\\"tradeTime\\\":\\\"20201117232546\\\",\\\"weOrderNo\\\":\\\"2020111722001474431431779439\\\"},\\\"retCode\\\":\\\"000000\\\",\\\"retMsg\\\":\\\"交易成功\\\",\\\"sign\\\":\\\"8BBF6A0AF8731DCFB317631C6BE6F144\\\",\\\"timestamp\\\":1605626797710,\\\"ver\\\":\\\"1.0.0\\\"}";
        JSONObject jsonObject = JSONObject.parseObject(str1);
        System.out.println(jsonObject);
    }
}
