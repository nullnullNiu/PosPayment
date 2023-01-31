package com.lakala.pos.presente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.BindDeviceInfoBean;
import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.IDeviceBindView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

public class DeviceBindingPresenter extends BasePresenter<IDeviceBindView> {


    /**
     * 根据公司名称获取抬头信息
     */
    public void companySearch(String info) {
        if (noNetWork()){
            return;
        }
        modelAPI.companySearch(info,new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("根据公司名称获取抬头信息 成功" + result);
                getView().companySearch(result);
            }

            @Override
            public void onFailure(Throwable e, String s) {

            }
        });
    }

    /**
     * 绑定设备
     */
    public void onBindDevice(String info) {
        if (noNetWork()){
            return;
        }
        modelAPI.onBindDevice(info,new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("绑定设备 成功" + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {

                    getView().bindResult(result);
                } else {
                    String msg = jsonObject.get("msg").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {

            }
        });
    }



}
