package com.lakala.pos.http;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2018/3/20
 */

public interface ServiceAPI {


    // 初始化绑定设备
    @POST("agency/device/bind")
    Observable<String> onBindinDevice(@Query("phoneNum") String phoneNum);

    /**
     * 用户登录
     *
     * @param phoneNum
     * @param verifyCode
     * @return
     */
    @POST("api/user/login")
    Observable<String> getUserLogin(@Query("phoneNum") String phoneNum,
                                    @Query("verifyCode") String verifyCode,
                                    @Query("passwordMd5") String passwordMd5,
                                    @Query("force") int force,
                                    @Query("deviceId") String deviceId,
                                    @Query("role") Byte role);

    /**
     * 用户一键登录
     *
     * @param oneClickToken
     * @param force
     * @return
     */
    @POST("/api/user/oneClickLogin")
    Observable<String> getOneKeyLogin(@Query("oneClickToken") String oneClickToken,
                                      @Query("force") int force);

    /**
     * 退出登录
     *
     * @return
     */
    @GET("api/user/logout")
    Observable<String> loginOutUser();


    /**
     * app版本更新
     *
     * @return
     */
    @GET("api/app/last")
    Observable<String> versionAppUpdate(@Query("type") String type,
                                        @Query("version") String version, @Query("packageName") String packageName);

    /**
     * 一次性获取全部行政区域
     *
     * @return
     */
    @GET("/api/region/all")
    Observable<String> getRegionAll(@Query("level") int level);

    /**
     * 评价调解员
     *
     * @param caseId
     * @param mediatorId
     * @return
     */
    @POST("api/mediator/evaluate")
    @FormUrlEncoded
    Observable<String> evaluateMediator(@Query("caseId") long caseId,
                                        @Query("appealId") long appealId,
                                        @Query("mediatorId") long mediatorId,
                                        @Query("score") float score,
                                        @Field("content") String content);

    /**
     * 评价案件
     *
     * @param caseId
     * @return
     */
    @POST("api/case/evaluate")
    @FormUrlEncoded
    Observable<String> evaluateCase(@Query("caseId") long caseId,
                                    @Field("content") String content);

    /**
     * 终止诉求
     *
     * @param reason
     * @return
     */
    @POST("api/case/appealStop")
    @FormUrlEncoded
    Observable<String> appealStop(@Query("appealId") long appealId,
                                  @Field("reason") String reason);

    /**
     * 获取终止诉讼的原因列表
     *
     * @param
     * @return
     */
    @GET("api/case/appealStopReason")
    Observable<String> appealStopReason();


    /**
     * 获取调解员列表
     *
     * @return
     */
    @GET("api/mediator")
    Observable<String> getMediatorList(@Query("regionId") Integer addressID);

    /**
     * 获取案件6大类
     *
     * @return
     */
    @GET("api/case/categoryAll")
    Observable<String> getSixCategories();


    /**
     * 获取案件详情
     *
     * @param caseId
     * @return
     */
    @GET("api/case/detail")
    Observable<String> getCaseDetails(@Query("caseId") long caseId,
                                      @Query("opinionType") String opinionType);

    /**
     * 获取案件列表
     *
     * @param businessType
     * @return
     */
    @GET("api/case/listCase")
    Observable<String> getCaseList(@Query("categoryId") String categoryId,
                                   @Query("businessType") long businessType,
                                   @Query("pageNum") int pageNum,
                                   @Query("pageSize") int pageSize);

    /**
     * 发送问题
     *
     * @param question
     * @return
     */
    @POST("api/conversation")
    @FormUrlEncoded
    Observable<String> sendQuestion(@Query("caseId") long caseId,
                                    @Field("question") String question,
                                    @Query("byVoice") int byVoice);


    /**
     * 即问即答发送问题
     *
     * @return
     */
    @GET("api/api")
    Observable<String> sendAskQuestion(@Query("caseId") long caseId,
                                       @Query("question") String question,
                                       @Query("byVoice") int byVoice);


    /**
     * 发送问题 到AI接口
     *
     * @param question
     * @return
     */
    @POST("api/conversation/experience")
    Observable<String> sendQuestionAIPlus(@Query("caseId") long caseId,
                                          @Query("question") String question);


    /**
     * 创建订单
     *
     * @param payType
     * @return
     */
    @POST("api/order")
    Observable<String> createOrder(@Query("amountFen") int amountFen,
                                   @Query("payType") String payType);

    /**
     * 旧支付案件应付费用
     *
     * @param caseId
     * @return
     */
    @POST("api/case/payCase")
    Observable<String> payCaseOrder(@Query("caseId") long caseId);

    /**
     * 新支付案件费用
     *
     * @param caseId
     * @return
     */
    @POST("api/case/payCaseSpecifyType")
    Observable<String> payCaseSpecifyType(@Query("caseId") long caseId, @Query("payType") int payType);


    /**
     * 支付宝异步通知接口
     *
     * @return POSTPOST
     */
    @POST("api/pay/aliNotify")
    Observable<String> aliPayNotification();

    /**
     * 获取钱包明细
     *
     * @return
     */
    @GET("api/user/walletDetail")
    Observable<String> getWalletDetails();

    /**
     * 微信登录
     *
     * @param wxCode
     * @return
     */
    @POST("api/user/wxLogin")
    Observable<String> wxLogin(@Query("wxCode") String wxCode, @Query("force") int force,
                               @Query("deviceId") String deviceId);

    /**
     * 微信登录补全手机号
     *
     * @param wxCode
     * @return
     */
    @POST("api/user/wxLoginAddPhoneNum")
    Observable<String> wxLoginAddPhoneNum(@Query("wxCode") String wxCode,
                                          @Query("phoneNum") String phoneNum,
                                          @Query("verifyCode") String verifyCode,
                                          @Query("force") int force,
                                          @Query("deviceId") String deviceId, @Query("role") Byte role);

    /**
     * 获取钱包余额
     *
     * @return
     */
    @GET("api/user/wallet")
    Observable<String> getWalletBalance();

    /**
     * 钱包提现
     *
     * @param amount
     * @return
     */
    @POST("api/user/walletCash")
    Observable<String> walletCash(@Query("amount") Integer amount,
                                  @Query("alipayAccount") String alipayAccount,
                                  @Query("bankCardNum") String bankCardNum,
                                  @Query("bankName") String bankName,
                                  @Query("peopleName") String peopleName);


    /**
     * 获取诉求内容
     *
     * @return
     */
    @GET("api/case/appeal")
    Observable<String> getAppealContent(@Query("caseId") long caseId);


    /**
     * 添加诉讼相关信息POST /case/appealAdd
     *
     * @param caseId
     * @return
     */
    @POST("api/case/appealAdd")
    @FormUrlEncoded
    Observable<String> addAppeal(@Query("caseId") long caseId,
                                 @Query("appealName") String appealName,
                                 @Query("appealPhoneNum") String appealPhoneNum,
                                 @Query("appealSex") String appealSex,
                                 @Query("appealAge") String appealAge,
                                 @Query("appealCaseAddress") String appealCaseAddress,
                                 @Field("appealContent") String appealContent,
                                 @Query("regionId") Integer regionId);


    /**
     * 选择案件调解员并推送消息给调解员
     *
     * @param caseId
     * @return
     */
    @POST("api/case/selectMediator")
    Observable<String> selectMediator(@Query("caseId") long caseId,
                                      @Query("mediatorId") long mediatorId);


    /**
     * 获取调解员信息
     *
     * @return
     */
    @GET("api/mediator/detail")
    Observable<String> getMediatorDetial(@Query("mediatorId") long caseId);


    /**
     * 调解结束
     *
     * @return
     */
    @POST("api/case/appealOver")
    Observable<String> appealOver(@Query("appealId") long appealId);

    /**
     * 首页Banner
     *
     * @return
     */
    @GET("api/banner/listByType")
    Observable<String> getBannerUrl(@Query("token") String token,
                                    @Query("deviceType") int type);


    /**
     * 获取民事、刑事、行政、合同起草 信息
     *
     * @return
     */
    @GET("/api/case/consultType")
    Observable<String> getConsultTypePer(@Query("id") int id);


    /**
     * 获取民事、刑事、行政、合同起草 详细信息
     *
     * @return
     */
    @GET("/api/case/consultType")
    Observable<String> getConsultTypePerDetails(@Query("id") String id);


    /**
     * 获取民事、刑事、行政、合同起草 详细信息
     *
     * @return
     */
    @GET("/api/case/app")
    Observable<String> getAppDataApi();


    /**
     * 首页咨询目录
     */
    @GET("api/entrance/listByHitTimes")
    Observable<String> getConsultationDirectory();


    /**
     * 首页Banner
     *
     * @return
     */
    @GET("api/lawyer")
    Observable<String> getLawyer(@Query("optimizated") int optimizated, @Query("recommended") int recommended, @Query("pageSize") int pageSize, @Query("pageNum") int pageNum);

    /**
     * 更新意见书分享次数
     *
     * @return
     */
    @POST("api/case/opinionSharedNum")
    Observable<String> getCaseOpinionSharedNum(@Query("caseId") Long caseId, @Query("shareType") int shareType);


    /**
     * IM对话：确认/拒绝结束邀请
     *
     * @param imId
     * @param status
     * @return
     */
    @PUT("api/lawyer/consult/im")
    Observable<String> getLawyerConsultIm(@Query("imId") Long imId, @Query("status") int status);

    /**
     * 获取最新咨询意见书
     *
     * @param imId
     * @return
     */
    @GET("api/lawyer/consult/imDetail")
    Observable<String> getLawyerConsultImDetail(@Query("imId") Long imId);

    /**
     * 获取咨询意见书列表
     *
     * @return
     */
    @GET("api/lawyer/consult/listOpinion")
    Observable<String> getLawyerConsultListOpinion();

    /**
     * 发送咨询意见书
     *
     * @return
     */
    @POST("api/lawyer/consult/sendOpinion")
    Observable<String> getLawyerConsultSendOpinion(@Query("imId") Long imId, @Query("caseId") long caseId, @Query("opinionUrl") String opinionUrl, @Query("pdfUrl") String pdfUrl);

    /**
     * 创建红包
     *
     * @return
     */
    @POST("api/lawyer/consult/redBag")
    Observable<String> getLawyerConsultRedBag(@Query("imId") Long imId, @Query("amountFen") Integer amountFen, @Query("message") String message, @Query("toUserId") long toUserId);

    /**
     * 律师详情
     *
     * @return
     */
    @GET("api/lawyer/detail")
    Observable<String> getLawyerDetail(@Query("lawyerId") Long lawyerId);

    /**
     * 雇主评价列表
     *
     * @return
     */
    @GET("api/lawyer/evaluate/list")
    Observable<String> getLawyerEvaluateList(@Query("lawyerId") Long lawyerId);

    /**
     * 创建预约面谈
     *
     * @return
     */
    @POST("api/lawyer/consult/interview")
    Observable<String> getLawyerConsultInterview(@Query("imId") Long imId, @Query("toUserId") long toUserId, @Query("content") String content, @Query("realName") String realName, @Query("phoneNum") String phoneNum, @Query("time") String time, @Query("districtId") Integer districtId, @Query("address") String address);

    /**
     * 预约面谈 - 状态改变
     *
     * @return
     */
    @PUT("api/lawyer/consult/interview")
    Observable<String> getLawyerConsultInterviewStatus(@Query("interviewId") Long interviewId, @Query("status") String status);

    /**
     * 发起咨询
     *
     * @return
     */
    @POST("api/lawyer/consult/start")
    Observable<String> faQiZiXun(@Query("caseId") Long caseId, @Query("amountFen") int amountFen, @Query("appeal") String appeal);

    /**
     * 获取红包状态
     *
     * @return
     */
    @GET("api/lawyer/consult/redBag")
    Observable<String> getLawyerConsultRedBagStatus(@Query("redBagId") Long redBagId);

    /**
     * 获取律师列表
     *
     * @return
     */
    @GET("api/lawyer")
    Observable<String> getLawyerList(@Query("regionId") int regionId,
                                     @Query("label") String label,
                                     @Query("keyword") String keyword);

    /**
     * 获取纠纷类别
     *
     * @return
     */
    @GET("api/mediator/case/type")
    Observable<String> getJiuFenType();

    /**
     * 创建IM对话
     *
     * @return
     */
    @POST("api/lawyer/consult/imCreate")
    Observable<String> createIM(@Query("caseId") Long caseId, @Query("toUserId") Long toUserId, @Query("orderId") Long orderId, @Query("type") int type);

    /**
     * legalAdviser获取我的法律顾问列表
     *
     * @return
     */
    @GET("api/lawyer/consult/legalAdviser")
    Observable<String> getLegalAdviser();

    /**
     * 追加红包
     *
     * @return
     */
    @POST("api/lawyer/consult/addMoney")
    Observable<String> addMoney(@Query("orderId") Long orderId, @Query("amountFen") int amountFen);

    /**
     * 取消发起咨询
     *
     * @return
     */
    @POST("api/lawyer/consult/cancel")
    Observable<String> cancelStartZiXun(@Query("orderId") Long orderId);

    /**
     * 对律师不满？换一个 和 更换律师
     *
     * @return
     */
    @POST("api/lawyer/consult/changeLawyer")
    Observable<String> changeLawyer(@Query("orderId") Long orderId, @Query("reason") String reason);

    /**
     * 获取预约面谈列表
     *
     * @return
     */
    @GET("api/lawyer/consult/interview")
    Observable<String> getinterviewIMList();


    /**
     * 对意见书和咨询过程的评价
     *
     * @return
     */
    @POST("api/case/evaluateOpinion")
    @FormUrlEncoded
    Observable<String> evaluateOpinion(@Query("caseId") Long caseId, @Field("content") String content);

    /**
     * 意见反馈
     *
     * @return
     */
    @POST("api/user/advice")
    @FormUrlEncoded
    Observable<String> yijianFanKui(@Field("content") String content);

    /**
     * 更换律师
     *
     * @return
     */
    @POST("api/lawyer/consult/changeLawyer")
    Observable<String> changeLawyer(@Query("orderId") Long orderId, @Query("imId") Long imId, @Query("reason") String reason);

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET("api/user/info")
    Observable<String> getUserInfo();

    /**
     * 更新用户设置
     *
     * @return
     */
    @POST("api/user/modifySetting")
    Observable<String> userModifySetting(@Query("interviewSetting") String interviewSetting);

    /**
     * 投诉
     *
     * @param realName
     * @param phoneNum
     * @param content
     * @param attachments
     * @return
     */
    @POST("api/lawyer/consult/complaint")
    @FormUrlEncoded
    Observable<String> lawyerConsultComplaint(@Query("realName") String realName, @Query("phoneNum") String phoneNum, @Field("content") String content, @Query("attachments") String attachments);

    /**
     * 文件上传
     *
     * @return
     */
    @Multipart
    @POST("/api/mediator/upload")
    Observable<String> uploadignature(@Part MultipartBody.Part file);

    /**
     * 发起咨询  抢单状态
     *
     * @return
     */
    @GET("api/lawyer/consult/orderDetail")
    Observable<String> lawyerConsultOrderDetail(@Query("caseId") long caseId);


    /**
     * Im 对话最后一条聊天记录
     *
     * @return
     */
    @POST("api/lawyer/im/lastQuestion")
    @FormUrlEncoded
    Observable<String> getLawyerImLastQuestion(@Query("imId") long imId, @Field("content") String content, @Query("type") String type, @Query("redBagId") long redBagId);

    /**
     * IM 对话列表
     *
     * @return
     */
    @GET("api/lawyer/consult/im")
    Observable<String> getLawyerConsultIm(@Query("type") Integer type);

    /**
     * 系统消息
     *
     * @return
     */
    @GET("api/user/systemMessage")
    Observable<String> getUserSystemMessage();


    /**
     * 雇佣为法律顾 - 返回用户个人信息
     *
     * @return
     */
    @POST("api/lawyer/consult/legalAdviser")
    Observable<String> getGwLegalAdviser(@Query("lawyerId") long lawyerId, @Query("amountFen") Integer amountFen);

    /**
     * 更新用户个人信息
     *
     * @return
     */
    @POST("api/user/modifyInfo")
    Observable<String> getUserModifyInfo(@Query("realName") String realName, @Query("sex") String sex, @Query("age") String age, @Query("contact") String contact, @Query("career") String career);


    /**
     * 获取主分类
     *
     * @return
     */
    @GET("api/case/consult")
    Observable<String> getCaseConsult();


    /**
     * 获取应用列表
     *
     * @return
     */
    @POST("addons/cms/wxapp.archives/index")
    Observable<String> getAppsList(@Query("channel") int channel, @Query("page") int page,
                                   @Query("_ajax") int ajax);

    /**
     * 获取 即问即答 分类项
     *
     * @return
     */
    @GET("/api/qaCategory/list")
    Observable<String> getCategorylist(@Query("qaCategoryPid") String id);


    /**
     * 获取 即问即答 分类子项
     *
     * @return
     */
    @GET("/api/qaCategory/list")
    Observable<String> getCategorylistDetails(@Query("qaCategoryPid") String id);


    /**
     * 绑定大牛卡
     *
     * @param cardNumber
     * @param cardPwd
     * @return
     */
    @GET("/api/user/chargeDaniubi")
    Observable<String> bindDaniuCard(@Query("cardNumber") String cardNumber,
                                     @Query("cardPwd") String cardPwd);

    /**
     * 预约律师获取律师列表
     *
     * @return
     */
    @GET("/api/lawyer/recommendLawyer")
    Observable<String> getMatchingLawyerList(@Query("conversationId") String conversationId);


    /**
     * 预约律师
     *
     * @return
     */
    @POST("api/user/orderToLawyer")
    Observable<String> onOrderToLawyer(@Query("conversationId") String conversationId,
                                       @Query("lawyerIds") String lawyerIds,
                                       @Query("phoneNum") String phoneNum);

    /**
     * 获取大牛卡信息
     *
     * @return
     */
    @GET("/api/user/myCardList")
    Observable<String> getDaNiuCardInfo();


    /**
     * 删除我的咨询列
     *
     * @return
     */
    @POST("/api/case/delete")
    @FormUrlEncoded
    Observable<String> onDeleteCaseItem(@Field("caseIds") String caseId);


    /**
     * 用户头像图片上传
     *
     * @return
     */
    @Multipart
    @POST("/api/user/uploadHeadImage")
    Observable<String> uploadAvatar(@Part MultipartBody.Part file);


    /**
     * 登录鉴权云调解
     *
     * @return
     */
    @GET("v2/auth")
    Observable<String> onAuthentication(@Query("token") String token,
                                        @Query("phone") String phone,
                                        @Query("env") String env,
                                        @Query("is_third") int is_third);


    /**
     * 调解员鉴权
     *
     * @return
     */
    @GET("v2.auth/auth_mediator")
    Observable<String> onMediatorAuthentication(@Query("token") String token,
                                        @Query("phone") String phone,
                                        @Query("env") String env,
                                        @Query("is_third") int is_third);


    /**
     * 获取调解案件列表
     *
     * @return
     */
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("sfv1/cases/listparty")
    @FormUrlEncoded
    Observable<String> getMediateList(@Header("Authorization") String authorization,
                                      @Field("phone") String phone,
                                      @Field("state") int state,
                                      @Field("begin_time") String begin_time,
                                      @Field("end_time") String end_time,
                                      @Field("mediate_code") String mediate_code,
                                      @Field("pageNum") int pageNum,
                                      @Field("pageSize") int pageSize,
                                      @Field("noPaged") int noPaged);


    /**
     * 获取调解案件列表id详情
     *
     * @return
     */
    @GET("sfv1/cases/detail")
    Observable<String> getMediateListDetails(@HeaderMap Map<String, String> headerws, @Query("case_id") int case_id);


    /**
     * 获取字典类型
     *
     * @return
     */
    @GET("common/getdict")
    Observable<String> getDict();


    /**
     * 证据材料图片
     *
     * @return
     */
    @Multipart
    @POST("common/upload")
    Observable<String> uploadEvidential(@Part MultipartBody.Part file,
                                        @Part("type") RequestBody type,
                                        @Part("file_type") RequestBody file_type);


    /**
     * 登录
     *
     * @return
     */

    @POST("agency/sys/login/loginMobile")
    Observable<String> onLogin(@Body RequestBody requestBody);


    /**
     * 绑定
     *
     * @return
     */

    @POST("agency/device/bind")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> onBindDevice(
            @Body RequestBody requestBody);


    /**
     * 根据公司名称获取抬头信息
     *
     * @return
     */

    @POST("agency/invoice/companySearch")
    @Headers({"API_KEY:ABC", "API_SECURITY_CODE:XYZ"})
    Observable<String> companySearch(
            @Body RequestBody requestBody);

}
