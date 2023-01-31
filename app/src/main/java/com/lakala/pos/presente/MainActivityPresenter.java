package com.lakala.pos.presente;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.IDeviceBindView;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.utils.LogUtil;

public class MainActivityPresenter extends BasePresenter<IHomeView> {


    /**
     * 退出登录
     */
    public void loginOutUserPresenter() {
        if (noNetWork()){
            return;
        }
        modelAPI.loginOutUserApi(new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("退出登录成功" + result);
            }

            @Override
            public void onFailure(Throwable e, String s) {

            }
        });
    }






}
