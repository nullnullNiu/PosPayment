package com.lakala.pos.http.net;


public interface IScanningApi {

    void onLogin(String info , DataListener<String> listener);

    void companySearch(String info , DataListener<String> listener);

    void onBindDevice(String info , DataListener<String> listener);

    void queryOrders(String info , DataListener<String> listener);

    void queryOrderByOrderId(String info , DataListener<String> listener);

    void invoiceList(String info , DataListener<String> listener);

    void goodsInfoList(String info , DataListener<String> listener);

    void uploaduploadOrder(String info , DataListener<String> listener);

    void createOrder(String info , DataListener<String> listener);

    void changeOwnPassword(String token,String password,String newPassword, DataListener<String> listener);



}
