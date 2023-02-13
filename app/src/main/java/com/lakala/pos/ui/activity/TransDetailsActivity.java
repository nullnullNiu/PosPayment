package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lakala.pos.R;
import com.lakala.pos.bean.TranQueryBean;
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
}