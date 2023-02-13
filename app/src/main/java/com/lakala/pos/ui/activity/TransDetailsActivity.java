package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.lakala.pos.R;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.bean.TransDetailsBean;
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

    @BindView(R.id.previous)
    LinearLayout previous;

    @BindView(R.id.next)
    LinearLayout next;

    String orderNo = "";

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


    @OnClick({R.id.back_tv, R.id.reprint_tv, R.id.draw_bill_tv, R.id.previous, R.id.next,R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.reprint_tv:// 重打印


                break;
            case R.id.draw_bill_tv:// 开发票


                break;


            case R.id.previous:// 上一页


                break;


            case R.id.next:// 下一页


                break;
            case R.id.submit_tv: // 确定


                break;

        }
    }



    @Override
    public void queryOrdersDetailsResult(TransDetailsBean bean) {




    }

    @Override
    public void getDate(String date, int type) {

    }
}