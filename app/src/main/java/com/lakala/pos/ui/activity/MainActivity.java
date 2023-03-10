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
import android.text.format.DateUtils;
import android.util.Log;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.R;
import com.lakala.pos.bean.CreateOrderBean;
import com.lakala.pos.bean.CreateOrderResultBean;
import com.lakala.pos.bean.SubmitOrderBean;
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

    long time = 0;
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
        judge();
    }

    private void judge(){
        Global.ORDER_SORT = PreferencesUtils.getPreferenceInt("ORDER_SORT",0);
        if (Global.ORDER_SORT == 0){
            tv_title.setText("00" + Global.ORDER_SORT);
            return;
        }

        time = PreferencesUtils.getPreferenceLoging("DATE_TIME",0);
        if (time == 0){
            time = System.currentTimeMillis();
            PreferencesUtils.setPreferenceLoging("DATE_TIME",time);
            return;
        }

       boolean isToday = DateUtils.isToday(time);
        LogUtil.i(" ?????????????????? " +
                com.lakala.pos.utils.DateUtils.millis2String(time,com.lakala.pos.utils.DateUtils.yyyyMMddHHmmssSSS.get()));

       if (!isToday){
           LogUtil.i("????????????,???????????? ???????????????");
           Global.ORDER_SORT = 0;
           PreferencesUtils.setPreferenceInt("ORDER_SORT",Global.ORDER_SORT);
        }

        tv_title.setText("00" + Global.ORDER_SORT);

    }

    // ??????????????????
    public boolean getDownladPermis() {
        PermisName = "??????";
        isNeedCheck = false;
        onCheckPermis(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return isNeedCheck;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        invoiceSwitch = isChecked;
        PreferencesUtils.setPreferenceBoolean("invoice_switch", isChecked);
        LogUtil.i("???????????????     " + isChecked);
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
            case R.id.im_more://??????
                if (mPopupWindow == null || !mPopupWindow.isShowing()) {
                    showMore(im_more);
                } else {
                    mPopupWindow.dismiss();
                }


                break;

            case R.id.shift_change://??????

                Intent i = new Intent(this, ShiftChangeActivity.class);
                startActivity(i);

                break;
            case R.id.tv_deletd://??????
                stringBuilder.setLength(0);
                money_et.setText("");

                break;
            case R.id.previous_tv://?????????
                if (indext < 1) {
                    ToastUtil.showToast("????????????????????????????????????");
                    return;
                }
                indext--;
                switchTitleItem(indext);
                break;
            case R.id.next_tv://?????????
                LogUtil.e(maxIndext + "   " + indext + "   " + maxIndext);
                if (maxIndext != 0 && indext >= maxIndext - 1 || indext >= listBean.size() - 1) {
                    if (allPageNum > pageNum) {
                        pageNum++;
                        indext++;
                        mPresenter.queryOrders(pageNum);
                    } else {
                        ToastUtil.showToast("???????????????????????????????????????");
                    }
                    return;
                }

                indext++;
                switchTitleItem(indext);
                break;

            case R.id.select_tv://??????

                startActivity(new Intent(this, TranQueryActivity.class));


                break;
            case R.id.revoke_tv://??????
                showRevokeDialog();
                break;

            case R.id.cash_receipt_tv://?????????
                cashPayment = true;
                amount = money_et.getText().toString();
                LogUtil.i("???????????????" + amount);
                if (TextUtils.isEmpty(amount)) {
                    ToastUtil.showToast("???????????????");
                    return;
                }
                try {
                    money = Double.parseDouble(amount);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast("???????????????????????????");
                    stringBuilder.setLength(0);
                    money_et.setText("");
                    return;
                }
                uploaduploadOrder("","","","11",String.valueOf(money));

                break;
            case R.id.collection_code_tv://?????????
//                cashPayment = false;
//                amount = money_et.getText().toString();
//                LogUtil.i("???????????????" + money);
//                if (TextUtils.isEmpty(amount)) {
//                    ToastUtil.showToast("???????????????");
//                    return;
//                }
//                try {
//                    money = Double.parseDouble(amount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtil.showToast("???????????????????????????");
//                    stringBuilder.setLength(0);
//                    money_et.setText("");
//                    return;
//                }

//                Intent codeIntent = new Intent(this, CollectionCodeActivity.class);
//                codeIntent.putExtra("amount", money);
//                startActivity(codeIntent);
                break;
            case R.id.scan_tv://?????????
                cashPayment = false;
                toPay(2);
//                amount = money_et.getText().toString();
//                LogUtil.i("???????????????" + money);
//                if (TextUtils.isEmpty(amount)) {
//                    ToastUtil.showToast("???????????????");
//                    return;
//                }
//
//                try {
//                    money = Double.parseDouble(amount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtil.showToast("???????????????????????????");
//                    stringBuilder.setLength(0);
//                    money_et.setText("");
//                    return;
//                }
//
//                // TODO
//                onCreateOrder(2);
                break;

            case R.id.bank_card_tv: // ?????????
                cashPayment = false;
                toPay(1);
//                amount = money_et.getText().toString();
//                LogUtil.i("???????????????" + money);
//                if (TextUtils.isEmpty(amount)) {
//                    ToastUtil.showToast("???????????????");
//                    return;
//                }
//
//                try {
//                    money = Double.parseDouble(amount);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtil.showToast("???????????????????????????");
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
     * ????????????
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
                    ToastUtil.showToast("??????????????????????????????????????????????????????????????????");
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
                    ToastUtil.showToast("??????????????????????????????????????????????????????????????????????????????");
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
                    ToastUtil.showToast("??????????????????????????????????????????????????????????????????");
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
        LogUtil.i("????????????????????? ");
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
            ToastUtil.showToast("??????????????????????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    // ????????????
    private AlertDialog dialog;

    private void showRevokeDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View layout = inflater.inflate(R.layout.view_revoke_dialog, null);
        // ?????????
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

    // ??????????????????
    private void onRevoke(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast("??????????????????");
        } else {
            String rP = PreferencesUtils.getPreferenceString("revoke_pwd", "123456");
            LogUtil.i(rP);
            if (pwd.equals(rP)) {
                dialog.dismiss();
                Intent revokeIntent = new Intent(this, RevokeActivity.class);
                startActivity(revokeIntent);
            } else {
                ToastUtil.showToast("?????????????????????");
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


    // ??????????????????????????????
    public void switchTitleItem(int numb) {
        LogUtil.e("numb ===========" + numb);
        if (listBean == null) {
            ToastUtil.showToast("???????????????");
            return;
        }
        try {
            voucher_no.setText("????????????" + listBean.get(numb).getVoucherNo());
            String time = listBean.get(numb).getCreateTime();
            String t = time.substring(time.indexOf(":") - 2, time.lastIndexOf(":"));
            LogUtil.e(time + "=================" + t);
            time_tv.setText(t);
            switch (listBean.get(numb).getStatus()) {
                case 0: // 0?????????/?????????
                    status.setText("?????????");
                    break;
                case 1:// 1???????????????/?????????
                    status.setText("?????????");
                    break;
                case 2:// 2?????????
                    status.setText("?????????");
                    break;
                case 3:// 3?????????
                    status.setText("?????????");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(e.toString());
        }
    }

    // ??????
    private void toPay(int type){
        amount = money_et.getText().toString();
        LogUtil.i("???????????????" + money);
        if (TextUtils.isEmpty(amount)) {
            ToastUtil.showToast("???????????????");
            return;
        }
        try {
            money = Double.parseDouble(amount);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast("???????????????????????????");
            stringBuilder.setLength(0);
            money_et.setText("");
            return;
        }
        onCreateOrder(type);
    }


    // ????????????
    private void onCreateOrder(int term_type) {
        String term_no = "";
        String busiType = "";

        if (term_type == 1) { //??????????????????
            term_no = Global.BANK_TERM_NO;
            busiType = "UPCARD";
        } else if (term_type == 2) { // ???????????????
            term_no = Global.CODE_TERM_NO;
            busiType = "SCPAY";
        }

        CreateOrderBean createOrderBean = new CreateOrderBean();
        createOrderBean.setTermId("A0010000");
        createOrderBean.setBusiType(busiType);


        try {
            JSONObject object = new JSONObject();
            object.put("merchantNo", Global.MERCHANT_NO); //??????????????? ??????
            object.put("termId", term_no); //?????????
            object.put("orderInfo", "????????????"); //????????????
            object.put("channelId", "2021052614391"); //???????????? ??????
            object.put("busiTypeInfo", createOrderBean.toString()); //?????????????????? ?????? UPCARD??????  SCPAY????????????
            object.put("amount", (money * 100)); //??????????????????????????? ??????
            mPresenter.onCreateOrder(term_type, object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getCreateQrderResult(int term_type, CreateOrderResultBean bean) {
        LogUtil.i(bean.toString());
        if (bean == null || bean.getData() == null) {
            ToastUtil.showToast("??????????????????");
            LogUtil.i("??????????????????" + bean.toString());
            return;
        }
        String out_order_no = bean.getData().getOut_order_no();//???????????????

        if (term_type == 1) { //?????????
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
        } else if (term_type == 2) { // ??????
            scanPay(out_order_no);
        }
    }


    private void bankPay(String orderNo){
        LogUtil.i(" ??????-================ " );
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
            bundle.putString("appid", "com.lakala.pos");//?????????????????????appId
            bundle.putString("time_stamp", System.currentTimeMillis() + "");
            bundle.putString("order_info", "???????????????????????????");
            intent.putExtras(bundle);

            startActivityForResult(intent, BANK_CODE_OK);
        } catch (ActivityNotFoundException e) {
            LogUtil.e( e.toString());
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

    }



    private void scanPay(String orderNo){
        LogUtil.i(" ????????????-================ " );
        // ????????????
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
            bundle.putString("appid", "com.lakala.pos");//?????????????????????appId
            bundle.putString("time_stamp", System.currentTimeMillis() + "");
            bundle.putString("order_info", "??????????????????");
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
        LogUtil.i("sdk ???????????????  requestCode=" + requestCode + "  resultCode =" + resultCode);

        if (data == null){
            ToastUtil.showToast("???????????????????????????????????????");
            LogUtil.i("data == null");
            return;
        }
        String reason = data.getExtras().getString("reason");
        String code = data.getExtras().getString("rescode");//?????????
        String message = data.getExtras().getString("reason");//????????????
        String datas = data.getExtras().getString("data");//????????????

        switch (resultCode) {
            // ??????
            case Activity.RESULT_OK:
                if (requestCode == DEVICE_CODE_OK) {
                    if (data != null) {
                        LogUtil.i("data != null??? ");
//                        Global.MERCHANT_NO = data.getExtras().getString("merchant_no"); // ?????????
//                        Global.BANK_TERM_NO = data.getExtras().getString("bank_term_no"); // ??????????????????
//                        Global.CODE_TERM_NO = data.getExtras().getString("code_term_no"); // ???????????????
                        Global.FBANK_TERM_NO = data.getExtras().getString("fbank_term_no"); // ???????????????
                        Global.REASON = data.getExtras().getString("reason"); // ????????????
                    }
                    LogUtil.i("?????????:" + Global.MERCHANT_NO + "   ??????????????????:" + Global.BANK_TERM_NO + "    ???????????????:" + Global.CODE_TERM_NO
                            + "   ???????????????:" + Global.FBANK_TERM_NO + "   ????????????:" + Global.REASON);
                } else if (requestCode == SCAN_CODE_OK) { // ??????
                    initScanPayData(data);
                }else if (requestCode == BANK_CODE_OK){ // ?????????
                    initBankPayData(data);
                }
                LogUtil.i("????????????" + code + "      ?????????????????????" + message + "\n\r ???????????????" + datas);
                break;
            // ????????????
            case Activity.RESULT_CANCELED:
                if (reason != null) {
                    ToastUtil.showToast(reason);
                    LogUtil.i("reason???" + reason);
                }
                LogUtil.i("???????????? :   ????????????" + code + "       ?????????????????????" + message + "\n\r ???????????????" + datas);
                break;
            // ????????????
            case -2:
                if (reason != null) {
                    ToastUtil.showToast(" ???????????????\n\n\r" + reason + message);
                    LogUtil.i("reason???" + reason);
                }
                LogUtil.i("???????????? :   ????????????" + code + "       ?????????????????????" + message + "\n\r ???????????????" + datas);
                break;
            default:
                break;
        }
    }


    // ????????????????????????
    private void initScanPayData(Intent data){
        // ????????????
        String msg_tp = data.getExtras().getString("msg_tp");
        // ????????????
        String pay_tp = data.getExtras().getString("pay_tp");
        // ???????????????
        String refernumber = data.getExtras().getString("refernumber");
        // ?????????
        String order_no = data.getExtras().getString("order_no");
        // ???????????????
        String time_stamp = data.getExtras().getString("time_stamp");
        // ????????????
        String reason = data.getExtras().getString("reason");
        // ????????????
        TransactionEntity transactionEntity = new Gson().fromJson(data.getExtras().getString("txndetail"), TransactionEntity.class);
        // ????????????
        String adddataword = data.getExtras().getString("adddataword");
        //????????????
        String remarkinfo = data.getExtras().getString("remarkinfo");
        //????????????(??????)
        String amt = data.getExtras().getString("amt");
        //????????????
        String orderAmt = data.getExtras().getString("orderamt");
        //????????????
        String discountAmt = data.getExtras().getString("discountamt");

        LogUtil.i("????????????:" + msg_tp + "   ????????????:" + pay_tp+ "    ???????????????:" + refernumber
                + "   ?????????:" + order_no + "   ???????????????:" + time_stamp +"    ????????????" + transactionEntity.toString()
                + "   ????????????:" + adddataword + "   ????????????:" + remarkinfo +"    ????????????" + amt
                + "   ????????????:" + orderAmt + "   ????????????:" + discountAmt);
        uploaduploadOrder(transactionEntity.getOrderid_scan(),transactionEntity.getBatchno(),transactionEntity.getSystraceno(),pay_tp,amt);

    }
    // ???????????????????????????
    private void initBankPayData(Intent data){
        // ????????????
        String msg_tp = data.getExtras().getString("msg_tp");
        // ????????????
        String pay_tp = data.getExtras().getString("pay_tp");
        // ???????????????
        String refernumber = data.getExtras().getString("refernumber");
        // ?????????
        String order_no = data.getExtras().getString("order_no");
        // ??????
        String card_no = data.getExtras().getString("card_no");
        // ???????????????
        String time_stamp = data.getExtras().getString("time_stamp");
        // ????????????
        String reason = data.getExtras().getString("reason");
        // ????????????
        String adddataword = data.getExtras().getString("adddataword");
        // ????????????
        TransactionEntity transactionEntity = new Gson().fromJson(data.getExtras().getString("txndetail"), TransactionEntity.class);
        // ?????????
        String card_org = data.getExtras().getString("card_org");
        //????????????
        String remarkinfo = data.getExtras().getString("remarkinfo");
        //????????????(??????)
        String amt = data.getExtras().getString("amt");
        //????????????
        String orderAmt = data.getExtras().getString("orderamt");
        //????????????
        String discountAmt = data.getExtras().getString("discountamt");

        LogUtil.i("????????????:" + msg_tp + "   ????????????:" + pay_tp+ "    ???????????????:" + refernumber
                + "   ?????????:" + order_no + "   ?????????" +card_no +  "   ???????????????:" + time_stamp +"    ????????????" + transactionEntity.toString()
                + "   ????????????:" + adddataword + "   ????????????:" + remarkinfo +"    ????????????" + amt + "  ?????????:"  +card_org
                + "   ????????????:" + orderAmt + "   ????????????:" + discountAmt);

        uploaduploadOrder(transactionEntity.getOrderid_scan(),transactionEntity.getBatchno(),transactionEntity.getSystraceno(),pay_tp,amt);
    }


    // ????????????
    private void uploaduploadOrder(String orderNo,String batchNo,String voucherNo,String payType,String amt){

       String reviewed =  PreferencesUtils.getPreferenceString("checker", "");//?????????
       String drawer =  PreferencesUtils.getPreferenceString("drawer", "");//?????????
       String admin =  PreferencesUtils.getPreferenceString("admin", "");

        try {
            JSONObject object = new JSONObject();
            object.put("checker",reviewed);//?????????
            object.put("payee",admin);//?????????
            object.put("drawer",drawer);//?????????
            object.put("orderNo",orderNo);//???????????????????????????
            object.put("deviceCode",Global.DEVICE_ID);//?????????
            object.put("orderSort", "000"+Global.ORDER_SORT);//????????????????????????
            object.put("invoiceMark",invoiceSwitch ? 1 : 0);//0???????????? 1?????????
            object.put("batchNo",batchNo);//?????????
            object.put("voucherNo",voucherNo);//?????????
            object.put("payType",payType);//????????????
            object.put("amount",amt);//????????????

            mPresenter.uploaduploadOrder(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    // ??????????????????
    @Override
    public void uploaduploadOrderResult(SubmitOrderBean bean) {
        LogUtil.i("????????????????????????????????? "+bean.toString());
        Global.ORDER_SORT ++;
        PreferencesUtils.setPreferenceInt("ORDER_SORT",Global.ORDER_SORT);
        tv_title.setText("00" + Global.ORDER_SORT);
//        pageNum =1;
        mPresenter.queryOrders(pageNum);

        if (cashPayment && invoiceSwitch){
            if (null == bean || null == bean.getData()) {
                ToastUtil.showToast("???????????????????????????????????????");
            } else {
                if (TextUtils.isEmpty(bean.getData().getUrl())){
                    ToastUtil.showToast("???????????????????????????????????????");
                }else {
                    billingCodeDialog = new BillingCodeDialog().newInstance(bean.getData().getUrl());
                    billingCodeDialog.show(getFragmentManager(), "billingCodeDialog");
                }
            }
        }else {
            ToastUtil.showToast("???????????????");
        }

    }


}