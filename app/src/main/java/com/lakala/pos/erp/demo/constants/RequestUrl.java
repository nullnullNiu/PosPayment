package com.lakala.pos.erp.demo.constants;

/**
 * 请求链接
 * @author ouqf
 * @date 2020/7/14 13:34
 */

public class RequestUrl {

    /** 推送链接 */
    //dev
    //public static String URL = "http://lklcmis.51cpay.com/lklcmis-service";
    //sit
    public static String URL = "https://test.wsmsd.cn/sit/lklcmis-service/";
    //prod
    //public static String URL = "https://cmisapi.lakala.com/lklcmis-service/";


    /** 订单推送接口 */
    public static final String ORDER_PUSH = "/open/v1/trade/push";

    /** 单个记录查询接口 */
    public static final String ORDER_QUERY_ONE = "/open/v1/trade/find";

    /** 批量记录查询接口 */
    public static final String ORDER_QUERY_BATCH = "/open/v1/trade/findByBatch";

}
