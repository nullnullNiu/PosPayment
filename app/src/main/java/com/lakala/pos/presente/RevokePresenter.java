package com.lakala.pos.presente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.EnterpriseInfoBean;
import com.lakala.pos.bean.QueryOrderBean;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.IRevokeView;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

public class RevokePresenter extends BasePresenter<IRevokeView> {



    /**
     * 订单查询
     */
    public void queryOrder(String info) {
        if (noNetWork()){
            return;
        }
        getView().showLoading();
        modelAPI.queryOrder(info,new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("订单查询 服务端返回信息：" + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    QueryOrderBean bean = new Gson().fromJson(result,QueryOrderBean.class);
                    getView().queryOrderResult(bean);
                }else {
                    getView().hideLoading();
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                LogUtil.e("error,throwable:" + e.getMessage() + ",message:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
                getView().hideLoading();
            }
        });
    }


    /**
     * 交易撤销
     */
    public void transRevoked(String info) {
        if (noNetWork()){
            return;
        }
        modelAPI.transRevoked(info,new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("交易撤销 服务的返回信息：" + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    getView().transRevokedResult(result);
                }else {
                    getView().hideLoading();
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                getView().hideLoading();
                LogUtil.e("error,throwable:" + e.getMessage() + ",message:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
            }
        });
    }



}
