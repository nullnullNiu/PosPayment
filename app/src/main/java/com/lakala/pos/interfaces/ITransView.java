package com.lakala.pos.interfaces;


import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.bean.TransDetailsBean;

//public interface IHomeView extends ILoadingView<String> {
public interface ITransView {

    void queryOrdersDetailsResult(TransDetailsBean bean);

}
