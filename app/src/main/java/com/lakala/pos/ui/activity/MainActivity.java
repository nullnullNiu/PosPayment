package com.lakala.pos.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;

import com.lakala.pos.R;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.PreferencesUtils;
import com.lakala.pos.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MVPActivity<IHomeView, MainActivityPresenter> implements IHomeView {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "NonConstantResourceId"})
    @BindView(R.id.swichbutton_invoicing)
    Switch btnSwitch;

    @BindView(R.id.money_et)
    TextView money_et;

    @BindView(R.id.im_more)
    ImageView im_more;

    String amount = "";
    double money = 0.0;

    StringBuilder stringBuilder = new StringBuilder();
    private PopupWindow mPopupWindow;

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        checkToken();
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


                break;
            case R.id.next_tv://下一条


                break;

            case R.id.select_tv://查询

                startActivity(new Intent(this, TranQueryActivity.class));


                break;
            case R.id.revoke_tv://撤销
                Intent revokeIntent = new Intent(this, RevokeActivity.class);
                startActivity(revokeIntent);
                break;

            case R.id.cash_receipt_tv://收现金
                amount = money_et.getText().toString();
                LogUtil.i("收款金额：" + money);
                if (TextUtils.isEmpty(amount)) {
                    ToastUtil.showToast("请输入金额");
                    return;
                }
                money = Double.parseDouble(amount);
                break;
            case R.id.collection_code_tv://收款码
                amount = money_et.getText().toString();
                LogUtil.i("收款金额：" + money);
                if (TextUtils.isEmpty(amount)) {
                    ToastUtil.showToast("请输入金额");
                    return;
                }
                money = Double.parseDouble(amount);

                Intent codeIntent = new Intent(this, CollectionCodeActivity.class);
                codeIntent.putExtra("amount", money);
                startActivity(codeIntent);
                break;
            case R.id.scan_tv://扫一扫
                amount = money_et.getText().toString();
                LogUtil.i("收款金额：" + money);
                if (TextUtils.isEmpty(amount)) {
                    ToastUtil.showToast("请输入金额");
                    return;
                }
                money = Double.parseDouble(amount);
                // TODO

                break;

            case R.id.bank_card_tv: // 银行卡
                amount = money_et.getText().toString();
                LogUtil.i("收款金额：" + money);
                if (TextUtils.isEmpty(amount)) {
                    ToastUtil.showToast("请输入金额");
                    return;
                }
                money = Double.parseDouble(amount);


                Intent bankCardIntent = new Intent(this, BankCardActivity.class);
                bankCardIntent.putExtra("amount", money);
                startActivity(bankCardIntent);

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

        tv_binding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, DeviceBindingActivity.class));
            }
        });
        tv_chang_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, ChangePwdActivity.class));
            }
        });
        tv_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, SetingActivity.class));
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
//        if (mPopupWindow.isShowing()){
//            LogUtil.i("11111111");
//            mPopupWindow.dismiss();
//        } else {
//            LogUtil.i("222222222");
//            mPopupWindow.showAsDropDown(view, 0, 0);
//        }


    }

    @Override
    public void versionAppUpdateView() {

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
            this.startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e){
            ToastUtil.showToast("调用对象不存在，请检查设备。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("商终信息查询 返回信息：  requestCode=" + requestCode + "  resultCode =" + resultCode);

        switch (resultCode) {
            // 成功
            case Activity.RESULT_OK:
                if (requestCode == 1) {
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
                }

                break;
            // 取消
            case Activity.RESULT_CANCELED:

                break;
            case -2:

                break;
            default:
                break;
        }
    }


}