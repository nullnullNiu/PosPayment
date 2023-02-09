package com.lakala.pos.presente;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.ICustomerServiceView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

public class CustomerServicePresenter extends BasePresenter<ICustomerServiceView> {

    /**
     * 获取客服页地址
     */
    public void onCustomerService(String info) {
        if (noNetWork()) {
            return;
        }
        modelAPI.customerService(info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("获取客服页地址接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    String data = jsonObject.get("data").getAsString();
                    getView().customerServiceResult(data);
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
