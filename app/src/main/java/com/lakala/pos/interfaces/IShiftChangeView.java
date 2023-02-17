package com.lakala.pos.interfaces;


import com.lakala.pos.bean.BossInfoBean;
import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.interfaces.base.ILoadingView;

//public interface IShiftChangeView extends ILoadingView<String> {
public interface IShiftChangeView {

    void getBossInfoResult(BossInfoBean bean);
    void loginResult(String token,String name, String phone, String pwd);

}
