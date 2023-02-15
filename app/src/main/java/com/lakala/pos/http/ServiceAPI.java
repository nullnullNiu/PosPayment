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

    /**
     * 交易撤销
     *
     */

    @POST("agency/pay/revoked")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> transRevoked(@Body RequestBody requestBody);


    /**
     * 撤销之前先查询订单
     *
     */

    @POST("agency/pay/queryOrder")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> queryOrder(@Body RequestBody requestBody);


    /**
     * 获取客服页地址
     *
     */

    @POST("agency/common/customerService")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> customerService();
//    Observable<String> customerService(@Body RequestBody requestBody);



    /**
     * 获取帮助页地址
     *
     */

    @POST("agency/common/help")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> getHelp();
//    Observable<String> getHelp(@Body RequestBody requestBody);





    /**
     * 获取订单条数
     *
     */

    @POST("agency/order/countByDeviceId")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> countByDeviceId(@Body RequestBody requestBody);





    /**
     * 交易汇总
     *
     */

    @POST("agency/pay/census")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> census(@Body RequestBody requestBody);




    /**
     * 添加老板信息
     *
     */

    @POST("agency/boss/add")
    Observable<String> addBossInfo(@Header("TOKEN") String token,
                                   @Body RequestBody requestBody);



    /**
     * 根据设备号查询账户列表
     *
     *
     */
    @POST("agency/boss/queryByDivice")
    Observable<String> queryByDivice(@Header("TOKEN") String token,
                                     @Body RequestBody requestBody);




}
