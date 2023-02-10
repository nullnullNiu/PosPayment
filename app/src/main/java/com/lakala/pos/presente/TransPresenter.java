package com.lakala.pos.presente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

public class TransPresenter extends BasePresenter<ITransView> {



    /**
     * 根据订单状态查询订单
     */
    public void queryOrders(String info) {
        if (noNetWork()) {
            return;
        }
        modelAPI.queryOrders(info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("根据订单状态查询订单 接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {

                    TranQueryBean tranQueryBean =new Gson().fromJson(result,TranQueryBean.class);
                    getView().queryOrdersResult(tranQueryBean);

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
