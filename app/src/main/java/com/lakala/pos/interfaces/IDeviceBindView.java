package com.lakala.pos.interfaces;


import com.lakala.pos.bean.BindDeviceInfoBean;
import com.lakala.pos.bean.EnterpriseInfoBean;

//public interface IHomeView extends ILoadingView<String> {
public interface IDeviceBindView {

    void companySearch(EnterpriseInfoBean bean);

    void bindResult(String result);

    void loginResult(String result);

}
