package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.lakala.pos.R;
import com.lakala.pos.interfaces.IHelpView;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.HelpPresenter;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class HelpActivity extends MVPActivity<IHelpView, HelpPresenter> implements IHelpView {



    @Override
    protected HelpPresenter createPresenter() {
        return new HelpPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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