package com.lakala.pos.interfaces;


import com.lakala.pos.bean.QueryOrderBean;
import com.lakala.pos.interfaces.base.ILoadingView;

public interface IRevokeView extends ILoadingView<String> {
    void queryOrderResult(QueryOrderBean bean);
    void transRevokedResult(String result);
}
