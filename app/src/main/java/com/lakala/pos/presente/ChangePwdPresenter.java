package com.lakala.pos.presente;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.ChangePwdView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.PreferencesUtils;
import com.lakala.pos.utils.ToastUtil;

public class ChangePwdPresenter extends BasePresenter<ChangePwdView> {


    /**
     * 登录
     */
    public void onLogin(String info,String oldP, String newP) {
        if (noNetWork()){
            return;
        }
        modelAPI.onLogin(info,new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("登录 成功" + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    String token = jsonObject.get("data").getAsJsonObject().get("access_token").getAsString();

                    PreferencesUtils.setPreference("access_token", token);

                    subNewPwd(token,oldP,newP);
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
     * 提交修改密码
     */
    public void subNewPwd(String token,String oldP, String newP) {
        if (noNetWork()){
            return;
        }
        modelAPI.changeOwnPassword(token,oldP,newP,new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("修改密码成功 : " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    PreferencesUtils.setPreference("possword", newP);
                    getView().changePwdResult(result);
                } else {
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }
            @Override
            public void onFailure(Throwable e, String s) {
            }
        });
    }









}
