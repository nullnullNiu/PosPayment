package com.lakala.pos.interfaces;


import com.lakala.pos.bean.TransDetailsBean;

//public interface IHomeView extends ILoadingView<String> {
public interface ITransView {

    void countByDeviceIdResult(String result);

    void queryOrdersDetailsResult(TransDetailsBean bean);
    void getDate(String date ,int type);

}
