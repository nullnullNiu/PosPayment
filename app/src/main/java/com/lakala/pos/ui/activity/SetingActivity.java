package com.lakala.pos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lakala.pos.R;
import com.lakala.pos.bean.BossInfoBean;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.ISetingView;
import com.lakala.pos.manager.ThreadPoolManager;
import com.lakala.pos.presente.SetPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.ui.MyApplication;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.PreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetingActivity extends MVPActivity<ISetingView, SetPresenter>
        implements ISetingView, CompoundButton.OnCheckedChangeListener , TextWatcher {

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

    @BindView(R.id.boss)
    TextView bossName;

    @BindView(R.id.accounting)
    TextView accounting;

    @BindView(R.id.cashier)
    TextView cashier;

    @BindView(R.id.et_undo_password)
    EditText undoPassword;

    StringBuilder  builderAcc = new StringBuilder();
    StringBuilder  builderCas = new StringBuilder();
    StringBuilder  builderBoss = new StringBuilder();

    private MyHandler handler = new MyHandler(this);


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
        undoPassword.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        LogUtil.i("beforeTextChanged  修改前 " + s  );
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        LogUtil.i("onTextChanged 修改中" );
    }

    @Override
    public void afterTextChanged(Editable s) {
        LogUtil.i("afterTextChanged 修改后"  + s );
       String pw = s.toString();
        if (pw.length()>=6){
            PreferencesUtils.setPreference("revoke_pwd", pw);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        onGetVlucoInfoDatabase();
        getBossInfo();
    }


    private void getBossInfo(){
        try {
            JSONObject object = new JSONObject();
            object.put("deviceCode", Global.DEVICE_ID);
            mPresenter.queryByDivice(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBossInfoResult(String result, int statues) {
    }

    @Override
    public void getBossInfoResult(String result) {
        LogUtil.i("解析JSON字符串:" + "result=" + result );
        builderBoss.setLength(0);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray == null || jsonArray.length() < 1){
                return;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
               String name  = jsonArray.getJSONObject(i).getString("bossName");
               builderBoss.append(name).append("   ");
               LogUtil.i("解析JSON字符串:" + "name=" + name );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        bossName.setText(builderBoss.toString());

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
            LogUtil.i("扫描数据库,数据数量 " + cursor.getColumnCount());
            while (cursor.moveToNext()) {
                int type = cursor.getInt(cursor.getColumnIndexOrThrow("Type"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
//                String pwd = cursor.getString(cursor.getColumnIndexOrThrow("Password"));
                if (type==2){
                    builderAcc.append(name).append("   ");
                } else if (type==3){
                    builderCas.append(name).append("   ");
                }
            }
            LogUtil.i("扫描数据库,获取数据库信息  会计 =" + builderAcc.toString() +"           收银 = "  +builderCas.toString());

            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }



    private class MyHandler extends Handler {
        WeakReference<SetingActivity> activityWeakReference;
        public MyHandler(SetingActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SetingActivity activity = activityWeakReference.get();
            if (activity != null) {
                if (msg.what == 1) {
                    accounting.setText(builderAcc.toString());
                    cashier.setText(builderCas.toString());
                }
            }
        }
    }

}