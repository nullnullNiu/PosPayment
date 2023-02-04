package com.lakala.pos.presente;

import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.ChangePwdView;
import com.lakala.pos.utils.LogUtil;

public class ChangePwdPresenter extends BasePresenter<ChangePwdView> {


    /**
     * 提交修改密码
     */
    public void subNewPwd(String oldP, String newP) {
        if (noNetWork()){
            return;
        }
        modelAPI.changeOwnPassword(oldP,newP,new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("修改密码成功 : " + result);
                getView().changePwdResult(result);
            }

            @Override
            public void onFailure(Throwable e, String s) {

            }
        });
    }









}
