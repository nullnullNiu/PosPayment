package com.lakala.pos.interfaces;


import com.google.gson.Gson;
import com.lakala.pos.bean.BossInfoBean;

//public interface IHomeView extends ILoadingView<String> {
public interface ISetingView {

    void addBossInfoResult(String result,int statues);

    void getBossInfoResult(String result);

}
