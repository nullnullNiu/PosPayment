package com.lakala.pos.presente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.IShiftChangeView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

public class ShiftChangePresenter extends BasePresenter<IShiftChangeView> {


    /**
     * 登录
     */
    public void onLogin(String info) {
        if (noNetWork()) {
            return;
        }
        modelAPI.onLogin(info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("登录成功" + result);

                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    LoginInfo loginInfo = new Gson().fromJson(result, LoginInfo.class);
                    getView().onLoginResult(loginInfo);
                } else {
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                LogUtil.e("error,throwable:" + e.getMessage() + ",msg:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
            }
        });


    }


}
