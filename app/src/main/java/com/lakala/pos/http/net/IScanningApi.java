package com.lakala.pos.http.net;

import java.io.File;
import java.util.Map;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2018/3/20
 */

public interface IScanningApi {


    void getVerificationCodeApi(String phoneNumber, DataListener<String> listener);

    void getUserLoginApi(String phoneNumber, String verifyCode, String passwordMd5, int force, String deviceId, Byte role, DataListener<String> listener);

    void getOneKeyLogin(String oneClickToken, int force, DataListener<String> listener);

    void loginOutUserApi(DataListener<String> listener);

    void evaluateMediatorApi(long caseId, long appealId, long mediatorId, float score, String content, DataListener<String> listener);

    void evaluateCaseApi(long caseId, String content, DataListener<String> listener);

    void getMediatorListApi(Integer addressID, DataListener<String> listener);

    void getSixCategoriesApi(DataListener<String> listener);

    void getCaseDetailsApi(long caseId, DataListener<String> listener);

    void getCaseListApi(String categoryId, long businessType, int pageNum, int pageSize, DataListener<String> listener);

    void sendQuestionApi(long caseId, String question, int byVoice, DataListener<String> listener);

    void sendAskQuestion(long caseId, String question, int byVoice, DataListener<String> listener);

    void sendQuestionAIPlusApi(long caseId, String question, DataListener<String> listener);

    void createOrderApi(int amountFen, String payType, DataListener<String> listener);

    void getWalletDetailsApi(DataListener<String> listener);

    void wxLoginApi(String wxCode, int force, String deviceId, DataListener<String> listener);

    void wxLoginAddPhoneNumApi(String wxCode, String phoneNum, String verifyCode, int force, String deviceId, Byte role, DataListener<String> listener);

    void getWalletBalanceApi(DataListener<String> listener);

    void walletCashApi(Integer amount, String alipayAccount, String bankCardNum, String bankName, String peopleName, DataListener<String> listener);

    void addAppealApi(long caseId, String appealName, String appealPhoneNum, String appealSex, String appealAge, String appealCaseAddress, String appealContent, Integer regionId, DataListener<String> listener);

    void selectMediatorApi(long caseId, long mediatorId, DataListener<String> listener);

    void payCaseOrderApi(long caseId, DataListener<String> listener);

    void payCaseSpecifyType(long caseId, int payType, DataListener<String> listener);

    void getAppealContentApi(long caseId, DataListener<String> listener);

    void appealStopApi(long appealId, String reason, DataListener<String> listener);

    void appealStopReasonApi(DataListener<String> listener);

    void versionAppUpdateApi(String type, String version, String packageName, DataListener<String> listener);

    void getMediatorDetailApi(long mediatorId, DataListener<String> listener);

    void appealOverApi(long appealId, DataListener<String> listener);

    void getBannerApi(String token, int type, DataListener<String> listener);

    void getLawyerApi(int optimizated, int recommended, int pageSize, int pageNum, DataListener<String> listener);

    void sharedNumApi(long caseId, int shareType, DataListener<String> listener);

    void getLawyerConsultImApi(long imId, int status, DataListener<String> listener);

    void getLawyerConsultImDetailApi(long imId, DataListener<String> listener);

    void getLawyerConsultListOpinionApi(DataListener<String> listener);

    void getLawyerConsultSendOpinionApi(long imId, long caseId, String opinionUrl, String pdfUrl, DataListener<String> listener);

    void getLawyerConsultRedBagApi(long imId, Integer amountFen, String message, long toUserId, DataListener<String> listener);

    void getLawyerDetailApi(long lawyerId, DataListener<String> listener);

    void getLawyerEvaluateListApi(long lawyerId, DataListener<String> listener);

    void getLawyerConsultInterviewApi(long imId, long toUserId, String content, String realName, String phoneNum, String time, Integer districtId, String address, DataListener<String> listener);

    void getLawyerConsultInterviewStatusApi(long interviewId, String status, DataListener<String> listener);

    void faQiZiXunApi(Long caseId, int amountFen, String appeal, DataListener<String> listener);

    void getLawyerConsultRedBagStatusApi(long redBagId, DataListener<String> listener);

    void getLawyerListApi(int regionId, String label, String keyword, DataListener<String> listener);

    void getJiuFenTypeApi(DataListener<String> listener);

    void createIMApi(Long caseId, Long toUserId, Long orderId, int type, DataListener<String> listener);

    void getLegalAdviserApi(DataListener<String> listener);

    void getGwLegalAdviserApi(long lawyerId, Integer amountFen, DataListener<String> listener);

    void addMoneyApi(Long orderId, int amountFen, DataListener<String> listener);

    void cancelStartZiXunApi(Long orderId, DataListener<String> listener);

    void changeLawyerApi(Long orderId, String reason, DataListener<String> listener);

    void getinterviewIMListApi(DataListener<String> listener);

    void evaluateOpinionApi(Long caseId, String content, DataListener<String> listener);

    void yijianFanKuiApi(String content, DataListener<String> listener);

    void getChangeLawyerApi(long orderId, long imId, String reason, DataListener<String> listener);

    void getUserInfoApi(DataListener<String> listener);

    void getUserModifySettingApi(String interviewSetting, DataListener<String> listener);

    void getLawyerConsultComplaintApi(String realName, String phoneNum, String content, String attachments, DataListener<String> listener);

    void uploadignatureApi(File file, DataListener<String> listener);

    void getLawyerConsultOrderDetail(long caseId, DataListener<String> listener);

    void getLawyerImLastQuestionApi(long imId, String content, String type, long redBagId, DataListener<String> listener);

    void getLawyerConsultImApi(Integer type, DataListener<String> listener);

    void getUserSystemMessageApi(DataListener<String> listener);

    void getUserModifyInfoApi(String realName, String sex, String age, String contact, String career, DataListener<String> listener);

    void getCaseConsultApi(DataListener<String> listener);

    void getDirectoryListApi(DataListener<String> listener);

    void getConsultTypePerApi(Integer id, DataListener<String> listener);

    void getConsultTypePerDetailsApi(String id, DataListener<String> listener);


    void getAppaList(int channel, int page, int ajax, DataListener<String> listener);


    void getCategorylist(DataListener<String> listener);

    void getCategorylistDetails(String id, DataListener<String> listener);

    void bindDaNiuCard(String cardNumber, String cardPwd, DataListener<String> listener);

    void getMatchingLawyerList(String conversationId, DataListener<String> listener);

    void onOrderToLawyer(String conversationId, String lawyerIds, String phoneNum, DataListener<String> listener);

    void getDaNiuCardInfo(DataListener<String> listener);

    void onDeleteCaseItem(String caseId, DataListener<String> listener);

    void onUploadAvatar(File file, DataListener<String> listener);

    void onAuthentication(String token,String phone,String env, DataListener<String> listener);

    void getMediateList(String authorization,String phone,int state,String begin_time, String end_time, String mediate_code, int pageNum, int pageSize, int noPaged , DataListener<String> listener);

    void getMediateListDetails(Map<String,String > map, int case_id , DataListener<String> listener);

    void getDict(DataListener<String> listener);

    void uploadEvidential(File file,int type,String file_type , DataListener<String> listener);

    /////////////////////////////////////////////////

    void onLogin(String info , DataListener<String> listener);

    void companySearch(String info , DataListener<String> listener);

    void onBindDevice(String info , DataListener<String> listener);


}
