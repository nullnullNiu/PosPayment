package com.lakala.pos.http;


import com.lakala.pos.http.factory.AddCookiesInterceptor;
import com.lakala.pos.http.factory.ReceivedCookiesInterceptor;
import com.lakala.pos.http.factory.StringConverterFactory;
import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.Query;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class BuildApi {

    public static final String BASE_URL = com.lakala.pos.BuildConfig.SERVER_HOST;

    private static final int DEFAULT_TIMEOUT = 50;

    private Retrofit retrofit;

    private ServiceAPI mServiceAPI;

    /**
     * 构造私有方法
     */
    private BuildApi() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuild = new OkHttpClient.Builder();
//        httpClientBuild.addInterceptor(new AddCookiesInterceptor());//添加Cookie的拦截器
//        httpClientBuild.addInterceptor(new ReceivedCookiesInterceptor());//获取Cookie的拦截器
        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别


        //设置 Debug Log 模式
        httpClientBuild.addInterceptor(loggingInterceptor);

        httpClientBuild.retryOnConnectionFailure(true);
        httpClientBuild.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuild.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuild.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuild.build())
//                   .addConverterFactory(GsonConverterFactory.create())
//                   .addInterceptor(ResponseInterceptor())//获取Cookie的拦截器
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mServiceAPI = retrofit.create(ServiceAPI.class);
    }

    /**
     * 构造私有方法
     */
    private BuildApi(String singletUrl) {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuild = new OkHttpClient.Builder();
        httpClientBuild.addInterceptor(new AddCookiesInterceptor());//添加Cookie的拦截器
        httpClientBuild.addInterceptor(new ReceivedCookiesInterceptor());//获取Cookie的拦截器
        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

        //设置 Debug Log 模式
        httpClientBuild.addInterceptor(loggingInterceptor);

        httpClientBuild.retryOnConnectionFailure(true);
        httpClientBuild.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuild.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuild.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuild.build())
//                   .addConverterFactory(GsonConverterFactory.create())
//                   .addInterceptor(ResponseInterceptor())//获取Cookie的拦截器
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(singletUrl)
                .build();
        mServiceAPI = retrofit.create(ServiceAPI.class);
    }

    /**
     * 访问http创建单例
     */
    public static class SingletonHolder {
        private static final BuildApi Instance = new BuildApi();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static BuildApi getInstance() {
        return SingletonHolder.Instance;
//        return new BuildApi();
    }


    /**
     * 创建一个文件FormBody
     *
     * @param fieldName
     * @param file
     * @return
     */
    public static MultipartBody.Part createFilePart(String fieldName, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(fieldName, file.getName(), requestFile);
        return body;
    }




    /**
     * 登录
     */
    public void onLogin(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.onLogin(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 根据公司名称获取抬头信息
     */
    public void companySearch(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.companySearch(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 绑定设备
     */
    public void onBindDevice(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.onBindDevice(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 根据订单状态查询订单
     */
    public void queryOrders(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.queryOrders(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 根据订单号查询订单详情
     */
    public void queryOrderByOrderId(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.queryOrderByOrderId(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 查询已开发票
     */
    public void invoiceList(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.invoiceList(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }



    /**
     * 商品查询
     */
    public void goodsInfoList(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.goodsInfoList(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }



    /**
     * 上送订单
     */
    public void uploaduploadOrder(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.uploaduploadOrder(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }




    /**
     * 创建订单
     */
    public void createOrder(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.createOrder(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }





    /**
     * 修改密码
     */
    public void changeOwnPassword(String token,String password,String newPassword, Subscriber<String> stringSubscriber) {
        mServiceAPI.changeOwnPassword(token,password,newPassword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }





    /**
     * 交易撤销
     */
    public void transRevoked(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.transRevoked(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }






    /**
     * 撤销之前先查询订单
     */
    public void queryOrder(String info, Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.queryOrder(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }





}
