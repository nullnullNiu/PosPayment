package com.lakala.pos.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.lakala.pos.R;
import com.lakala.pos.adapter.ViewPagerAdapter;
import com.lakala.pos.interfaces.IBankCardView;
import com.lakala.pos.presente.BankCardPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.DateTimeUtil;
import com.lakala.pos.utils.LogUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BankCardActivity extends MVPActivity<IBankCardView, BankCardPresenter> implements IBankCardView {

    @BindView(R.id.tv_money_lalue)
    TextView money;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.dot_1)
    RadioButton dot1;

    @BindView(R.id.dot_2)
    RadioButton dot2;

    @BindView(R.id.dot_3)
    RadioButton dot3;
    List<View> pages = new ArrayList<>();
    View first, second, third;
    LayoutInflater layoutInflater;
    ViewPagerAdapter viewPagerAdapter;

    double amount = 0;

    String payOrderNo = "";
    String createTime = "";
    String merchantOrderNo = "";
    String channelId = "";
    String merchantNo = "";


    @Override
    protected BankCardPresenter createPresenter() {
        return new BankCardPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        ButterKnife.bind(this);

        setOrderInfo();
//        showProposalView(this);

        initView();
    }


    // ?????????????????????
    private void setOrderInfo() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        Bundle bundle = intent.getExtras();
        LogUtil.i("????????????????????? ???  " + bundle.toString());
        if (intent.hasExtra("orderInfo")) {
            payOrderNo = bundle.getString("payOrderNo");
        }

        if (intent.hasExtra("createTime")) {
            createTime = bundle.getString("createTime");
        }

        if (intent.hasExtra("merchantOrderNo")) {
            merchantOrderNo = bundle.getString("merchantOrderNo");
        }

        if (intent.hasExtra("channelId")) {
            channelId = bundle.getString("channelId");
        }

        if (intent.hasExtra("merchantNo")) {
            merchantNo = bundle.getString("merchantNo");
        }

        if (getIntent().hasExtra("amount")) {
            amount = getIntent().getDoubleExtra("amount", 0);
            money.setText("???" + amount);
            LogUtil.i("?????? ??? " + amount + "");
        }
        pay();
    }

    private void pay(){
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
            bundle.putString("amt", amount+"");
            bundle.putString("order_no", merchantOrderNo);
            bundle.putString("appid", "com.lakala.pos");//?????????????????????appId
            bundle.putString("time_stamp", System.currentTimeMillis() + "");
//            bundle.putString("pay_order_no", payOrderNo);
            bundle.putString("order_info", "????????????");
//            bundle.putString("print_info", "????????????");
//            bundle.putString("remarkinfo", "????????????");
            intent.putExtras(bundle);

            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            LogUtil.e( e.toString());
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("?????????????????? ???????????????  requestCode=" + requestCode + "  resultCode =" + resultCode);
        String code = data.getExtras().getString("rescode");
        String reason = data.getExtras().getString("reason");
        LogUtil.i("?????????  code=" + code + "  reason =" + reason);


        switch (resultCode) {
            // ??????
            case Activity.RESULT_OK:
                if (requestCode == 1) {
                    if (data != null) {
                        LogUtil.i("data != null??? ");
                        String msg_tp = data.getExtras().getString("msg_tp"); // ?????????

                        LogUtil.i("msg_tp:" + msg_tp);
                    }
                }

                break;
            // ??????
            case Activity.RESULT_CANCELED:
                showProposalView(this);
                break;
            case -2:

                break;
            default:
                break;
        }
    }

    private void initView() {
        setDot(true, false, false);
        //??????????????????
        layoutInflater = getLayoutInflater();
        first = layoutInflater.inflate(R.layout.fragment_first, null);//???????????????
        second = layoutInflater.inflate(R.layout.fragment_second, null);//???????????????
        third = layoutInflater.inflate(R.layout.fragment_third, null);//???????????????
        //??????????????????????????????????????????
        pages.add(first);
        pages.add(second);
        pages.add(third);

        viewPagerAdapter = new ViewPagerAdapter(pages); //?????????????????????
        viewPager.setAdapter(viewPagerAdapter); // ???????????????

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //????????????
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //????????????
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setDot(true, false, false);
                        break;
                    case 1:
                        setDot(false, true, false);
                        break;
                    case 2:
                        setDot(false, false, true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @OnClick({R.id.back_tv,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// ??????

                finish();

                break;

        }
    }

    @Override
    public void versionAppUpdateView() {

    }


    /**
     * ???????????? ????????????
     */
    public void showProposalView(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.exception_reminder_dialog, null);
        builder.setView(dialogView);
        TextView reTry = dialogView.findViewById(R.id.tv_re_try);
        TextView cancel = dialogView.findViewById(R.id.tv_cancel);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.show();

        reTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });
    }


    //???????????????????????????????????????????????????????????????)
    private void setDot(boolean selectA, boolean selectB, boolean selectC) {

        if (selectA) {
            dot1.setBackgroundResource(R.mipmap.dot_select);
        } else {
            dot1.setBackgroundResource(R.mipmap.dot_unchecked);
        }
        if (selectB) {
            dot2.setBackgroundResource(R.mipmap.dot_select);
        } else {
            dot2.setBackgroundResource(R.mipmap.dot_unchecked);
        }
        if (selectC) {
            dot3.setBackgroundResource(R.mipmap.dot_select);
        } else {
            dot3.setBackgroundResource(R.mipmap.dot_unchecked);
        }
    }

}