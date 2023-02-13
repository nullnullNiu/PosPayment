package com.lakala.pos.interfaces;


import com.lakala.pos.bean.SummaryBean;

//public interface IHomeView extends ILoadingView<String> {
public interface ISummaryView {

    void onCensusResult(SummaryBean summaryBean);

    void getDate(String date ,int type);
}
