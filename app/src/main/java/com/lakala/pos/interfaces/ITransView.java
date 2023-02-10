package com.lakala.pos.interfaces;


import com.lakala.pos.bean.TranQueryBean;

//public interface IHomeView extends ILoadingView<String> {
public interface ITransView {

    void queryOrdersResult(TranQueryBean bean);

}
