package com.lakala.pos.ui.activity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lakala.pos.R;
import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.interfaces.IShiftChangeView;
import com.lakala.pos.manager.ThreadPoolManager;
import com.lakala.pos.presente.ShiftChangePresenter;
import com.lakala.pos.sqlite.SaveDataToDatabase;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.ui.MyApplication;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import java.util.ArrayList;

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
        onGetVlucoInfoDatabase();
    }


    @OnClick({R.id.back_tv, R.id.boss_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();


                break;

            case R.id.boss_tv://

                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setLoginName("13231917723");
                userInfoBean.setPassword("123456");
                String userJson = new Gson().toJson(userInfoBean);


                mPresenter.onLogin(userJson);

                onLogin();
                break;

        }
    }


    @Override
    public void onLoginResult(LoginInfo info) {
        LogUtil.i(info.getData().getAccess_token() + "  =========");

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
        ArrayList<UserInfoBean> infolist = new ArrayList<>();
        Cursor cursor = null;
        try {
            //扫描数据库,将数据库信息放入infolist
            cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
            //调用moveToFirst()将数据指针移动到第一行的位置。
            while (cursor.moveToNext()) {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String pwd = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                UserInfoBean infoBean = new UserInfoBean(type, name, pwd);
                infolist.add(infoBean);//把数据库的每一行加入数组中
                LogUtil.i("扫描数据库,将数据库信息放入infolist " + infoBean.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("请求数据库 中压缩机故障停机信息失败" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            LogUtil.i("finally      cursor.close()");
        }

    }


    private AlertDialog dialog;
    // 登录窗口
    private void onLogin(){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View layout = inflater.inflate(R.layout.view_login_dialog, null);
        // 对话框
        dialog = new android.app.AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCancelable(false);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        EditText et_name=layout.findViewById(R.id.et_name);
        EditText et_pwd=layout.findViewById(R.id.et_password);
//        ll_pw=layout.findViewById(R.id.ll_pw);
//        rl_name=layout.findViewById(R.id.rl_name);
        TextView tv_cancel=layout.findViewById(R.id.tv_developer_cancel);
        TextView tv_login=layout.findViewById(R.id.tv_developer_login);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin(et_name.getText().toString(),et_pwd.getText().toString());
            }
        });
    }
    // 登录
    private void onLogin(String name,String pwd){
        if (TextUtils.isEmpty(name)){
            ToastUtil.showToast("请输入名字！");
        }else if (TextUtils.isEmpty(pwd)){
            ToastUtil.showToast("请输入密码！");
        }else {
            dialog.dismiss();
            onInspectUser(name,pwd);
        }
    }



    public void onInspectUser(String in_name ,String in_pwd) {
        LogUtil.i("输入的名字 = "+ in_name  +"          输入的密码 = "  + in_pwd );
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {


                        Cursor cursor = null;
                        try {
                            cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
                            //调用moveToFirst()将数据指针移动到第一行的位置。
                            String name = "";
                            String pwd = "";
                            if (cursor.moveToFirst()) {
                                do {
                                    //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
                                    name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                                    pwd = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                                    LogUtil.i(name  +"  "  + pwd);

                                    if (in_name.equals(name) && in_pwd.equals(pwd)){
                                        LogUtil.i(name  +"  "  + pwd +"   数据库中有");
                                    }else {

                                        LogUtil.i(name  +"  "  + pwd +"   数据库中没有");
                                        SaveDataToDatabase.getInstance().onSaveData(1,in_name,in_pwd);
                                    }


                                } while (cursor.moveToNext());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtil.e("查询数据库失败：" + e.getMessage());
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                            LogUtil.i("onMonitorWifi    finally      cursor.close()");
                        }



            }
        });
    }


    private boolean wifiName(String wifiName) {

        Cursor cur = null;
        try {
            cur = MyApplication.db.query("User_Info", null, "ssid=?",
                    new String[]{wifiName}, null, null, null);
            while (cur.moveToNext()) {
                String user = cur.getString(1);
                if (user.equals(wifiName)) {
                    LogUtil.i("wifi", wifiName + "数据库中有这个wifi   当前的等于数据库中的    当前的:" + wifiName + "  ==        数据库中的：" + user);
                    return false;
                } else
                    LogUtil.i("wifi", wifiName + "数据库中没有这个wifi  当前的不等于数据库中的    当前的:" + wifiName + "  !=        数据库中的：" + user);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("wifi", wifiName + "请求数据库失败 看有无当前wifi" + e.getMessage());
        } finally {
            if (cur != null) {
                cur.close();
            }
            LogUtil.i("wifi", wifiName + "finally      cur.close()");
        }


        return true;
    }


}