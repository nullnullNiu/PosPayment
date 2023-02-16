package com.lakala.pos.ui.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.lakala.pos.R;
import com.lakala.pos.bean.BossInfoBean;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.ISetingView;
import com.lakala.pos.manager.ThreadPoolManager;
import com.lakala.pos.presente.SetPresenter;
import com.lakala.pos.sqlite.SaveDataToDatabase;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.ui.MyApplication;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPersonnelActivity extends MVPActivity<ISetingView, SetPresenter> implements ISetingView, View.OnClickListener {

    @BindView(R.id.type_name)
    TextView typeName;

    @BindView(R.id.iv_look)
    ImageView ivLook;

    @BindView(R.id.add_connect)
    LinearLayout addView;

    ImageView inIvLook, inIvReduce;

    @BindView(R.id.et_account)
    EditText account;

    @BindView(R.id.et_pwd)
    EditText pwd;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.phone_layout)
    LinearLayout phoneLayout;

    EditText inAccount, inPwd ,inPhone;
    LinearLayout inPhoneLayout;

    boolean addV = false;

    int type = 0; // 1老板 2会计  3收银
    private boolean isLook = false;
    private boolean inIsLook = false;

    @Override
    protected SetPresenter createPresenter() {
        return new SetPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_personnel);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        initTypeName(type);

    }


    private void initTypeName(int type) {
        LogUtil.i("type=   " + type);
        switch (type) {
            case 1:
                typeName.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                phoneLayout.setVisibility(View.GONE);
                typeName.setVisibility(View.VISIBLE);
                typeName.setText("会计");
                break;
            case 3:
                phoneLayout.setVisibility(View.GONE);
                typeName.setVisibility(View.VISIBLE);
                typeName.setText("收银");
                break;
            default:
                phoneLayout.setVisibility(View.GONE);
                typeName.setVisibility(View.GONE);
                break;
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @OnClick({R.id.back_tv, R.id.iv_add, R.id.iv_look, R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回
                finish();
                break;
            case R.id.iv_add:// 添加
                addAgentLayout();
                break;

            case R.id.iv_look:// 看
                if (isLook) {
                    isLook = false;
                    ivLook.setImageResource(R.mipmap.no_look);
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    isLook = true;
                    ivLook.setImageResource(R.mipmap.look);
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.submit_tv:

                if (TextUtils.isEmpty(account.getText().toString())) {
                    ToastUtil.showToast("账号名称不能为空");
                    return;
                }

                if (TextUtils.isEmpty(pwd.getText().toString())) {
                    ToastUtil.showToast("密码不能为空");
                    return;
                }
//                onInspectUser(type, account.getText().toString(), pwd.getText().toString());

                String phon = "";
                if (type == 1){
                    phon = etPhone.getText().toString();
                    if (TextUtils.isEmpty(phon)) {
                        ToastUtil.showToast("手机号码不能为空");
                        return;
                    }
                }

                if (!addV) {
                    setUserInfo(type, account.getText().toString(),phon, pwd.getText().toString(),1);
                    return;
                }
                setUserInfo(type, account.getText().toString(),phon, pwd.getText().toString(),0);

                if (TextUtils.isEmpty(inAccount.getText().toString())) {
                    ToastUtil.showToast("账号名称不能为空");
                    return;
                }

                if (TextUtils.isEmpty(inPwd.getText().toString())) {
                    ToastUtil.showToast("密码不能为空");
                    return;
                }

                String in_phon = "";
                if (type == 1){
                    in_phon = inPhone.getText().toString();
                    if (TextUtils.isEmpty(in_phon)) {
                        ToastUtil.showToast("手机号码不能为空");
                        return;
                    }
                }
                setUserInfo(type, account.getText().toString(),in_phon, pwd.getText().toString(),1);

                break;
        }
    }


//    // 执行完退出
//    private void setUserInfo(int type,String name,String phone,String pwd){
//        if (type != 1){
//            onInspectUser(type,name,pwd);
//        } else {
//            try {
//                JSONObject object = new JSONObject();
//                object.put("bossName",name);
//                object.put("bossPhone",phone);
//                object.put("password",pwd);
//                object.put("deviceCode", Global.DEVICE_ID);
//                mPresenter.addBossInfo(object.toString(),1);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }


    private void setUserInfo(int type,String name,String phone,String pwd,int statues){
        if (type != 1){
            onInspectUser(type,name,pwd,statues);
        } else {
            try {
                JSONObject object = new JSONObject();
                object.put("bossName",name);
                object.put("bossPhone",phone);
                object.put("password",pwd);
                object.put("deviceCode", Global.DEVICE_ID);
//                object.put("deviceCode", "D9587314");
                mPresenter.addBossInfo(object.toString(),statues);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    // 添加人员
    private void addAgentLayout() {
        addV = true;
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.include_personnel_layout, null);
        addView.removeAllViews();
        addView.addView(view, layoutParams);
        initaddView(view);
    }

    private void initaddView(View view) {
        if (type == 1){
            inPhoneLayout = view.findViewById(R.id.in_phone_layout);
            inPhoneLayout.setVisibility(View.VISIBLE);
        }

        inIvReduce = view.findViewById(R.id.in_iv_reduce);
        inIvLook = view.findViewById(R.id.in_iv_look);

        inAccount = view.findViewById(R.id.et_account_name);
        inPhone = view.findViewById(R.id.in_et_phone);
        inPwd = view.findViewById(R.id.et_pwd);
        inIvReduce.setOnClickListener(this);
        inIvLook.setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.in_iv_reduce:
                addV = false;
                addView.removeAllViews();
                break;

            case R.id.in_iv_look:
                if (inIsLook) {
                    inIsLook = false;
                    inIvLook.setImageResource(R.mipmap.no_look);
                    inPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    inIsLook = true;
                    inIvLook.setImageResource(R.mipmap.look);
                    inPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;

        }
    }


//    public void onInspectUser(int in_type, String in_name, String in_pwd) {
//
//        LogUtil.i("输入的名字 = " + in_name + "          输入的密码 = " + in_pwd +"     类型 = " + in_type);
//        ThreadPoolManager.getInstance().execute(new Runnable() {
//            int a = in_type;
//            @Override
//            public void run() {
//                boolean isSave = true;
//                Cursor cursor = null;
//                try {
//                    cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
//                    //调用moveToFirst()将数据指针移动到第一行的位置。
//                    String name = "";
//                    int type = 0;
//
//                    if (cursor.moveToFirst()) { //将光标移到第一行。如果光标为空，此方法将返回false。
//                        do {
//                            //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
//                            name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
//                            type = cursor.getInt(cursor.getColumnIndexOrThrow("Type"));
//
//                            if (in_name.equals(name) && in_type == type) {
//                                isSave = false;
//                            }
//
//                        } while (cursor.moveToNext());
//                    } else {
//                        LogUtil.i(name + "  " + type + "   数据库中没有数据 ");
//                    }
//
//                    if (isSave){
//                        LogUtil.i(in_type+"      "+in_name + "  " + in_pwd + "   数据库中没有,需要保存到数据库");
//                        SaveDataToDatabase.getInstance().onSaveData(in_type, in_name, in_pwd);
//                    } else {
//                        LogUtil.i(in_type+"      "+in_name + "  " + in_pwd + "   数据库中有，无需保存。");
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    LogUtil.e("查询数据库失败：" + e.getMessage());
//                } finally {
//                    if (cursor != null) {
//                        cursor.close();
//                    }
//                    LogUtil.i("finally      cursor.close()");
//
//                    a =1;
//                    finish();
//                }
//            }
//        });
//
//    }

    public void onInspectUser(int in_type, String in_name, String in_pwd,int statues) {

        LogUtil.i("输入的名字 = " + in_name + "          输入的密码 = " + in_pwd +"     类型 = " + in_type);
        ThreadPoolManager.getInstance().execute(new Runnable() {
            int start = statues;
            @Override
            public void run() {
                boolean isSave = true;
                Cursor cursor = null;
                try {
                    cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
                    //调用moveToFirst()将数据指针移动到第一行的位置。
                    String name = "";
                    int type = 0;

                    if (cursor.moveToFirst()) { //将光标移到第一行。如果光标为空，此方法将返回false。
                        do {
                            //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
                            name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                            type = cursor.getInt(cursor.getColumnIndexOrThrow("Type"));

                            if (in_name.equals(name) && in_type == type) {
                                isSave = false;
                            }

                        } while (cursor.moveToNext());
                    } else {
                        LogUtil.i(name + "  " + type + "   数据库中没有数据 ");
                    }

                    if (isSave){
                        LogUtil.i(in_type+"      "+in_name + "  " + in_pwd + "   数据库中没有,需要保存到数据库");
                        SaveDataToDatabase.getInstance().onSaveData(in_type, in_name, in_pwd);
                    } else {
                        LogUtil.i(in_type+"      "+in_name + "  " + in_pwd + "   数据库中有，无需保存。");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("查询数据库失败：" + e.getMessage());
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    LogUtil.i("finally      cursor.close()");
                    if (start == 1){
                        finish();
                    }
                }
            }
        });

    }

    @Override
    public void addBossInfoResult(String result,int statues) {
        LogUtil.i("result : " + result);

        if (statues == 1){
            finish();
            return;
        }


    }

    @Override
    public void getBossInfoResult(String result) {

    }


//    public void onInspectUser(int type, String in_name, String in_pwd) {
//        if (type == 1) {
//            return;
//        }
//        LogUtil.i("输入的名字 = " + in_name + "          输入的密码 = " + in_pwd);
//        ThreadPoolManager.getInstance().execute(new Runnable() {
//            @Override
//            public void run() {
//
//                Cursor cursor = null;
//                try {
//                    cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
//                    //调用moveToFirst()将数据指针移动到第一行的位置。
//                    String name = "";
//                    String pwd = "";
//
//                    if (cursor.moveToFirst()) { //将光标移到第一行。如果光标为空，此方法将返回false。
//                        do {
//                            //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
//                            name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
//                            pwd = cursor.getString(cursor.getColumnIndexOrThrow("Password"));
//
//                            LogUtil.i(name + "  " + pwd);
//                            if (in_name.equals(name) && in_pwd.equals(pwd)) {
//                                LogUtil.i(name + "  " + pwd + "   数据库中有");
//                            } else {
//                                LogUtil.i(name + "  " + pwd + "   数据库中没有");
//                                SaveDataToDatabase.getInstance().onSaveData(type, in_name, in_pwd);
//                            }
//
//                        } while (cursor.moveToNext());
//                    }else{
//                        LogUtil.i(name + "  " + pwd + "   数据库中没有2");
//                        SaveDataToDatabase.getInstance().onSaveData(type, in_name, in_pwd);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    LogUtil.e("查询数据库失败：" + e.getMessage());
//                } finally {
//                    if (cursor != null) {
//                        cursor.close();
//                    }
//                    LogUtil.i("finally      cursor.close()");
//                }
//
//            }
//        });
//    }
}