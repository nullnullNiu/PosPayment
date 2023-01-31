package com.lakala.pos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import com.lakala.pos.R;
import com.lakala.pos.interfaces.ISetingView;
import com.lakala.pos.presente.SetPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetingActivity extends MVPActivity<ISetingView, SetPresenter>
        implements ISetingView, CompoundButton.OnCheckedChangeListener {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "NonConstantResourceId"})
    @BindView(R.id.sw_collection_code)
    Switch swCollection;


    @SuppressLint({"NonConstantResourceId", "UseSwitchCompatOrMaterialCode"})
    @BindView(R.id.sw_scan_code)
    Switch swScan;

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "NonConstantResourceId"})
    @BindView(R.id.sw_cash_code)
    Switch swCash;

    boolean collection, scan, cash;


    @BindView(R.id.et_undo_password)
    EditText undoPassword;

    @Override
    protected SetPresenter createPresenter() {
        return new SetPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        ButterKnife.bind(this);
        swCollection.setOnCheckedChangeListener(this);
        swScan.setOnCheckedChangeListener(this);
        swCash.setOnCheckedChangeListener(this);

    }


    @OnClick({R.id.back_tv, R.id.boss, R.id.accounting, R.id.cashier})
    public void onViewClicked(View view) {
        Intent i = new Intent(this,SetPersonnelActivity.class);
        switch (view.getId()) {
            case R.id.back_tv:// 返回
                finish();
                break;
            case R.id.boss:// 老板
                i.putExtra("type",1);
                startActivity(i);
                break;
            case R.id.accounting:// 会计
                i.putExtra("type",2);
                startActivity(i);
                break;
            case R.id.cashier: // 收银员
                i.putExtra("type",3);
                startActivity(i);
                break;
        }
    }


    @Override
    public void versionAppUpdateView() {

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (b) {
            Log.i("app", ">>>>>>switched is on  打开");
        } else {
            Log.i("app", ">>>>>>switched is off 关闭");
        }

        switch (compoundButton.getId()) {
            case R.id.sw_collection_code:
                collection = b;
                break;

            case R.id.sw_scan_code:
                scan = b;
                break;

            case R.id.sw_cash_code:
                cash = b;
                break;
        }

        LogUtil.i("switched     "  +  collection + "  "  + scan +"    " + cash);
    }
}