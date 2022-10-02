package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.lakala.pos.R;
import com.lakala.pos.interfaces.ICustomerServiceView;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.CustomerServicePresenter;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class CustomerServiceActivity extends MVPActivity<ICustomerServiceView, CustomerServicePresenter> implements ICustomerServiceView {



    @Override
    protected CustomerServicePresenter createPresenter() {
        return new CustomerServicePresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.back_tv,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

        }
    }

    @Override
    public void versionAppUpdateView() {

    }


}