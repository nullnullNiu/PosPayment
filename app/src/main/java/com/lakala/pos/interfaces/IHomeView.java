package com.lakala.pos.interfaces;


import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.interfaces.base.ILoadingView;

//public interface IHomeView extends ILoadingView<String> {
public interface IHomeView {

    void getQaCategoryList(TranQueryBean bean);

}
