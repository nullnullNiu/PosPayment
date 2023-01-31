package com.lakala.pos.http;


import com.lakala.pos.BuildConfig;
import com.lakala.pos.http.factory.AddCookiesInterceptor;
import com.lakala.pos.http.factory.ReceivedCookiesInterceptor;
import com.lakala.pos.http.factory.StringConverterFactory;
import com.lakala.pos.utils.PreferencesUtils;
import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2018/3/20
 */

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
     * 一次性获取全部行政区域
     *
     * @param stringSubscriber
     */
    public void getRegionAll(int level, Subscriber<String> stringSubscriber) {
        mServiceAPI.getRegionAll(level)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 用户登录
     *
     * @param stringSubscriber
     */
    public void getUserLogin(String phoneNumber, String verifyCode, String passwordMd5, int force, String deviceId, Byte role, Subscriber<String> stringSubscriber) {
        mServiceAPI.getUserLogin(phoneNumber, verifyCode, passwordMd5, force, deviceId, role)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 用户一键登录
     *
     * @param stringSubscriber
     */
    public void getOneKeyLogin(String oneClickToken, int force, Subscriber<String> stringSubscriber) {
        mServiceAPI.getOneKeyLogin(oneClickToken, force)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 退出登录
     *
     * @param stringSubscriber
     */
    public void loginOutUser(Subscriber<String> stringSubscriber) {
        mServiceAPI.loginOutUser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * APP版本更新
     *
     * @param type
     * @param stringSubscriber
     */
    public void versionAppUpdate(String type, String version, String packageName, Subscriber<String> stringSubscriber) {
        mServiceAPI.versionAppUpdate(type, version, packageName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 评价调解员
     *
     * @param caseId
     * @param mediatorId
     * @return
     */
    public void evaluateMediator(long caseId, long appealId, long mediatorId, float score, String content, Subscriber<String> stringSubscriber) {
        mServiceAPI.evaluateMediator(caseId, appealId, mediatorId, score, content)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 评价案件
     *
     * @param caseId
     * @return
     */
    public void evaluateCase(long caseId, String content, Subscriber<String> stringSubscriber) {
        mServiceAPI.evaluateCase(caseId, content)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 终止诉求
     *
     * @param reason
     * @return
     */
    public void appealStop(long appealId, String reason, Subscriber<String> stringSubscriber) {
        mServiceAPI.appealStop(appealId, reason)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取终止诉讼的原因列表
     *
     * @param
     * @return
     */
    public void appealStopReason(Subscriber<String> stringSubscriber) {
        mServiceAPI.appealStopReason()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取调解员列表
     *
     * @param addressID
     * @return
     */
    public void getMediatorList(Integer addressID, Subscriber<String> stringSubscriber) {
        mServiceAPI.getMediatorList(addressID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取案件6大类
     *
     * @return
     */
    public void getSixCategories(Subscriber<String> stringSubscriber) {
        mServiceAPI.getSixCategories()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取案件详情
     *
     * @param caseId
     * @return
     */
    public void getCaseDetails(long caseId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getCaseDetails(caseId, "free")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取案件列表
     *
     * @param businessType
     * @return
     */
    public void getCaseList(String categoryId, long businessType, int pageNum, int pageSize, Subscriber<String> stringSubscriber) {
        mServiceAPI.getCaseList(categoryId, businessType, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 发送问题
     *
     * @param question
     * @return
     */
    public void sendQuestion(long caseId, String question, int byVoice, Subscriber<String> stringSubscriber) {
        mServiceAPI.sendQuestion(caseId, question, byVoice)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 即问即答发送问题
     *
     * @param question
     * @return
     */
    public void sendAskQuestion(long caseId, String question, int byVoice, Subscriber<String> stringSubscriber) {
        mServiceAPI.sendAskQuestion(caseId, question, byVoice)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 发送问题 到AI接口
     *
     * @param question
     * @return
     */
    public void sendQuestionAIPlus(long caseId, String question, Subscriber<String> stringSubscriber) {
        mServiceAPI.sendQuestionAIPlus(caseId, question)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 创建订单
     *
     * @param amountFen
     * @return
     */
    public void createOrder(int amountFen, String payType, Subscriber<String> subscriber) {
        mServiceAPI.createOrder(amountFen, payType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 支付案件应付费用
     *
     * @param
     * @return
     */
    public void payCaseOrder(long caseId, Subscriber<String> subscriber) {
        mServiceAPI.payCaseOrder(caseId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 支付案件应付费用
     *
     * @param
     * @return
     */
    public void payCaseSpecifyType(long caseId, int payType, Subscriber<String> subscriber) {
        mServiceAPI.payCaseSpecifyType(caseId, payType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 支付宝异步通知
     *
     * @return
     */
    public void aliPayNotification(Subscriber<String> subscriber) {
        mServiceAPI.aliPayNotification()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取钱包明细
     *
     * @return
     */
    public void getWalletDetails(Subscriber<String> subscriber) {
        mServiceAPI.getWalletDetails()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 微信登录
     *
     * @return
     */
    public void wxLogin(String wxCode, int force, String deviceId, Subscriber<String> subscriber) {
        mServiceAPI.wxLogin(wxCode, force, deviceId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 微信登录补全手机号
     *
     * @return
     */
    public void wxLoginAddPhoneNum(String wxCode, String phoneNum, String verifyCode, int force, String deviceId, Byte role, Subscriber<String> subscriber) {
        mServiceAPI.wxLoginAddPhoneNum(wxCode, phoneNum, verifyCode, force, deviceId, role)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取钱包余额
     *
     * @return
     */
    public void getWalletBalance(Subscriber<String> subscriber) {
        mServiceAPI.getWalletBalance()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 钱包提现
     *
     * @param amount
     * @return
     */
    public void walletCash(Integer amount, String alipayAccount, String bankCardNum, String bankName, String peopleName, Subscriber<String> subscriber) {
        mServiceAPI.walletCash(amount, alipayAccount, bankCardNum, bankName, peopleName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 获取诉求内容
     *
     * @return
     */
    public void getAppealContent(long caseId, Subscriber<String> subscriber) {
        mServiceAPI.getAppealContent(caseId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 添加诉讼相关信息
     *
     * @param caseId
     * @return
     */
    public void addAppeal(long caseId, String appealName, String appealPhoneNum, String appealSex, String appealAge, String appealCaseAddress, String appealContent, Integer regionId, Subscriber<String> subscriber) {
        mServiceAPI.addAppeal(caseId, appealName, appealPhoneNum, appealSex, appealAge, appealCaseAddress, appealContent, regionId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 选择案件调解员并推送消息给调解员
     *
     * @param caseId
     * @return
     */
    public void selectMediator(long caseId, long mediatorId, Subscriber<String> subscriber) {
        mServiceAPI.selectMediator(caseId, mediatorId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
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
     * 获取调解员信息
     *
     * @param caseId
     * @param
     * @param
     */
    public void getForMediatorDetail(long caseId, Subscriber<String> subscriber) {
        mServiceAPI.getMediatorDetial(caseId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 结束调解
     *
     * @return
     */
    public void appealOver(long appealId, Subscriber<String> stringSubscriber) {
        mServiceAPI.appealOver(appealId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取首页banner
     *
     * @return
     */
    public void getBanner(String token, int type, Subscriber<String> stringSubscriber) {
        mServiceAPI.getBannerUrl(token, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取首页推荐律师
     *
     * @return
     */
    public void getLawyer(int optimizated, int recommended, int pageSize, int pageNum, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyer(optimizated, recommended, pageSize, pageNum)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 更新意见书分享次数
     *
     * @return
     */
    public void getCasePpinionSharedNum(Long caseId, int shareType, Subscriber<String> stringSubscriber) {
        mServiceAPI.getCaseOpinionSharedNum(caseId, shareType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * IM对话：确认/拒绝结束邀请
     *
     * @param imId
     * @param status
     * @param stringSubscriber
     */
    public void getLawyerConsultIm(Long imId, int status, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultIm(imId, status)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * IM：对话 获取最新咨询意见书
     *
     * @param imId
     * @param stringSubscriber
     */
    public void getLawyerConsultImDetail(Long imId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultImDetail(imId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * IM对话：获取咨询意见书列表
     *
     * @param stringSubscriber
     */
    public void getLawyerConsultListOpinion(Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultListOpinion()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * IM对话：发送咨询意见书
     *
     * @param stringSubscriber
     */
    public void getLawyerConsultSendOpinion(long imId, long caseId, String opinionUrl, String pdfUrl, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultSendOpinion(imId, caseId, opinionUrl, pdfUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * IM对话：发送咨询意见书
     *
     * @param stringSubscriber
     */
    public void getLawyerConsultRedBag(long imId, Integer amountFen, String message, long toUserId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultRedBag(imId, amountFen, message, toUserId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * IM对话：获取律师详情
     *
     * @param stringSubscriber
     */
    public void getLawyerDetail(long lawyerId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerDetail(lawyerId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }
//GET /mediator/case/type

    /**
     * 获取律师列表
     *
     * @param regionId
     * @param label
     * @param keyword
     * @param stringSubscriber
     */
    public void getLawyerList(int regionId, String label, String keyword, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerList(regionId, label, keyword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取纠纷类别
     *
     * @param stringSubscriber
     */
    public void getJiuFenType(Subscriber<String> stringSubscriber) {
        mServiceAPI.getJiuFenType()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * IM对话：获取雇主评价列表
     *
     * @param stringSubscriber
     */
    public void getLawyerEvaluateList(long lawyerId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerEvaluateList(lawyerId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * IM对话：创建预约面谈
     *
     * @param imId
     * @param toUserId
     * @param content
     * @param realName
     * @param phoneNum
     * @param time
     * @param districtId
     * @param address
     * @param subscriber
     */
    public void getLawyerConsultInterview(long imId, long toUserId, String content, String realName, String phoneNum, String time, Integer districtId, String address, Subscriber<String> subscriber) {
        mServiceAPI.getLawyerConsultInterview(imId, toUserId, content, realName, phoneNum, time, districtId, address)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * IM对话：改变预约面谈状态
     *
     * @param interviewId
     * @param status
     * @param stringSubscriber
     */
    public void getLawyerConsultInterviewStatus(long interviewId, String status, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultInterviewStatus(interviewId, status)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 发起咨询
     */
    public void faQiZiXun(Long caseId, int amountFen, String appeal, Subscriber<String> stringSubscriber) {
        mServiceAPI.faQiZiXun(caseId, amountFen, appeal)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 创建IM
     *
     * @param caseId
     * @param toUserId
     * @param stringSubscriber
     */
    public void createIM(Long caseId, Long toUserId, Long orderId, int type, Subscriber<String> stringSubscriber) {
        mServiceAPI.createIM(caseId, toUserId, orderId, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取我的法律顾问
     *
     * @param stringSubscriber
     */
    public void getLegalAdviser(Subscriber<String> stringSubscriber) {
        mServiceAPI.getLegalAdviser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /* 雇佣法律顾问
     * @param stringSubscriber
     */
    public void getGwLegalAdviser(long lawyerId, Integer amountFen, Subscriber<String> stringSubscriber) {
        mServiceAPI.getGwLegalAdviser(lawyerId, amountFen)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取红包状态
     *
     * @param redBagId
     * @param stringSubscriber
     */
    public void getLawyerConsultRedBagStatus(Long redBagId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultRedBagStatus(redBagId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 追加红包
     */
    public void addMoney(Long orderId, int amountFen, Subscriber<String> stringSubscriber) {
        mServiceAPI.addMoney(orderId, amountFen)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 取消咨询订单
     *
     * @param orderId
     */
    public void cancelStartZiXun(Long orderId, Subscriber<String> stringSubscriber) {
        mServiceAPI.cancelStartZiXun(orderId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 换一个律师
     *
     * @param orderId
     * @param reason
     */
    public void changeLawyer(Long orderId, String reason, Subscriber<String> stringSubscriber) {
        mServiceAPI.changeLawyer(orderId, reason)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取预约面谈列表
     *
     * @param stringSubscriber
     */
    public void getinterviewIMList(Subscriber<String> stringSubscriber) {
        mServiceAPI.getinterviewIMList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 对意见书和咨询过程评价
     *
     * @param caseId
     * @param content
     * @param stringSubscriber
     */
    public void evaluateOpinion(Long caseId, String content, Subscriber<String> stringSubscriber) {
        mServiceAPI.evaluateOpinion(caseId, content)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 意见反馈
     *
     * @param content
     * @param stringSubscriber
     */
    public void yijianFanKui(String content, Subscriber<String> stringSubscriber) {
        mServiceAPI.yijianFanKui(content)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 更换律师
     *
     * @param orderId
     * @param imId
     * @param reason
     * @param stringSubscriber
     */
    public void getChangeLawyer(Long orderId, long imId, String reason, Subscriber<String> stringSubscriber) {
        mServiceAPI.changeLawyer(orderId, imId, reason)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取用户信息
     *
     * @param stringSubscriber
     */
    public void getUserInfo(Subscriber<String> stringSubscriber) {
        mServiceAPI.getUserInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 更新用户设置
     *
     * @param interviewSetting
     * @param stringSubscriber
     */
    public void getUserModifySetting(String interviewSetting, Subscriber<String> stringSubscriber) {
        mServiceAPI.userModifySetting(interviewSetting)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 投诉
     *
     * @param realName
     * @param phoneNum
     * @param content
     * @param attachments
     * @param stringSubscriber
     */
    public void getLawyerConsultComplaint(String realName, String phoneNum, String content, String attachments, Subscriber<String> stringSubscriber) {
        mServiceAPI.lawyerConsultComplaint(realName, phoneNum, content, attachments)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 文件上传
     *
     * @return
     */
    public void uploadignature(File file, Subscriber<String> stringSubscriber) {
        mServiceAPI.uploadignature(createFilePart("file", file))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 发起咨询- 获取订单状态
     *
     * @return
     */
    public void lawyerConsultOrderDetail(long caseId, Subscriber<String> stringSubscriber) {
        mServiceAPI.lawyerConsultOrderDetail(caseId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * Im 对话 最后一条聊天记录
     *
     * @param imId
     * @param content
     * @param type
     * @param redBagId
     * @param stringSubscriber
     */
    public void getLawyerImLastQuestion(long imId, String content, String type, long redBagId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerImLastQuestion(imId, content, type, redBagId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取 IM会话列表
     *
     * @param type
     * @param stringSubscriber
     */
    public void getLawyerConsultIm(Integer type, Subscriber<String> stringSubscriber) {
        mServiceAPI.getLawyerConsultIm(type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 系统消息
     *
     * @param stringSubscriber
     */
    public void getUserSystemMessage(Subscriber<String> stringSubscriber) {
        mServiceAPI.getUserSystemMessage()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 更新用户信息
     *
     * @param realName
     * @param sex
     * @param age
     * @param contact
     * @param career
     * @param stringSubscriber
     */
    public void getUserModifyInfo(String realName, String sex, String age, String contact, String career, Subscriber<String> stringSubscriber) {
        mServiceAPI.getUserModifyInfo(realName, sex, age, contact, career)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取主分类
     *
     * @param stringSubscriber
     */
    public void getCaseConsult(Subscriber<String> stringSubscriber) {
        mServiceAPI.getCaseConsult()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取咨询目录
     *
     * @param stringSubscriber
     */
    public void getConsultationDirectory(Subscriber<String> stringSubscriber) {
        mServiceAPI.getConsultationDirectory()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取民事、刑事、行政、合同起草 信息
     *
     * @param stringSubscriber
     */
    public void getHomeCategoriList(int id, Subscriber<String> stringSubscriber) {
        mServiceAPI.getConsultTypePer(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取民事、刑事、行政、合同起草 详细信息
     *
     * @param stringSubscriber
     */
    public void getHomeCategoriListDetails(String id, Subscriber<String> stringSubscriber) {
        mServiceAPI.getConsultTypePerDetails(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取应用数据
     *
     * @param stringSubscriber
     */
    public void getAppDataApi(Subscriber<String> stringSubscriber) {
        mServiceAPI.getAppDataApi()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取民事、刑事、行政、合同起草 详细信息
     *
     * @param stringSubscriber
     */
    public void getAppaList(int channel, int page, int ajax, Subscriber<String> stringSubscriber) {
        mServiceAPI.getAppsList(channel, page, ajax)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取 即问即答 分类项
     *
     * @param stringSubscriber
     */
    public void getCategorylist(Subscriber<String> stringSubscriber) {
        mServiceAPI.getCategorylist("1")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取 即问即答 分类子项
     *
     * @param stringSubscriber
     */
    public void getCategorylistDetails(String id, Subscriber<String> stringSubscriber) {
        mServiceAPI.getCategorylistDetails(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 绑定大牛卡
     *
     * @param cardNumber
     * @param cardPwd
     * @param stringSubscriber
     */
    public void bindDaNiuCard(String cardNumber, String cardPwd, Subscriber<String> stringSubscriber) {
        mServiceAPI.bindDaniuCard(cardNumber, cardPwd)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取匹配律师列表
     *
     * @param conversationId
     * @param stringSubscriber
     */
    public void getMatchingLawyerList(String conversationId, Subscriber<String> stringSubscriber) {
        mServiceAPI.getMatchingLawyerList(conversationId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 预约律师
     *
     * @param conversationId
     * @param stringSubscriber
     */
    public void onOrderToLawyer(String conversationId, String lawyerIds, String phoneNum, Subscriber<String> stringSubscriber) {
        mServiceAPI.onOrderToLawyer(conversationId, lawyerIds, phoneNum)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取大牛卡信息
     *
     * @param stringSubscriber
     */
    public void getDaNiuCardInfo(Subscriber<String> stringSubscriber) {
        mServiceAPI.getDaNiuCardInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 获取大牛卡信息
     *
     * @param stringSubscriber
     */
    public void onDeleteCaseItem(String caseId, Subscriber<String> stringSubscriber) {
        mServiceAPI.onDeleteCaseItem(caseId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 上传用户头像
     *
     * @param file
     */
    public void onUploadAvatar(File file, Subscriber<String> stringSubscriber) {
        mServiceAPI.uploadAvatar(createFilePart("file", file))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 登录鉴权云调解
     */
    public void onAuthentication(String token, String phone, String env, Subscriber<String> stringSubscriber) {
        mServiceAPI.onAuthentication(token, phone, env, 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    /**
     * 调解员鉴权
     */
    public void onMediatorAuthentication(String token, String phone, String env, Subscriber<String> stringSubscriber) {
        mServiceAPI.onMediatorAuthentication(token, phone, env, 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取调解案件列表
     *
     * @param state 案件状态
     */
    public void getMediateList(String authorization, String phone, int state, String begin_time, String end_time, String mediate_code, int pageNum, int pageSize, int noPaged, Subscriber<String> stringSubscriber) {
        mServiceAPI.getMediateList(authorization, phone, state, begin_time, end_time, mediate_code, pageNum, pageSize, noPaged)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取调解案件列表详情
     */
    public void getMediateListDetails(Map<String, String> map, int case_id, Subscriber<String> stringSubscriber) {
        mServiceAPI.getMediateListDetails(map, case_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 获取字典类型
     */
    public void getDict(Subscriber<String> stringSubscriber) {
        mServiceAPI.getDict()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 上传证据材料
     *
     * @param file
     */
    public void uploadEvidential(File file, int type, String file_type, Subscriber<String> stringSubscriber) {

        RequestBody types = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(type));
        RequestBody file_types = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(file_type));

        mServiceAPI.uploadEvidential(createFilePart("file", file), types, file_types)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 登录
     *
     */
    public void onLogin(String info,  Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.onLogin(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }


    /**
     * 根据公司名称获取抬头信息
     *
     */
    public void companySearch(String info,  Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.companySearch(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }



    /**
     * 绑定设备
     *
     */
    public void onBindDevice(String info,  Subscriber<String> stringSubscriber) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), info);
        mServiceAPI.onBindDevice(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }



}
