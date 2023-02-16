package com.lakala.pos.presente;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.BossInfoBean;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.ISetingView;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.PreferencesUtils;
import com.lakala.pos.utils.ToastUtil;

public class SetPresenter extends BasePresenter<ISetingView> {


    /**
     * 添加老板信息
     */
    public void addBossInfo(String info, int statues) {
        if (noNetWork()) {
            return;
        }
        String access_token = PreferencesUtils.getPreferenceString("access_token", "");
        if (TextUtils.isEmpty(access_token)) {
            ToastUtil.showToast("添加失败，请重新绑定设备。");
            return;
        }
        modelAPI.addBossInfo(access_token, info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求服务端添加老板返回信息：" + result);

                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    getView().addBossInfoResult(result, statues);
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


    /**
     * 根据设备号查询账户列表
     */
    public void queryByDivice(String info) {
        if (noNetWork()) {
            return;
        }
        String access_token = PreferencesUtils.getPreferenceString("access_token", "");
        if (TextUtils.isEmpty(access_token)) {
//            ToastUtil.showToast("添加失败，请重新绑定设备。");
            return;
        }
        modelAPI.queryByDivice(access_token, info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求服务端根据设备号查询账户列表返回信息：" + result);

                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    getView().getBossInfoResult(result);
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
