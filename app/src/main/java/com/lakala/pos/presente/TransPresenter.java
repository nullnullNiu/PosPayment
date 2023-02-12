package com.lakala.pos.presente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.TransDetailsBean;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

public class TransPresenter extends BasePresenter<ITransView> {



    /**
     * 获取订单条数
     */
    public void countByDeviceId(String info) {
        if (noNetWork()) {
            return;
        }
        modelAPI.countByDeviceId(info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("获取订单条数 接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    String data = jsonObject.get("data").getAsString();
                    getView().countByDeviceIdResult(data);
                } else {
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                LogUtil.e("error,throwable:" + e.getMessage() + ",message:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
            }
        });
    }



 /**
     * 根据订单号查询订单详情
     */
    public void queryOrderByOrderId(String info) {
        if (noNetWork()) {
            return;
        }
        modelAPI.queryOrderByOrderId(info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("根据订单号查询订单详情 接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {

                    TransDetailsBean tranQueryBean =new Gson().fromJson(result,TransDetailsBean.class);
                    getView().queryOrdersDetailsResult(tranQueryBean);

                } else {
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                LogUtil.e("error,throwable:" + e.getMessage() + ",message:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
            }
        });
    }



}
