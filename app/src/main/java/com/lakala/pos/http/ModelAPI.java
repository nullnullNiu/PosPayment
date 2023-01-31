package com.lakala.pos.http;


import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.http.net.IScanningApi;


import java.io.File;
import java.util.Map;

import rx.Subscriber;


/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2018/3/20
 */

public class ModelAPI implements IScanningApi {


    @Override
    public void getVerificationCodeApi(String phoneNumber, DataListener<String> listener) {

    }

    /**
     * 用户登录
     *
     * @param listener
     * @paramdword
     */
    @Override
    public void getUserLoginApi(String phoneNumber, String verifyCode, String passwordMd5,int force, String deviceId,Byte role,final DataListener<String> listener) {
        BuildApi.getInstance().getUserLogin(phoneNumber, verifyCode, passwordMd5, force,deviceId,role,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "登录失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }
    /**
     * 用户一键登录
     *
     * @param listener
     * @paramdword
     */
    @Override
    public void getOneKeyLogin(String oneClickToken, int force,final DataListener<String> listener) {
        BuildApi.getInstance().getOneKeyLogin(oneClickToken, force,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "一键登录失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 退出登录
     *
     * @param listener
     * @paramdword
     */
    @Override
    public void loginOutUserApi(final DataListener<String> listener) {
        BuildApi.getInstance().loginOutUser(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "退出登录失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 评价调解员
     *
     * @param caseId
     * @param mediatorId
     * @return
     */
    @Override
    public void evaluateMediatorApi(long caseId, long appealId, long mediatorId, float score, String content, final DataListener<String> listener) {
        BuildApi.getInstance().evaluateMediator(caseId, appealId, mediatorId, score, content, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "评价调解员失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 评价案件
     *
     * @param caseId
     * @param
     * @return
     */
    @Override
    public void evaluateCaseApi(long caseId, String content, final DataListener<String> listener) {
        BuildApi.getInstance().evaluateCase(caseId, content, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "评价案件失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }


    /**
     * 获取调解员列表
     *
     * @param addressID
     * @return
     */
    @Override
    public void getMediatorListApi(Integer addressID, final DataListener<String> listener) {
        BuildApi.getInstance().getMediatorList(addressID, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取调解员列表失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取案件6大类
     *
     * @return
     */
    @Override
    public void getSixCategoriesApi(final DataListener<String> listener) {
        BuildApi.getInstance().getSixCategories(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取六大类案件失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取案件详情
     *
     * @param caseId
     * @return
     */
    @Override
    public void getCaseDetailsApi(long caseId, final DataListener<String> listener) {
        BuildApi.getInstance().getCaseDetails(caseId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取案件详情失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取案件列表
     *
     * @param businessType
     * @return
     */
    @Override
    public void getCaseListApi(String categoryId, long businessType, int pageNum, int pageSize, final DataListener<String> listener) {
        BuildApi.getInstance().getCaseList(categoryId, businessType, pageNum, pageSize, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取案件拉列表失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 发送问题
     *
     * @param question
     * @return
     */
    @Override
    public void sendQuestionApi(long caseId, String question,int byVoice ,final DataListener<String> listener) {
        BuildApi.getInstance().sendQuestion(caseId, question, byVoice,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "发送问题失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }
    /**
     * 即问即答发送问题
     *
     * @param question
     * @return
     */
    @Override
    public void sendAskQuestion(long caseId, String question,int byVoice ,final DataListener<String> listener) {
        BuildApi.getInstance().sendAskQuestion(caseId, question, byVoice,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "发送问题失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 发送问题 到AI接口
     *
     * @param question
     * @return
     */
    @Override
    public void sendQuestionAIPlusApi(long caseId, String question, final DataListener<String> listener) {
        BuildApi.getInstance().sendQuestionAIPlus(caseId, question, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "发送问题失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 创建订单
     *
     * @param payType
     * @return
     */
    @Override
    public void createOrderApi(int amountFen, String payType, final DataListener<String> listener) {
        BuildApi.getInstance().createOrder(amountFen, payType, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "创建订单失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取钱包明细
     *
     * @return
     */
    @Override
    public void getWalletDetailsApi(final DataListener<String> listener) {
        BuildApi.getInstance().getWalletDetails(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取钱包明细失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 微信登录
     *
     * @return
     */
    @Override
    public void wxLoginApi(String wxCode,int force,String deviceId, final DataListener<String> listener) {
        BuildApi.getInstance().wxLogin(wxCode,force,deviceId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "微信登录失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 微信登录补全手机号
     *
     * @return
     */
    @Override
    public void wxLoginAddPhoneNumApi(String wxCode, String phoneNum, String verifyCode,int force,String deviceId,Byte role, final DataListener<String> listener) {
        BuildApi.getInstance().wxLoginAddPhoneNum(wxCode, phoneNum, verifyCode,force,deviceId, role,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "微信登录补全手机号失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }


    /**
     * 获取钱包余额
     *
     * @return
     */
    @Override
    public void getWalletBalanceApi(final DataListener<String> listener) {
        BuildApi.getInstance().getWalletBalance(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取钱包余额失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 钱包提现
     *
     * @param amount
     * @return
     */
    @Override
    public void walletCashApi(Integer amount, String alipayAccount, String bankCardNum, String bankName, String peopleName, final DataListener<String> listener) {
        BuildApi.getInstance().walletCash(amount, alipayAccount, bankCardNum, bankName, peopleName, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "钱包提现失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }




    /**
     * 添加诉讼相关信息
     *
     * @param caseId
     * @return
     */
    @Override
    public void addAppealApi(long caseId, String appealName, String appealPhoneNum,String appealSex, String appealAge, String appealCaseAddress, String appealContent,Integer regionId, final DataListener<String> listener) {
            BuildApi.getInstance().addAppeal(caseId, appealName,appealPhoneNum, appealSex, appealAge, appealCaseAddress, appealContent, regionId,new Subscriber<String>() {
                @Override
                public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "添加诉讼相关信息失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 选择案件调解员并推送消息给调解员
     *
     * @param caseId
     * @return
     */
    @Override
    public void selectMediatorApi(long caseId, long mediatorId, final DataListener<String> listener) {
        BuildApi.getInstance().selectMediator(caseId, mediatorId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "选择案件调解员并推送消息给调解员失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 支付案件应付费用
     *
     * @param
     * @return
     */
    @Override
    public void payCaseOrderApi(long caseId, final DataListener<String> listener) {
        BuildApi.getInstance().payCaseOrder(caseId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "支付案件应付金额失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 支付案件应付费用
     *
     * @param
     * @return
     */
    @Override
    public void payCaseSpecifyType(long caseId,int payType, final DataListener<String> listener) {
        BuildApi.getInstance().payCaseSpecifyType(caseId,payType, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "支付案件应付金额失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取诉求内容
     *
     * @return
     */
    @Override
    public void getAppealContentApi(long caseId, final DataListener<String> listener) {
        BuildApi.getInstance().getAppealContent(caseId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取诉求内容失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 终止诉求
     *
     * @param reason
     * @return
     */
    @Override
    public void appealStopApi(long appealId, String reason, final DataListener<String> listener) {
        BuildApi.getInstance().appealStop(appealId, reason, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "终止诉求请求失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }


    /**
     * 获取终止诉讼的原因列表
     *
     * @param
     * @return
     */
    @Override
    public void appealStopReasonApi(final DataListener<String> listener) {
        BuildApi.getInstance().appealStopReason(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "获取终止诉讼的原因列表失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * app版本升级
     *
     * @param type
     * @param listener
     */
    @Override
    public void versionAppUpdateApi(String type, String version,String packageName ,final DataListener<String> listener) {
        BuildApi.getInstance().versionAppUpdate(type, version, packageName,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "版本升级失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }



    /**
     * 获取调解员信息
     * @param mediatorId
     * @param listener
     */
    @Override
    public void getMediatorDetailApi(long mediatorId,final DataListener<String> listener) {
        BuildApi.getInstance().getForMediatorDetail(mediatorId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }


    /**
     *  调解结束
     * @param appealId
     * @param listener
     */
    @Override
    public void appealOverApi(long appealId,final DataListener<String> listener) {
        BuildApi.getInstance().appealOver(appealId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e, "调解失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     *  获取Banner
     * @param listener
     */
    @Override
    public void getBannerApi(String token, int type,final DataListener<String> listener) {
        BuildApi.getInstance().getBanner(token, type,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取Banner失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     *  获取推荐律师
     * @param listener
     */

    @Override
    public void getLawyerApi(int optimizated, int recommended, int pageSize, int pageNum,final DataListener<String> listener) {
        BuildApi.getInstance().getLawyer(optimizated, recommended,pageSize,pageNum,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取获取推荐律师信息失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 更新意见书分享次数
     *
     * @param listener
     */
    @Override
    public void sharedNumApi(long caseId, int shareType, DataListener<String> listener) {
        BuildApi.getInstance().getCasePpinionSharedNum(caseId, shareType,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * IM对话：确认/拒绝结束邀请
     * @param imId
     * @param status
     * @param listener
     */
    @Override
    public void getLawyerConsultImApi(long imId, int status, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultIm(imId, status,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * IM对话：获取最新咨询意见书
     * @param imId
     * @param listener
     */
    @Override
    public void getLawyerConsultImDetailApi(long imId, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultImDetail(imId,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * IM对话：获取咨询意见书列表
     * @param listener
     */
    @Override
    public void getLawyerConsultListOpinionApi(DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultListOpinion(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * IM对话：发送咨询意见书
     * @param imId
     * @param caseId
     * @param opinionUrl
     * @param pdfUrl
     * @param listener
     */
    @Override
    public void getLawyerConsultSendOpinionApi(long imId, long caseId, String opinionUrl, String pdfUrl, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultSendOpinion(imId,caseId,opinionUrl,pdfUrl,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 创建红包 -返回红包id
     * @param imId
     * @param amountFen
     * @param message
     * @param toUserId
     * @param listener
     */
    @Override
    public void getLawyerConsultRedBagApi(long imId, Integer amountFen, String message, long toUserId, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultRedBag(imId,amountFen,message,toUserId,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取律师详情
     * @param lawyerId
     * @param listener
     */
    @Override
    public void getLawyerDetailApi(long lawyerId, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerDetail(lawyerId,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取雇主评价列表
     * @param lawyerId
     * @param listener
     */
    @Override
    public void getLawyerEvaluateListApi(long lawyerId, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerEvaluateList(lawyerId,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 创建预约面谈
     * @param imId
     * @param toUserId
     * @param content
     * @param realName
     * @param phoneNum
     * @param time
     * @param districtId
     * @param address
     * @param listener
     */
    @Override
    public void getLawyerConsultInterviewApi(long imId, long toUserId, String content, String realName, String phoneNum, String time, Integer districtId, String address, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultInterview(imId,toUserId,content,realName,phoneNum,time,districtId,address,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 改变预约面谈状态
     * @param interviewId
     * @param status
     * @param listener
     */
    @Override
    public void getLawyerConsultInterviewStatusApi(long interviewId, String status, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultInterviewStatus(interviewId,status,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**发起咨询
     * @param caseId
     * @param amountFen
     * @param appeal
     * @param listener
     */
    @Override
    public void faQiZiXunApi(Long caseId, int amountFen, String appeal, DataListener<String> listener) {
        BuildApi.getInstance().faQiZiXun(caseId, amountFen, appeal, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"发起咨询失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取红包状态
     * @param redBagId
     * @param listener
     */
    @Override
    public void getLawyerConsultRedBagStatusApi(long redBagId, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultRedBagStatus(redBagId,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**获取律师列表
     * @param regionId
     * @param label
     * @param keyword
     * @param listener
     */
    @Override
    public void getLawyerListApi(int regionId, String label, String keyword, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerList(regionId, label, keyword, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取律师列表失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**获取纠纷类别
     * @param listener
     */
    @Override
    public void getJiuFenTypeApi(DataListener<String> listener) {
        BuildApi.getInstance().getJiuFenType(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取纠纷类别失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**创建IM
     * @param caseId
     * @param toUserId
     * @param listener
     */
    @Override
    public void createIMApi(Long caseId, Long toUserId, Long orderId,int type,DataListener<String> listener) {
        BuildApi.getInstance().createIM(caseId, toUserId, orderId,type,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"创建IM失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**获取我的法律顾问
     * @param listener
     */
    @Override
    public void getLegalAdviserApi(DataListener<String> listener) {
        BuildApi.getInstance().getLegalAdviser(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取法律顾问失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /** 雇佣法律顾问
     * @param listener
     */
    @Override
    public void getGwLegalAdviserApi(long lawyerId,Integer amountFen,DataListener<String> listener) {
        BuildApi.getInstance().getGwLegalAdviser(lawyerId,amountFen,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取法律顾问失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 更换律师
     * @param orderId
     * @param imId
     * @param reason
     * @param listener
     */
    @Override
    public void getChangeLawyerApi(long orderId, long imId, String reason, DataListener<String> listener) {
        BuildApi.getInstance().getChangeLawyer(orderId,imId,reason,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"更换律师失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**追加红包
     * @param orderId
     * @param amountFen
     * @param listener
     */
    @Override
    public void addMoneyApi(Long orderId, int amountFen, DataListener<String> listener) {
        BuildApi.getInstance().addMoney(orderId, amountFen, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"追加红包失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**取消发起咨询订单
     * @param orderId
     * @param listener
     */
    @Override
    public void cancelStartZiXunApi(Long orderId, DataListener<String> listener) {
            BuildApi.getInstance().cancelStartZiXun(orderId, new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    listener.onFailure(e,"取消发起咨询订单失败 :" + e);
                }

                @Override
                public void onNext(String s) {
                    listener.onSuccess(s);
                }
            });
    }

    /**更换律师
     * @param orderId
     * @param reason
     * @param listener
     */
    @Override
    public void changeLawyerApi(Long orderId, String reason, DataListener<String> listener) {
            BuildApi.getInstance().changeLawyer(orderId, reason, new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    listener.onFailure(e,"更换律师失败 :" + e);
                }

                @Override
                public void onNext(String s) {
                    listener.onSuccess(s);
                }
            });
    }

    /**获取预约面谈列表
     * @param listener
     */
    @Override
    public void getinterviewIMListApi(DataListener<String> listener) {
        BuildApi.getInstance().getinterviewIMList(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取预约面谈列表失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**对咨询过程和意见书的评价
     * @param caseId
     * @param content
     * @param listener
     */
    @Override
    public void evaluateOpinionApi(Long caseId, String content, DataListener<String> listener) {
        BuildApi.getInstance().evaluateOpinion(caseId, content, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"评价失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**意见反馈
     * @param content
     * @param listener
     */
    @Override
    public void yijianFanKuiApi(String content, DataListener<String> listener) {
        BuildApi.getInstance().yijianFanKui(content, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"意见反馈失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**获取用户信息
     * @param listener
     */
    @Override
    public void getUserInfoApi(DataListener<String> listener) {
        BuildApi.getInstance().getUserInfo(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取用户信息失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 更新用户设置
     * @param interviewSetting
     * @param listener
     */
    @Override
    public void getUserModifySettingApi(String interviewSetting, DataListener<String> listener) {
        BuildApi.getInstance().getUserModifySetting(interviewSetting,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"更新用户设置失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 投诉
     * @param realName
     * @param phoneNum
     * @param content
     * @param attachments
     * @param listener
     */
    @Override
    public void getLawyerConsultComplaintApi(String realName, String phoneNum, String content, String attachments, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultComplaint(realName, phoneNum, content, attachments, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"投诉失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 上传文件
     * @return
     */
    @Override
    public void uploadignatureApi(File file, DataListener<String> listener) {
        BuildApi.getInstance().uploadignature(file, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"上传签名图片失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 发起咨询 获取抢单的状态
     * @param caseId
     * @param listener
     */
    @Override
    public void getLawyerConsultOrderDetail(long caseId, DataListener<String> listener) {

        BuildApi.getInstance().lawyerConsultOrderDetail(caseId, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取抢单的状态失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * Im 对话 最后一条聊天记录
     * @param imId
     * @param content
     * @param type
     * @param redBagId
     * @param listener
     */
    @Override
    public void getLawyerImLastQuestionApi(long imId, String content, String type, long redBagId, DataListener<String> listener) {

        BuildApi.getInstance().getLawyerImLastQuestion(imId,content,type, redBagId,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"最后一条聊天记录失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * IM 对话列表
     * @param type
     * @param listener
     */
    @Override
    public void getLawyerConsultImApi(Integer type, DataListener<String> listener) {
        BuildApi.getInstance().getLawyerConsultIm(type,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取对话列表失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 系统消息
     * @param listener
     */
    @Override
    public void getUserSystemMessageApi(DataListener<String> listener) {
        BuildApi.getInstance().getUserSystemMessage(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取系统消息失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 更新用户信息
     * @param realName
     * @param sex
     * @param age
     * @param contact
     * @param career
     * @param listener
     */
    @Override
    public void getUserModifyInfoApi(String realName, String sex, String age, String contact, String career,DataListener<String> listener) {
        BuildApi.getInstance().getUserModifyInfo(realName,sex,age,contact,career,new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取系统消息失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    /**
     * 获取主分类
     * @param listener
     */
    @Override
    public void getCaseConsultApi(DataListener<String> listener) {
        BuildApi.getInstance().getCaseConsult(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取主分类失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void getDirectoryListApi(DataListener<String> listener) {
        BuildApi.getInstance().getConsultationDirectory(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取咨询目录失败 :" + e);
            }
            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void getConsultTypePerApi(Integer id, DataListener<String> listener) {

        BuildApi.getInstance().getHomeCategoriList(id,new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取民事、刑事、行政、合同起草 信息 失败 :" + e);
            }
            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void getConsultTypePerDetailsApi(String id, DataListener<String> listener) {
        BuildApi.getInstance().getHomeCategoriListDetails(id,new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取民事、刑事、行政、合同起草 详细信息 失败 :" + e);
            }
            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void getAppaList(int channel, int page, int ajax, DataListener<String> listener) {

    }

    @Override
    public void getCategorylist(DataListener<String> listener) {
        BuildApi.getInstance().getCategorylist(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取  即问即答 分类项 失败 :" + e);
            }
            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void getCategorylistDetails(String id, DataListener<String> listener) {
        BuildApi.getInstance().getCategorylistDetails(id,new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取  即问即答 分类子项 失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }



    @Override
    public void bindDaNiuCard(String cardNumber,String cardPwd, DataListener<String> listener) {
        BuildApi.getInstance().bindDaNiuCard(cardNumber,cardPwd,new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"绑定大牛卡 失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void getMatchingLawyerList(String conversationId, DataListener<String> listener) {
        BuildApi.getInstance().getMatchingLawyerList(conversationId,new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取匹配律师列表 失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void onOrderToLawyer(String conversationId, String lawyerIds, String phoneNum, DataListener<String> listener) {

        BuildApi.getInstance().onOrderToLawyer(conversationId,lawyerIds,phoneNum,new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"预约律师 失败 :" + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void getDaNiuCardInfo(DataListener<String> listener) {

        BuildApi.getInstance().getDaNiuCardInfo(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"获取大牛卡信息 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void onDeleteCaseItem(String caseId, DataListener<String> listener) {
        BuildApi.getInstance().onDeleteCaseItem(caseId,new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"删除案件 失败 :" + e);
            }
            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });
    }

    @Override
    public void onUploadAvatar(File file, DataListener<String> listener) {
        BuildApi.getInstance().onUploadAvatar(file, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"图片上传 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });

    }


    @Override
    public void onAuthentication(String token,String phone,String env, DataListener<String> listener) {


    }


    @Override
    public void getMediateList(String authorization,String phone,int state, String begin_time, String end_time, String mediate_code, int pageNum, int pageSize, int noPaged , DataListener<String> listener) {


    }

    @Override
    public void getMediateListDetails(Map<String,String > map, int case_id, DataListener<String> listener) {


    }

    @Override
    public void getDict(DataListener<String> listener) {


    }

    @Override
    public void uploadEvidential(File file,int type,String file_type , DataListener<String> listener) {


    }


    //////////////////////////////
    @Override
    public void onLogin(String info , DataListener<String> listener) {
        BuildApi.getInstance().onLogin(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"登录换班 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });

    }


    @Override
    public void companySearch(String info , DataListener<String> listener) {
        BuildApi.getInstance().companySearch(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"根据公司名称获取抬头信息 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });

    }

    @Override
    public void onBindDevice(String info , DataListener<String> listener) {
        BuildApi.getInstance().onBindDevice(info, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e,"根据公司名称获取抬头信息 失败: " + e);
            }

            @Override
            public void onNext(String s) {
                listener.onSuccess(s);
            }
        });

    }


}
