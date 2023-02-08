package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lakala.pos.R;
import com.lakala.pos.bean.QueryOrderBean;
import com.lakala.pos.bean.RevokeInfoBean;
import com.lakala.pos.common.DeviceInfo;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.interfaces.IRevokeView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.presente.RevokePresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RevokeActivity extends MVPActivity<IRevokeView, RevokePresenter> implements IRevokeView  {

//    @BindView(R.id.rb_scan)
//    RadioButton rbScan;
//
    @BindView(R.id.et_transaction_no)
    EditText transNo;

    private int transType = 0;

    @Override
    protected RevokePresenter createPresenter() {
        return new RevokePresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revoke);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.back_tv, R.id.rb_scan, R.id.rb_card, R.id.submit_tv , R.id.img_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.rb_scan:
                transType = 1;
                LogUtil.i("扫一扫 transType:  " + transType);
                break;


            case R.id.rb_card:

                transType = 2;
                LogUtil.i("银行卡 transType:  " + transType);
                break;
            case R.id.submit_tv:
                if (transType == 0){
                    ToastUtil.showToast("请选择交易方式");
                    return;
                }
                if (TextUtils.isEmpty(transNo.getText().toString())){
                    ToastUtil.showToast("请输入原交易凭证号");
                    return;
                }

                queryOrder(transNo.getText().toString());
                break;

            case R.id.img_scan:
                ToastUtil.showToast("待开发");
                break;

        }
    }

    // 撤销前先请求
    private void queryOrder(String out_order_no){
        try {
            JSONObject object = new JSONObject();
            object.put("out_order_no","20230206000000000000000000000002");
            object.put("merchant_no", "822290070111135");

//            object.put("out_order_no",out_order_no);
//          object.put("merchant_no", Global.MERCHANT_NO);

            mPresenter.queryOrder(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void queryOrderResult(QueryOrderBean bean) {

        String Merchant_no = bean.getData().getMerchant_no();
        String term_no = bean.getData().getTerm_no();
        String origin_out_trade_no = bean.getData().getOut_order_no();
        String origin_trade_no = bean.getData().getPay_order_no();
        String requestIp = Global.IP;

        LogUtil.i("查询订单信息： 结算商户号：" + Merchant_no +"    终端号:" + term_no +"   商户订单号: " + origin_out_trade_no +"   支付流水号:" +origin_trade_no +"   ip: " +requestIp);
        transRevoked(Merchant_no,term_no,origin_out_trade_no,origin_trade_no,requestIp);
    }


    // 撤销
    private void transRevoked( String Merchant_no,String term_no,String origin_out_trade_no,String origin_trade_no ,String requestIp){

        if (TextUtils.isEmpty(Merchant_no)){
            ToastUtil.showToast("查询不到当前订单！");
            return;
        }
        if (TextUtils.isEmpty(term_no)){
            ToastUtil.showToast("查询不到当前订单！");
            return;
        }
        if (TextUtils.isEmpty(origin_out_trade_no)){
            ToastUtil.showToast("查询不到当前订单！");
            return;
        }
        if (TextUtils.isEmpty(origin_trade_no)){
            ToastUtil.showToast("查询不到当前订单！");
            return;
        }
        if (TextUtils.isEmpty(requestIp)){
            ToastUtil.showToast("查询不到当前订单！");
            return;
        }

        RevokeInfoBean bean = new RevokeInfoBean();
        bean.setMerchant_no(Merchant_no);//结算商户号
        bean.setTerm_no(term_no);//终端号
        bean.setOrigin_out_trade_no(origin_out_trade_no);//商户订单号
        bean.setOrigin_trade_no(origin_trade_no);//支付流水号
        bean.setRequestIp(requestIp);//ip，必送


//        RevokeInfoBean bean = new RevokeInfoBean();
//        bean.setMerchant_no("822290070111135");//结算商户号
//        bean.setTerm_no("29034705");//终端号
//        bean.setOrigin_out_trade_no("20230206000000000000000000000002");//商户订单号
//        bean.setOrigin_trade_no("23020611012001101011000885840");//支付流水号
//        bean.setRequestIp("101.23.108.254");//ip，必送

        String revokedInfo = new Gson().toJson(bean);
        mPresenter.transRevoked(revokedInfo);
    }

    @Override
    public void transRevokedResult(String result) {
       hideLoading();
        ToastUtil.showToast("撤销完成");

        finish();
    }


    @Override
    public void showLoading() {
        LogUtil.e("正在撤销订单");
        showProgress("正在撤销订单，请稍后。。。");

    }

    @Override
    public void hideLoading() {
        LogUtil.e("取消进度");
        removeProgress();
    }

    @Override
    public void fetchedData(String result) {

    }
}