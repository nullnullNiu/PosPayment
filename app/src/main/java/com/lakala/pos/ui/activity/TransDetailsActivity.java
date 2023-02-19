package com.lakala.pos.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lakala.pos.R;
import com.lakala.pos.bean.TransDetailsBean;
import com.lakala.pos.dialog.BillingCodeDialog;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.presente.TransPresenter;
import com.lakala.pos.ui.MVPActivity;
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

    String payOrderNo = "";
    String batchbillNo = "";
    int payType = 0;


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


    private void queryOrderByOrderId(String id) {
        LogUtil.i("orderNo :" + orderNo);
        if (TextUtils.isEmpty(orderNo)) {
            ToastUtil.showToast("查询订单详情失败");
            return;
        }

        try {
            JSONObject object = new JSONObject();
            object.put("orderNo", orderNo);
            mPresenter.queryOrderByOrderId(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.back_tv, R.id.reprint_tv, R.id.draw_bill_tv, R.id.invoicing_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.reprint_tv:// 重打印
                Print();
                break;
            case R.id.draw_bill_tv:// 开发票

                if (TextUtils.isEmpty(billingCode)) {
                    ToastUtil.showToast("开票码为空！");
                    return;
                }
                billingCodeDialog = new BillingCodeDialog().newInstance(billingCode);
                billingCodeDialog.show(getFragmentManager(), "billingCodeDialog");
                break;

            case R.id.invoicing_code: // 开票码

                if (TextUtils.isEmpty(billingCode)) {
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
        if (bean.getData() == null) {
            ToastUtil.showToast("数据信息为空");
            return;
        }
        payOrderNo = bean.getData().getOrderNo();

        batchbillNo = bean.getData().getBatchNo();

        payType = bean.getData().getPayType();
        trans_data.setText(bean.getData().getTradeTime()); //交易时间

        trans_type.setText(bean.getData().getTradeType()); //交易类型

        batch_no.setText(bean.getData().getBatchNo()); //批次号

        voucher_no.setText(bean.getData().getVoucherNo());//凭证号

        order_amount.setText(bean.getData().getAmount() + "");//订单金额

        paid_in_amount.setText(bean.getData().getPayAmount() + "");//实付金额

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
        if (billingCodeDialog != null) {
            billingCodeDialog.dismissAllowingStateLoss();
        }
    }


    private void Print() {

        if (TextUtils.isEmpty(payOrderNo) || TextUtils.isEmpty(batchbillNo)) {
            ToastUtil.showToast("订单号 或 流水号查询不到，无法打印。");
            return;
        }

        if (payType == 0) {
            //    银行卡交易重打印
            try {
                LogUtil.e(" 打印 =============="+payOrderNo +"   " + batchbillNo);
                ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
                Intent intent = new Intent();
                intent.setComponent(component);

                Bundle bundle = new Bundle();
                bundle.putString("msg_tp", "0200");//            报文类型
                bundle.putString("pay_tp", "0");//            支付方式
                bundle.putString("proc_tp", "00");//            交易类型
                bundle.putString("proc_cd", "700006");//            交易处理码
                bundle.putString("appid", "com.lakala.pos");//            应用包名
                bundle.putString("time_stamp", System.currentTimeMillis() + "");//           交易时间戳
                bundle.putString("print_info", "银行卡支付重新打印");//            打印信息
                bundle.putString("batchbillno", batchbillNo);//                    批次流水号
                bundle.putString("pay_order_no", payOrderNo);//                    拉卡拉订单号
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            } catch (ActivityNotFoundException e) {
                LogUtil.e(e.toString());
            } catch (Exception e) {
                LogUtil.e(e.toString());
            }

            return;
        }


//        扫码重打印
        try {
            LogUtil.e(" 扫码重打印 ==============拉卡拉订单号 ： "+payOrderNo +"     批次流水号 ： " + batchbillNo);
            ComponentName component = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
            Intent intent = new Intent();
            intent.setComponent(component);
            Bundle bundle = new Bundle();
            bundle.putString("msg_tp", "0200");//            报文类型
            bundle.putString("pay_tp", "1");//            支付方式
            bundle.putString("proc_tp", "00");//            交易类型
            bundle.putString("proc_cd", "700007");//            交易处理码
//            bundle.putString("order_no", "20230219000000000000000000000001");//            订单号

            bundle.putString("appid", "com.lakala.pos");//            应用包名
            bundle.putString("time_stamp", System.currentTimeMillis() + "");//           交易时间戳
            bundle.putString("print_info", "扫码支付重新打印");//            打印信息

            bundle.putString("batchbillno", batchbillNo);//                    批次流水号
            bundle.putString("pay_order_no", payOrderNo);//                    拉卡拉订单号
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            LogUtil.e(e.toString());
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("打印信息查询 返回信息：  requestCode=" + requestCode + "  resultCode =" + resultCode);

        if (data == null){
            ToastUtil.showToast("暂无打印插件，请更新设备。");
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
                if (reason != null) {
                    ToastUtil.showToast(reason);
                }
                LogUtil.i("打印取消 :   响应吗：" + code + "       响应错误信息：" + message + "\n\r 响应数据：" + datas);
                break;
            // 失败
            case -2:
                if (reason != null) {
                    ToastUtil.showToast(" 交易失败：\n\n\r" + reason + message);
                }
                LogUtil.i("打印失败 :   响应吗：" + code + "       响应错误信息：" + message + "\n\r 响应数据：" + datas);
                break;
            default:
                break;
        }
    }

}