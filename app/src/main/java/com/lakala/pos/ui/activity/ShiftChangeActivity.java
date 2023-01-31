package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lakala.pos.R;
import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.interfaces.IShiftChangeView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.presente.ShiftChangePresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShiftChangeActivity extends MVPActivity<IShiftChangeView, ShiftChangePresenter> implements IShiftChangeView {



    @Override
    protected ShiftChangePresenter createPresenter() {
        return new ShiftChangePresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_change);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.back_tv,R.id.boss_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();



                break;

            case R.id.boss_tv:// 返回

                UserInfoBean  userInfoBean = new UserInfoBean();
                userInfoBean.setLoginName("13231917723");
                userInfoBean.setPassword("123456");
                String userJson = new Gson().toJson(userInfoBean);


                mPresenter.onLogin(userJson);

                break;

        }
    }


    @Override
    public void onLoginResult(LoginInfo info) {
        LogUtil.i(info.getData().getAccess_token()+"  =========");

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void fetchedData(String result) {

    }
}