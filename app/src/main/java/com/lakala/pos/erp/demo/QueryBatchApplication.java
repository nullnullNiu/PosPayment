package com.lakala.pos.erp.demo;

import com.alibaba.fastjson.JSONObject;
import com.lakala.pos.erp.demo.constants.KeyConstant;
import com.lakala.pos.erp.demo.constants.RequestUrl;
import com.lakala.pos.erp.demo.dto.QueryOrderDTO;
import com.lakala.pos.erp.demo.dto.base.QueryBaseDTO;
import com.lakala.pos.erp.demo.dto.base.RequestHeader;
import com.lakala.pos.erp.demo.utils.BaseUtil;
import com.lakala.pos.erp.demo.utils.HttpUtil;
import com.lakala.pos.erp.demo.utils.SignUtil;
import java.util.Arrays;
import java.util.List;

/**
 * 多个订单查询示例
 *
 * @author ouqf
 * @date 2020/7/21 13:37
 */
public class QueryBatchApplication {
    public static void main(String[] args) {

        // 1. 请求头
        RequestHeader header = new RequestHeader();
        header.setAppId("h6thsi28");
        header.setVersion("01");
        header.setNonce(BaseUtil.generateString(8));
        header.setTs(String.valueOf(System.currentTimeMillis() / 1000));

        // 2 请求体
        QueryBaseDTO body = new QueryBaseDTO();
        body.setMchId("822290153112834");
        body.setTermSeq("00001104YP610000353693");
        List<String> outTradeNoArray = Arrays.asList("1599530918897", "1599530918897");
        body.setOutTradeNoArray(outTradeNoArray);
        // 查询参数
        QueryOrderDTO queryOrderDTO = new QueryOrderDTO();
        queryOrderDTO.setHeader(header);
        queryOrderDTO.setBody(body);

        // 3 签名
        String signStr = SignUtil.createLinkString(SignUtil.getAllParams(queryOrderDTO));
        String sign = SignUtil.signWithPrivateKey(signStr, KeyConstant.PRIVATE_KEY_STR);
        queryOrderDTO.setSign(sign);

        // 批量查询请求
        String url = RequestUrl.URL + RequestUrl.ORDER_QUERY_BATCH;
        System.out.println("请求接口：" + url);
        System.out.println("请求参数：" + JSONObject.toJSONString(queryOrderDTO));
//        String responseStr = HttpUtil.doPostJson(url, JSONObject.toJSONString(queryOrderDTO));
//        System.out.println("请求结果：" + responseStr);

//        // 结果校验
//        String responseLinkStr = SignUtil.createLinkString(SignUtil.getAllParamsByJson(responseStr));
//        String responseSign = JSONObject.parseObject(responseStr).getString("sign");
//        System.out.println("待签拼接字符串：" + responseLinkStr);
//        boolean res = SignUtil.verifyByPublicKey(responseLinkStr, responseSign, KeyConstant.PLATFORM_CRT);
//        System.err.println("请求结果签名校验：" + res);
    }
}
