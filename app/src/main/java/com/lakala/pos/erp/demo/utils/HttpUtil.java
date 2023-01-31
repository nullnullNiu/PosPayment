package com.lakala.pos.erp.demo.utils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * Http请求工具类
 *
 * @author ouqf
 * @date 2020/7/14 11:16
 */
public class HttpUtil {

//    public static String doPostJson(String url, String json) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String resultString = "";
//
//        try {
//            HttpPost httpPost = new HttpPost(url);
//            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
//            entity.setContentEncoding("UTF-8");
//            httpPost.setEntity(entity);
//            response = httpClient.execute(httpPost);
//            resultString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());
//        } catch (Exception var15) {
//            var15.printStackTrace();
//        } finally {
//            try {
//                response.close();
//            } catch (IOException var14) {
//                var14.printStackTrace();
//            }
//        }
//        return resultString;
//    }

}
