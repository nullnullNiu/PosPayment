package com.lakala.pos.interfaces;


import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.interfaces.base.ILoadingView;

public interface IShiftChangeView extends ILoadingView<String> {

    void onLoginResult(LoginInfo info);

}
