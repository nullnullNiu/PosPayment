package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lakala.pos.R;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RevokeActivity extends MVPActivity<IHomeView, MainActivityPresenter> implements IHomeView  {

//    @BindView(R.id.rb_scan)
//    RadioButton rbScan;
//
    @BindView(R.id.et_transaction_no)
    EditText transNo;

    private int transType = 0;

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
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


                break;

            case R.id.img_scan:
                ToastUtil.showToast("待开发");
                break;

        }
    }

    @Override
    public void versionAppUpdateView() {

    }


}