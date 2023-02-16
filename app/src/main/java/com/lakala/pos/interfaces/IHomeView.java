package com.lakala.pos.interfaces;


import com.lakala.pos.bean.CreateOrderResultBean;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.interfaces.base.ILoadingView;

//public interface IHomeView extends ILoadingView<String> {
public interface IHomeView {

    void getQaCategoryList(TranQueryBean bean);

    void getCreateQrderResult(int term_type , CreateOrderResultBean bean);

    void uploaduploadOrderResult(String result);

}
