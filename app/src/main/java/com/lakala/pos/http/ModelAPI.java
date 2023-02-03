package com.lakala.pos.http;


import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.http.net.IScanningApi;
import rx.Subscriber;


public class ModelAPI implements IScanningApi {

    @Override
    public void onLogin(String info, DataListener<String> listener) {
        BuildApi.getInstance().onLogin(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "登录换班 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });

    }


    @Override
    public void companySearch(String info, DataListener<String> listener) {
        BuildApi.getInstance().companySearch(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "根据公司名称获取抬头信息 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });

    }

    @Override
    public void onBindDevice(String info, DataListener<String> listener) {
        BuildApi.getInstance().onBindDevice(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "根据公司名称获取抬头信息 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });

    }

    @Override
    public void queryOrders(String info, DataListener<String> listener) {
        BuildApi.getInstance().queryOrders(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "根据订单状态查询订单 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void queryOrderByOrderId(String info, DataListener<String> listener) {
        BuildApi.getInstance().queryOrderByOrderId(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "根据订单号查询订单详情 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }


    @Override
    public void invoiceList(String info, DataListener<String> listener) {
        BuildApi.getInstance().invoiceList(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "查询已开发票 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void goodsInfoList(String info, DataListener<String> listener) {
        BuildApi.getInstance().goodsInfoList(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "商品查询 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }


    @Override
    public void uploaduploadOrder(String info, DataListener<String> listener) {
        BuildApi.getInstance().uploaduploadOrder(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "上送订单 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

}
