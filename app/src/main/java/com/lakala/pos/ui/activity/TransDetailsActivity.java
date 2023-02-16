package com.lakala.pos.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lakala.pos.R;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.bean.TransDetailsBean;
import com.lakala.pos.common.Global;
import com.lakala.pos.dialog.BillingCodeDialog;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.presente.TransPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.DateTimeUtil;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TransDetailsActivity extends MVPActivity<ITransView, TransPresenter> implements ITransView {

    @BindView(R.id.trans_data)
    TextView trans_data; //交易时间

    @BindView(R.id.trans_type)
    TextView trans_type; //交易类型

    @BindView(R.id.batch_no)
    TextView batch_no; //批次号

    @BindView(R.id.voucher_no)
    TextView voucher_no;//凭证号

    @BindView(R.id.order_amount)
    TextView order_amount;//订单金额

    @BindView(R.id.paid_in_amount)
    TextView paid_in_amount;//实付金额

    @BindView(R.id.invoicing_code)
    TextView invoicing_code;//生成开票码

    @BindView(R.id.complete_invoicing)
    TextView complete_invoicing;//完成开发票




    String orderNo = "";
    BillingCodeDialog billingCodeDialog;
    String billingCode = "";
    @Override
    protected TransPresenter createPresenter() {
        return new TransPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_details);
        ButterKnife.bind(this);


        orderNo = getIntent().getStringExtra("orderNo");

        queryOrderByOrderId(orderNo);
    }


    private void queryOrderByOrderId(String id){
        LogUtil.i("orderNo :"  +orderNo);
        if (TextUtils.isEmpty(orderNo)){
            ToastUtil.showToast("查询订单详情失败");
            return;
        }

        try {
            JSONObject object = new JSONObject();
            object.put("orderNo",orderNo);
            mPresenter.queryOrderByOrderId(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.back_tv, R.id.reprint_tv, R.id.draw_bill_tv,R.id.invoicing_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.reprint_tv:// 重打印
                Print();
                break;
            case R.id.draw_bill_tv:// 开发票

                if (TextUtils.isEmpty(billingCode)){
                    ToastUtil.showToast("开票码为空！");
                    return;
                }
                billingCodeDialog = new BillingCodeDialog().newInstance(billingCode);
                billingCodeDialog.show(getFragmentManager(), "billingCodeDialog");
                break;

            case R.id.invoicing_code: // 开票码

                if (TextUtils.isEmpty(billingCode)){
                    ToastUtil.showToast("开票码为空！");
                    return;
                }
                billingCodeDialog = new BillingCodeDialog().newInstance(billingCode);
                billingCodeDialog.show(getFragmentManager(), "billingCodeDialog");
                break;
        }
    }


    @Override
    public void countByDeviceIdResult(String result) {

    }

    @Override
    public void queryOrdersDetailsResult(TransDetailsBean bean) {
        if(bean.getData() == null){
            ToastUtil.showToast("数据信息为空");
            return;
        }

        trans_data.setText(bean.getData().getTradeTime()); //交易时间

        trans_type.setText(bean.getData().getTradeType()); //交易类型

        batch_no.setText(bean.getData().getBatchNo()); //批次号

        voucher_no.setText(bean.getData().getVoucherNo());//凭证号

        order_amount.setText(bean.getData().getAmount()+"");//订单金额

        paid_in_amount.setText(bean.getData().getPayAmount()+"");//实付金额

        invoicing_code.setText(bean.getData().getCreateTime());//生成开票码

        complete_invoicing.setText(bean.getData().getUpdateTime());//完成开发票

        billingCode = bean.getData().getScanUrl();

    }

    @Override
    public void getDate(String date, int type) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingCodeDialog != null){
            billingCodeDialog.dismissAllowingStateLoss();
        }
    }


    private void Print(){

        //    银行卡交易重打印
        try {
            LogUtil.e(" 打印 ==============");
            ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
            Intent intent = new Intent();
            intent.setComponent(component);

            Bundle bundle = new Bundle();
            bundle.putString("msg_tp", "0200");//            报文类型
            bundle.putString("pay_tp", "0");//            支付方式
            bundle.putString("proc_tp", "00");//            交易类型
            bundle.putString("proc_cd", "700006");//            交易处理码
//            bundle.putString("order_no", "700008");//            订单号
            bundle.putString("appid", "com.lakala.pos");//            应用包名
//            bundle.putString("time_stamp", DateTimeUtil.getCurrentDate("yyyyMMddhhmmss"));//
            bundle.putString("time_stamp", System.currentTimeMillis() + "");//           交易时间戳
            bundle.putString("print_info", "重新打印");//            打印信息
//            bundle.putString("return_type", "1");//                    打单页面是否自动关闭
//            bundle.putString("reserve", "0");//                    扩展参数
//            bundle.putString("batchbillno", "0");//                    批次流水号
//            bundle.putString("pay_order_no", "0");//                    拉卡拉订单号
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            LogUtil.e( e.toString());
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

//        结算重打印

//        try {
//            LogUtil.e(" 结算重打印 ==============");
//            ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
//            Intent intent = new Intent();
//            intent.setComponent(component);
//            Bundle bundle = new Bundle();
//            bundle.putString("msg_tp", "0200");//            报文类型
//            bundle.putString("pay_tp", "0");//            支付方式
//            bundle.putString("proc_tp", "00");//            交易类型
//            bundle.putString("proc_cd", "700008");//            交易处理码
//            bundle.putString("appid", "com.lakala.pos");//            应用包名
//            bundle.putString("time_stamp", DateTimeUtil.getCurrentDate("yyyyMMddhhmmss"));//            交易时间戳
//            bundle.putString("return_type", "1");//                    打单页面是否自动关闭
//            bundle.putString("adddataword", "重新打印");//            附加数据
//            bundle.putString("reserve", "0");//                    扩展参数
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 1);
//        } catch (ActivityNotFoundException e) {
//            LogUtil.e( e.toString());
//        } catch (Exception e) {
//            LogUtil.e(e.toString());
//        }


//        扫码重打印
//
//        try {
//            LogUtil.e(" 扫码重打印 ==============");
//            ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
//            Intent intent = new Intent();
//            intent.setComponent(component);
//            Bundle bundle = new Bundle();
//            bundle.putString("msg_tp", "0200");//            报文类型
//            bundle.putString("pay_tp", "1");//            支付方式
//            bundle.putString("proc_tp", "00");//            交易类型
//            bundle.putString("proc_cd", "700007");//            交易处理码
//            bundle.putString("order_no", "73423254");//            订单号
//            bundle.putString("appid", "com.lakala.pos");//            应用包名
//            bundle.putString("time_stamp", DateTimeUtil.getCurrentDate("yyyyMMddhhmmss"));//            交易时间戳
//            bundle.putString("print_info", "重新打印");//            打印信息
//            bundle.putString("return_type", "1");//                    打单页面是否自动关闭
//            bundle.putString("reserve", "0");//                    扩展参数
//            bundle.putString("batchbillno", "0");//                    批次流水号
//            bundle.putString("pay_order_no", "0");//                    拉卡拉订单号
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 1);
//        } catch (ActivityNotFoundException e) {
//            LogUtil.e( e.toString());
//        } catch (Exception e) {
//            LogUtil.e(e.toString());
//        }



//        推送自定义打印
//        try {
//            LogUtil.e(" 推送自定义打印 ==============");
//                ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
//                Intent intent = new Intent();
//                intent.setComponent(component);
//                Bundle bundle = new Bundle();
//                bundle.putString("msg_tp", "0200");
//                bundle.putString("proc_cd", "700020");
//                bundle.putString("template_tp", "1");
//                bundle.putString("mer_name", "拉卡拉测试商户");
//                bundle.putString("appid", "com.lakala.pos");
//                bundle.putString("mer_no", "82112233123123");
//                bundle.putString("term_id", "12345678");
//                bundle.putString("operator", "01");
//                bundle.putString("org_no", "LAKALA");
//                bundle.putString("txn_type", "扫码支付（收款码）");
//                bundle.putString("pay_channel", "微信");
//                bundle.putString("batch_no", "000102");
//                bundle.putString("cseq_no", "000102");
//                bundle.putString("order_no", "2212312312312313123123213");
//                bundle.putString("auth_code", "000000");
//                bundle.putString("refernumber", "098765432112");
//                bundle.putString("trans_date", "2021/12/24 23:59:59");
//                bundle.putString("amt", "0.01");
//                bundle.putString("remarkinfo", "备注");
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 1);
//            } catch (ActivityNotFoundException e) {
//            LogUtil.e( e.toString());
//            } catch (Exception e) {
//            LogUtil.e( e.toString());
//            }





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("打印信息查询 返回信息：  requestCode=" + requestCode + "  resultCode =" + resultCode);
        String code = data.getExtras().getString("rescode");
        String reason = data.getExtras().getString("reason");
        LogUtil.i("信息：  code=" + code + "  reason =" + reason);
        switch (resultCode) {
            // 成功
            case Activity.RESULT_OK:
                if (requestCode == 1) {
                    if (data != null) {
                        LogUtil.i("data != null： ");
                        String msg_tp = data.getExtras().getString("msg_tp"); // 商户号

                        LogUtil.i("msg_tp:" + msg_tp);
                    }
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