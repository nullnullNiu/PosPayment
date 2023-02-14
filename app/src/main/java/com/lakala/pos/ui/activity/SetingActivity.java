package com.lakala.pos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.lakala.pos.R;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.interfaces.ISetingView;
import com.lakala.pos.manager.ThreadPoolManager;
import com.lakala.pos.presente.SetPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.ui.MyApplication;
import com.lakala.pos.utils.LogUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

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

    @BindView(R.id.accounting)
    TextView accounting;

    @BindView(R.id.cashier)
    TextView cashier;

    @BindView(R.id.et_undo_password)
    EditText undoPassword;

    StringBuilder  builderAcc = new StringBuilder();
    StringBuilder  builderCas = new StringBuilder();
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

    @Override
    public void onResume() {
        super.onResume();
        onGetVlucoInfoDatabase();
    }

    @OnClick({R.id.back_tv, R.id.boss, R.id.accounting, R.id.cashier})
    public void onViewClicked(View view) {
        clearBuilder();
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

    private void clearBuilder(){
        builderAcc.setLength(0);
        builderCas.setLength(0);
        LogUtil.e("清楚Builder" +builderAcc +"  "  +builderCas);
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

    public void onGetVlucoInfoDatabase() {
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                getUserInfo();
            }
        });
    }


    private void getUserInfo() {
        LogUtil.i("===========================================读取数据库里的数据========================================" + "[" + Thread.currentThread().getName() + "线程--");

        Cursor cursor = null;
        try {
            //扫描数据库,将数据库信息放入infolist
            cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
            //调用moveToFirst()将数据指针移动到第一行的位置。
            while (cursor.moveToNext()) {
                int type = cursor.getInt(cursor.getColumnIndexOrThrow("Type"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
//                String pwd = cursor.getString(cursor.getColumnIndexOrThrow("Password"));
                if (type==2){
                    builderAcc.append(name+"   ");
                } else if (type==3){
                    builderCas.append(name+"   ");
                }
            }
            LogUtil.i("扫描数据库,将数据库信息放入  会计 " + builderAcc.toString() +"   ////////////// 收银 "  +builderCas.toString());
            accounting.setText(builderAcc.toString());
            cashier.setText(builderCas.toString());

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("请求数据库 用户信息失败" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            LogUtil.i("finally      cursor.close()");
        }

    }

}