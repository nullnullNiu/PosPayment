package com.lakala.pos.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lakala.pos.R;
import com.lakala.pos.bean.CreateOrderBean;
import com.lakala.pos.bean.CreateOrderResultBean;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.bean.TransactionEntity;
import com.lakala.pos.common.DeviceInfo;
import com.lakala.pos.common.Global;
import com.lakala.pos.dialog.BillingCodeDialog;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.EditTextUtils;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.PreferencesUtils;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MVPActivity<IHomeView, MainActivityPresenter> implements IHomeView, CompoundButton.OnCheckedChangeListener {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "NonConstantResourceId"})
    @BindView(R.id.swichbutton_invoicing)
    Switch btnSwitch;

    @BindView(R.id.money_et)
    EditText money_et;

    @BindView(R.id.status)
    TextView status;

    @BindView(R.id.voucher_no)
    TextView voucher_no;

    @BindView(R.id.time_tv)
    TextView time_tv;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.im_more)
    ImageView im_more;

    String amount = "";
    double money = 0.0;

    StringBuilder stringBuilder = new StringBuilder();
    private PopupWindow mPopupWindow;

    private int pageNum = 1;
    private int maxIndext = 0;
    private int indext = 0;
    private int allPageNum = 0;
    private List<TranQueryBean.Records> listBean = new ArrayList<>();

    BillingCodeDialog billingCodeDialog;

    private boolean invoiceSwitch  = false;
    private boolean cashPayment = false;

    private static final int DEVICE_CODE_OK = 1;
    private static final int SCAN_CODE_OK = 2;
    private static final int BANK_CODE_OK = 3;

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDownladPermis();
        DeviceInfo.initDevice(this);
        checkToken();
        money_et.setFocusable(false);
        money_et.setFocusableInTouchMode(false);

        EditTextUtils.afterDotTwo(money_et);
        invoiceSwitch = PreferencesUtils.getPreferenceBoolean("invoice_switch",false);
        btnSwitch.setChecked(invoiceSwitch);
        btnSwitch.setOnCheckedChangeListener(this);
        Global.ORDER_SORT = PreferencesUtils.getPreferenceInt("ORDER_SORT",1);
        time_tv.setText("000" + Global.ORDER_SORT);
    }


    // 判断储存权限
    public boolean getDownladPermis() {
        PermisName = "储存";
        isNeedCheck = false;
        onCheckPermis(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return isNeedCheck;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        invoiceSwitch = isChecked;
        PreferencesUtils.setPreferenceBoolean("invoice_switch", isChecked);
        LogUtil.i("开发开关：     " + isChecked);
    }

    private void checkToken() {
        String access_token = PreferencesUtils.getPreferenceString("access_token", "");
        if (TextUtils.isEmpty(access_token)) {
            Intent bindIntent = new Intent(this, DeviceBindingActivity.class);
            bindIntent.putExtra("typeCode", -1);
            startActivity(bindIntent);
            return;
        }
        getDeviceInfo();
        mPresenter.queryOrders(pageNum);
    }


    @OnClick({R.id.im_more, R.id.shift_change, R.id.tv_deletd, R.id.previous_tv, R.id.next_tv, R.id.select_tv, R.id.revoke_tv
            , R.id.cash_receipt_tv, R.id.collection_code_tv, R.id.scan_tv, R.id.bank_card_tv,
            R.id.spot_tv, R.id.zero_tv, R.id.one_tv, R.id.two_tv, R.id.three_tv, R.id.four_tv, R.id.five_tv, R.id.six_tv, R.id.seven_tv, R.id.eight_tv, R.id.nine_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_more://菜单
                if (mPopupWindow == null || !mPopupWindow.isShowing()) {
                    showMore(im_more);
                } else {
                    mPopupWindow.dismiss();
                }


                break;

            case R.id.shift_change://换班

                Intent i = new Intent(this, ShiftChangeActivity.class);
                startActivity(i);

                break;
            case R.id.tv_deletd://删除
                stringBuilder.setLength(0);
                money_et.setText("");

                break;
            case R.id.previous_tv://上一条
                if (indext < 1) {
                    ToastUtil.showToast("当前已经是第一条记录了！");
                    return;
                }
                indext--;
                switchTitleItem(indext);
                break;
            case R.id.next_tv://下一条
                LogUtil.e(maxIndext + "   " + indext + "   " + maxIndext);
                if (maxIndext != 0 && indext >= maxIndext - 1 || indext >= listBean.size() - 1) {
                    if (allPageNum > pageNum) {
                        pageNum++;
                        indext++;
                        mPresenter.queryOrders(pageNum);
                    } else {
                        ToastUtil.showToast("当前已经是最后一条记录了！");
                    }
                    return;
                }

                indext++;
                switchTitleItem(indext);
                break;

            case R.id.select_tv://查询

                startActivity(new Intent(this, TranQueryActivity.class));


                break;
            case R.id.revoke_tv://撤销
                showRevokeDialog();
                break;

            case R.id.cash_receipt_tv://收现金
                cashPayment = true;
                amount = money_et.getText().toString();
                LogUtil.i("收款金额：" + money);
                if (TextUtils.isEmpty(amount)) {
                    ToastUtil.showToast("请输入金额");
                    return;
                }
                try {
                    money = Double.parseDouble(amount);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast("金额格式输入错误！");
                    stringBuilder.setLength(0);
                    money_et.setText("");
                    return;
                }
                uploaduploadOrder("","","","11",String.valueOf(money));

                break;
            case R.id.collection_code_tv://收款码
//                cashPayment = false;
//                amount = money_et.getText().toString();
//                LogUtil.i("收款金额：" + money);
//                if (TextUtils.isEmpty(amount)) {
//                    ToastUtil.showToast("请输入金额");
//                    return;
//                }
//                try {
//                    money = Double.parseDouble(amount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtil.showToast("金额格式输入错误！");
//                    stringBuilder.setLength(0);
//                    money_et.setText("");
//                    return;
//                }

//                Intent codeIntent = new Intent(this, CollectionCodeActivity.class);
//                codeIntent.putExtra("amount", money);
//                startActivity(codeIntent);
                break;
            case R.id.scan_tv://扫一扫
                cashPayment = false;
                toPay(2);
//                amount = money_et.getText().toString();
//                LogUtil.i("收款金额：" + money);
//                if (TextUtils.isEmpty(amount)) {
//                    ToastUtil.showToast("请输入金额");
//                    return;
//                }
//
//                try {
//                    money = Double.parseDouble(amount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtil.showToast("金额格式输入错误！");
//                    stringBuilder.setLength(0);
//                    money_et.setText("");
//                    return;
//                }
//
//                // TODO
//                onCreateOrder(2);
                break;

            case R.id.bank_card_tv: // 银行卡
                cashPayment = false;
                toPay(1);
//                amount = money_et.getText().toString();
//                LogUtil.i("收款金额：" + money);
//                if (TextUtils.isEmpty(amount)) {
//                    ToastUtil.showToast("请输入金额");
//                    return;
//                }
//
//                try {
//                    money = Double.parseDouble(amount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtil.showToast("金额格式输入错误！");
//                    stringBuilder.setLength(0);
//                    money_et.setText("");
//                    return;
//                }
//
//                onCreateOrder(1);
//

                break;

            case R.id.zero_tv:// 0
                stringBuilder.append("0");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.spot_tv://.
                stringBuilder.append(".");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.one_tv://1
                stringBuilder.append("1");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.two_tv://2
                stringBuilder.append("2");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.three_tv://3
                stringBuilder.append("3");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.four_tv://4
                stringBuilder.append("4");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.five_tv://5
                stringBuilder.append("5");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.six_tv://6
                stringBuilder.append("6");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.seven_tv://7
                stringBuilder.append("7");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.eight_tv://8
                stringBuilder.append("8");
                money_et.setText(stringBuilder.toString());
                break;
            case R.id.nine_tv://9
                stringBuilder.append("9");
                money_et.setText(stringBuilder.toString());
                break;

        }
    }


    /**
     * 更多按钮
     */
    private void showMore(View view) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_more_item, null);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        mPopupWindow.showAsDropDown(view, 0, 0);
        mPopupWindow.setTouchable(true);
//        mPopupWindow.setFocusable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAsDropDown(view);

        TextView tv_binding = popupView.findViewById(R.id.tv_binding);
        TextView tv_chang_pwd = popupView.findViewById(R.id.tv_chang_pwd);
        TextView tv_seting = popupView.findViewById(R.id.tv_seting);
        TextView tv_help = popupView.findViewById(R.id.tv_help);
        TextView tv_service = popupView.findViewById(R.id.tv_service);

        boolean role_boss = PreferencesUtils.getPreferenceBoolean("role_boss", false);
        tv_binding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!role_boss) {
                    ToastUtil.showToast("当前人员没有绑定权限，请换班为老板进行绑定。");
                } else {
                    mPopupWindow.dismiss();
                    startActivity(new Intent(MainActivity.this, DeviceBindingActivity.class));
                }
            }
        });
        tv_chang_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!role_boss) {
                    ToastUtil.showToast("当前人员没有修改密码权限，请换班为老板进行修改密码。");
                } else {
                    mPopupWindow.dismiss();
                    startActivity(new Intent(MainActivity.this, ChangePwdActivity.class));
                }
            }
        });
        tv_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!role_boss){
                    ToastUtil.showToast("当前人员没有设置权限，请换班为老板进行设置。");
                }else {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, SetingActivity.class));
                }
            }
        });
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
            }
        });

        tv_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, CustomerServiceActivity.class));
            }
        });

    }


    private void getDeviceInfo() {
        LogUtil.i("商终信息查询： ");
        try {
            ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
            Intent intent = new Intent();
            intent.setComponent(component);
            Bundle bundle = new Bundle();
            bundle.putString("msg_tp", "0000");
            bundle.putString("proc_cd", "100000");
            intent.putExtras(bundle);
            this.startActivityForResult(intent, DEVICE_CODE_OK);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showToast("调用对象不存在，请检查设备。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    // 撤销窗口
    private AlertDialog dialog;

    private void showRevokeDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View layout = inflater.inflate(R.layout.view_revoke_dialog, null);
        // 对话框
        dialog = new android.app.AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCancelable(false);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        EditText et_pwd = layout.findViewById(R.id.et_password);
        TextView tv_cancel = layout.findViewById(R.id.tv_cancel);
        TextView tv_submit = layout.findViewById(R.id.tv_submit);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRevoke(et_pwd.getText().toString());
            }
        });
    }

    // 进入撤销页面
    private void onRevoke(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast("请输入密码！");
        } else {
            String rP = PreferencesUtils.getPreferenceString("revoke_pwd", "123456");
            LogUtil.i(rP);
            if (pwd.equals(rP)) {
                dialog.dismiss();
                Intent revokeIntent = new Intent(this, RevokeActivity.class);
                startActivity(revokeIntent);
            } else {
                ToastUtil.showToast("密码输入有误。");
            }
        }
    }

    @Override
    public void getQaCategoryList(TranQueryBean tranQueryBean) {
        LogUtil.e("size ===========" + tranQueryBean.getData().getRecords().size());
        maxIndext = tranQueryBean.getData().getTotal();
        allPageNum = tranQueryBean.getData().getPages();
        if (null != tranQueryBean && null != tranQueryBean.getData() && 0 < tranQueryBean.getData().getRecords().size()) {
            listBean.addAll(tranQueryBean.getData().getRecords());
            switchTitleItem(indext);
        }
    }


    // 更新上一条下一条数据
    public void switchTitleItem(int numb) {
        LogUtil.e("numb ===========" + numb);
        if (listBean == null) {
            ToastUtil.showToast("没有数据了");
            return;
        }
        try {
            voucher_no.setText(listBean.get(numb).getOrderNo());
            String time = listBean.get(numb).getCreateTime();
            String t = time.substring(time.indexOf(":") - 2, time.lastIndexOf(":"));
            LogUtil.e(time + "=================" + t);
            time_tv.setText(t);
            switch (listBean.get(numb).getStatus()) {
                case 0: // 0已收款/未开票
                    status.setText("未开票");
                    break;
                case 1:// 1已上送订单/已填报
                    status.setText("已填报");
                    break;
                case 2:// 2已开票
                    status.setText("已开票");
                    break;
                case 3:// 3已退单
                    status.setText("已退单");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 支付
    private void toPay(int type){
        amount = money_et.getText().toString();
        LogUtil.i("收款金额：" + money);
        if (TextUtils.isEmpty(amount)) {
            ToastUtil.showToast("请输入金额");
            return;
        }
        try {
            money = Double.parseDouble(amount);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast("金额格式输入错误！");
            stringBuilder.setLength(0);
            money_et.setText("");
            return;
        }
        onCreateOrder(type);
    }


    // 创建订单
    private void onCreateOrder(int term_type) {
        String term_no = "";
        String busiType = "";

        if (term_type == 1) { //银行卡终端号
            term_no = Global.BANK_TERM_NO;
            busiType = "UPCARD";
        } else if (term_type == 2) { // 扫码终端号
            term_no = Global.CODE_TERM_NO;
            busiType = "SCPAY";
        }

        CreateOrderBean createOrderBean = new CreateOrderBean();
        createOrderBean.setTermId("A0010000");
        createOrderBean.setBusiType(busiType);

        try {
            JSONObject object = new JSONObject();
            object.put("merchantNo", Global.MERCHANT_NO); //结算商户号 必传
//            object.put("merchantNo", "822290070111135"); //结算商户号 必传
            object.put("termId", term_no); //终端号
            object.put("orderInfo", "消费支出"); //订单信息
            object.put("channelId", "2021052614391"); //渠道编号 必传
            object.put("busiTypeInfo", createOrderBean.toString()); //业务大类信息 必传 UPCARD刷卡  SCPAY扫码交易
            object.put("amount", (money * 100)); //订单金额，单位：分 必传
            mPresenter.onCreateOrder(term_type, object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getCreateQrderResult(int term_type, CreateOrderResultBean bean) {
        LogUtil.i(bean.toString());
        if (bean == null || bean.getData() == null) {
            ToastUtil.showToast("创建订单失败");
            LogUtil.i("创建订单失败" + bean.toString());
            return;
        }
        String out_order_no = bean.getData().getOut_order_no();//商户订单号

        if (term_type == 1) { //银行卡
//            Intent bankCardIntent = new Intent(this, BankCardActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("payOrderNo", payOrderNo);
//            bundle.putString("createTime", createTime);
//            bundle.putString("merchantOrderNo", merchantOrderNo);
//            bundle.putString("channelId", channelId);
//            bundle.putString("merchantNo", merchantNo);
//            bundle.putDouble("amount", money);
//            bankCardIntent.putExtras(bundle);
//            startActivity(bankCardIntent);
            bankPay(out_order_no);
        } else if (term_type == 2) { // 扫码
            scanPay(out_order_no);
        }
    }


    private void bankPay(String orderNo){
        LogUtil.i(" 消费-================ " );
        try {
            ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
            Intent intent = new Intent();
            intent.setComponent(component);
            Bundle bundle = new Bundle();
            bundle.putString("msg_tp", "0200");
            bundle.putString("pay_tp", "0");
            bundle.putString("proc_tp", "00");
            bundle.putString("proc_cd", "000000");
            bundle.putString("amt", String.valueOf(money));
            bundle.putString("order_no", orderNo);
            bundle.putString("appid", "com.lakala.pos");//传入自己应用的appId
            bundle.putString("time_stamp", System.currentTimeMillis() + "");
            bundle.putString("order_info", "用户银行卡刷卡支付");
            intent.putExtras(bundle);

            startActivityForResult(intent, BANK_CODE_OK);
        } catch (ActivityNotFoundException e) {
            LogUtil.e( e.toString());
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

    }



    private void scanPay(String orderNo){
        LogUtil.i(" 扫码支付-================ " );
        // 扫码支付
        try {
            ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
            Intent intent = new Intent();
            intent.setComponent(component);
            Bundle bundle = new Bundle();
            bundle.putString("msg_tp", "0200");
            bundle.putString("pay_tp", "1");
            bundle.putString("proc_tp", "00");
            bundle.putString("proc_cd", "660000");
            bundle.putString("amt", String.valueOf(money));
            bundle.putString("order_no", orderNo);
            bundle.putString("appid", "com.lakala.pos");//传入自己应用的appId
            bundle.putString("time_stamp", System.currentTimeMillis() + "");
            bundle.putString("order_info", "用户扫码付款");
            intent.putExtras(bundle);
            startActivityForResult(intent, SCAN_CODE_OK);
        } catch (ActivityNotFoundException e) {
            LogUtil.e( e.toString());
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("sdk 返回信息：  requestCode=" + requestCode + "  resultCode =" + resultCode);

        if (data == null){
            LogUtil.i("data == null");
            return;
        }
        String reason = data.getExtras().getString("reason");
        String code = data.getExtras().getString("rescode");//响应吗
        String message = data.getExtras().getString("reason");//相应信息
        String datas = data.getExtras().getString("data");//相应数据

        switch (resultCode) {
            // 成功
            case Activity.RESULT_OK:
                if (requestCode == DEVICE_CODE_OK) {
                    if (data != null) {
                        LogUtil.i("data != null： ");
                        Global.MERCHANT_NO = data.getExtras().getString("merchant_no"); // 商户号
                        Global.BANK_TERM_NO = data.getExtras().getString("bank_term_no"); // 银行卡终端号
                        Global.CODE_TERM_NO = data.getExtras().getString("code_term_no"); // 扫码终端号
                        Global.FBANK_TERM_NO = data.getExtras().getString("fbank_term_no"); // 外卡终端号
                        Global.REASON = data.getExtras().getString("reason"); // 失败原因
                    }
                    LogUtil.i("商户号:" + Global.MERCHANT_NO + "   银行卡终端号:" + Global.BANK_TERM_NO + "    扫码终端号:" + Global.CODE_TERM_NO
                            + "   外卡终端号:" + Global.FBANK_TERM_NO + "   失败原因:" + Global.REASON);
                } else if (requestCode == SCAN_CODE_OK) { // 扫码
                    initScanPayData(data);
                }else if (requestCode == BANK_CODE_OK){ // 银行卡
                    initBankPayData(data);
                }
                LogUtil.i("响应吗：" + code + "      响应错误信息：" + message + "\n\r 响应数据：" + datas);
                break;
            // 支付取消
            case Activity.RESULT_CANCELED:
                if (reason != null) {
                    ToastUtil.showToast(reason);
                }
                LogUtil.i("交易取消 :   响应吗：" + code + "       响应错误信息：" + message + "\n\r 响应数据：" + datas);
                break;
            // 交易失败
            case -2:
                if (reason != null) {
                    ToastUtil.showToast(" 交易失败：\n\n\r" + reason + message);
                }
                LogUtil.i("交易失败 :   响应吗：" + code + "       响应错误信息：" + message + "\n\r 响应数据：" + datas);
                break;
            default:
                break;
        }
    }


    // 扫码支付返回信息
    private void initScanPayData(Intent data){
        // 报文类型
        String msg_tp = data.getExtras().getString("msg_tp");
        // 支付方式
        String pay_tp = data.getExtras().getString("pay_tp");
        // 检索参考号
        String refernumber = data.getExtras().getString("refernumber");
        // 订单号
        String order_no = data.getExtras().getString("order_no");
        // 交易时间戳
        String time_stamp = data.getExtras().getString("time_stamp");
        // 失败原因
        String reason = data.getExtras().getString("reason");
        // 交易详情
        TransactionEntity transactionEntity = new Gson().fromJson(data.getExtras().getString("txndetail"), TransactionEntity.class);
        // 附加数据
        String adddataword = data.getExtras().getString("adddataword");
        //备注信息
        String remarkinfo = data.getExtras().getString("remarkinfo");
        //实付金额(余额)
        String amt = data.getExtras().getString("amt");
        //订单金额
        String orderAmt = data.getExtras().getString("orderamt");
        //折扣金额
        String discountAmt = data.getExtras().getString("discountamt");

        uploaduploadOrder(transactionEntity.getOrderid_scan(),transactionEntity.getBatchno(),transactionEntity.getSystraceno(),pay_tp,amt);

    }
    // 银行卡支付返回信息
    private void initBankPayData(Intent data){
        // 报文类型
        String msg_tp = data.getExtras().getString("msg_tp");
        // 支付方式
        String pay_tp = data.getExtras().getString("pay_tp");
        // 检索参考号
        String refernumber = data.getExtras().getString("refernumber");
        // 订单号
        String order_no = data.getExtras().getString("order_no");
        // 卡号
        String card_no = data.getExtras().getString("card_no");
        // 交易时间戳
        String time_stamp = data.getExtras().getString("time_stamp");
        // 失败原因
        String reason = data.getExtras().getString("reason");
        // 附加数据
        String adddataword = data.getExtras().getString("adddataword");
        // 交易详情
        TransactionEntity transactionEntity = new Gson().fromJson(data.getExtras().getString("txndetail"), TransactionEntity.class);
        // 卡组织
        String card_org = data.getExtras().getString("card_org");
        //备注信息
        String remarkinfo = data.getExtras().getString("remarkinfo");
        //实付金额(余额)
        String amt = data.getExtras().getString("amt");
        //订单金额
        String orderAmt = data.getExtras().getString("orderamt");
        //折扣金额
        String discountAmt = data.getExtras().getString("discountamt");

        uploaduploadOrder(transactionEntity.getOrderid_scan(),transactionEntity.getBatchno(),transactionEntity.getSystraceno(),pay_tp,amt);
    }


    // 上送订单
    private void uploaduploadOrder(String orderNo,String batchNo,String voucherNo,String payType,String amt){

       String reviewed =  PreferencesUtils.getPreferenceString("checker", "");//复核人
       String drawer =  PreferencesUtils.getPreferenceString("drawer", "");//开票人
       String admin =  PreferencesUtils.getPreferenceString("admin", "");

        try {
            JSONObject object = new JSONObject();
            object.put("checker",reviewed);//复核人
            object.put("payee",admin);//收款人
            object.put("drawer",drawer);//开票人
            object.put("orderNo",orderNo);//卡拉卡生成的订单号
            object.put("deviceCode",Global.DEVICE_ID);//设备号
            object.put("orderSort", "000"+Global.ORDER_SORT);//该设备下订单序号
            object.put("invoiceMark",invoiceSwitch ? 1 : 0);//0不开发票 1开发票
            object.put("batchNo",batchNo);//批次号
            object.put("voucherNo",voucherNo);//凭证号
            object.put("payType",payType);//支付方式
            object.put("amount",amt);//支付金额

            mPresenter.uploaduploadOrder(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    // 上送订单返回
    @Override
    public void uploaduploadOrderResult(String result) {
        Global.ORDER_SORT ++;
        PreferencesUtils.setPreferenceInt("ORDER_SORT",Global.ORDER_SORT);
        time_tv.setText("000" + Global.ORDER_SORT);

        if (cashPayment && invoiceSwitch){ //现金支付 且 开票开关开着 弹出开票码
            billingCodeDialog = new BillingCodeDialog().newInstance("billingCode");
            billingCodeDialog.show(getFragmentManager(), "billingCodeDialog");
        }
    }



}