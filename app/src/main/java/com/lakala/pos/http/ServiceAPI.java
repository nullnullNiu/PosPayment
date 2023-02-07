package com.lakala.pos.http;


import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;



public interface ServiceAPI {

    /**
     * 登录
     *
     */

    @POST("agency/sys/login/loginMobile")
    Observable<String> onLogin(@Body RequestBody requestBody);


    /**
     * 绑定
     *
     */

    @POST("agency/device/bind")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> onBindDevice(@Body RequestBody requestBody);


    /**
     * 根据公司名称获取抬头信息
     *
     */

    @POST("agency/invoice/companySearch")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> companySearch(@Body RequestBody requestBody);


    /**
     * 根据订单状态查询订单
     *
     */

    @POST("agency/order/queryOrders")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> queryOrders(@Body RequestBody requestBody);


    /**
     * 根据订单号查询订单详情
     *
     */

    @POST("agency/order/queryOrderByOrderId")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> queryOrderByOrderId(@Body RequestBody requestBody);


    /**
     * 查询已开发票
     *
     */

    @POST("agency/invoice/invoiceList")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> invoiceList(@Body RequestBody requestBody);


    /**
     * 商品查询
     *
     */

    @POST("agency/goods/goodsInfoList")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> goodsInfoList(@Body RequestBody requestBody);


    /**
     * 上送订单
     *
     */

    @POST("order/uploaduploadOrder")
    @Headers({"TOKEN:XGYQRSQJKDYXGPQJPBQV"})
    Observable<String> uploaduploadOrder(@Body RequestBody requestBody);


    /**
     * 创建订单
     *
     */

    @POST("agency/pay/createOrder")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> createOrder(@Body RequestBody requestBody);



    /**
     * 修改密码
     *
     */
    @GET("agency/sys/user/changeOwnPassword")
    Observable<String> changeOwnPassword(@Header("TOKEN") String token,
                                         @Query("password") String password,
                                         @Query("newPassword") String newPassword);


}
