package com.lakala.pos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
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
import com.lakala.pos.utils.ToastUtil;

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


    String revokePwd = "";

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
        undoPassword.setText(PreferencesUtils.getPreferenceString("revoke_pwd", "123456"));
        undoPassword.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        LogUtil.i("beforeTextChanged  ????????? " + s  );
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        LogUtil.i("onTextChanged ?????????" );
    }

    @Override
    public void afterTextChanged(Editable s) {
        LogUtil.i("afterTextChanged ?????????"  + s );
        revokePwd = s.toString();
//        if (revokePwd.length()>=6){
//            PreferencesUtils.setPreference("revoke_pwd", revokePwd);
//        }
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
        LogUtil.i("??????JSON?????????:" + "result=" + result );
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
               LogUtil.i("??????JSON?????????:" + "name=" + name );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        bossName.setText(builderBoss.toString());

    }


    @OnClick({R.id.back_tv, R.id.boss, R.id.accounting, R.id.cashier,R.id.submit_tv})
    public void onViewClicked(View view) {
        clearBuilder();
        Intent i = new Intent(this,SetPersonnelActivity.class);
        switch (view.getId()) {
            case R.id.back_tv:// ??????
                finish();
                break;
            case R.id.boss:// ??????
                i.putExtra("type",1);
                startActivity(i);
                break;
            case R.id.accounting:// ??????
                i.putExtra("type",2);
                startActivity(i);
                break;
            case R.id.cashier: // ?????????
                i.putExtra("type",3);
                startActivity(i);
                break;

            case R.id.submit_tv: // ??????
                if (!TextUtils.isEmpty(revokePwd)) {
                    if (revokePwd.length() >= 6) {
                        PreferencesUtils.setPreference("revoke_pwd", revokePwd);
                        ToastUtil.showToast("???????????????????????????");
                    }else {
                        ToastUtil.showToast("??????????????????????????????6?????????????????????");
                        LogUtil.i("rePwd : " + revokePwd);
                    }
                }
                break;
        }
    }

    private void clearBuilder(){
        builderAcc.setLength(0);
        builderCas.setLength(0);
        LogUtil.e("??????Builder" +builderAcc +"  "  +builderCas);
    }



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (b) {
            Log.i("app", ">>>>>>switched is on  ??????");
        } else {
            Log.i("app", ">>>>>>switched is off ??????");
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
        LogUtil.i("===========================================???????????????????????????========================================" + "[" + Thread.currentThread().getName() + "??????--");

        Cursor cursor = null;
        try {
            //???????????????,????????????????????????infolist
            cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
            //??????moveToFirst()?????????????????????????????????????????????
            LogUtil.i("???????????????,???????????? " + cursor.getColumnCount());
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
            LogUtil.i("???????????????,?????????????????????  ?????? =" + builderAcc.toString() +"           ?????? = "  +builderCas.toString());

            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("??????????????? ??????????????????" + e.getMessage());
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