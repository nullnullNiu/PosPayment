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

    void transRevoked(String info , DataListener<String> listener);

    void queryOrder(String info , DataListener<String> listener);

    void customerService(String info , DataListener<String> listener);

    void getHelp(String info , DataListener<String> listener);

    void countByDeviceId(String info , DataListener<String> listener);

    void census(String info , DataListener<String> listener);



}
