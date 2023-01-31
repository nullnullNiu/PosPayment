package com.lakala.pos.http.factory;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2018/3/20
 */

public class StringResponseBodyConverter implements Converter<ResponseBody, String> {

    @Override
    public String convert(ResponseBody value) throws IOException {
        String responseBody = value.string();
//        LogUtil.e("源头============" + responseBody);
//        try {
//            baseBean = new Gson().fromJson(responseBody,BaseBeanTest.class);
//            if (!baseBean.getMsg().equals("")){
//                LogUtil.e("工厂反馈"+baseBean.getMsg());
//                Global.errorMesager = baseBean.getMsg();
//                Global.errorCode = baseBean.getCode();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return responseBody;
    }
}
