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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lakala.pos.R;
import com.lakala.pos.adapter.BossPersonnelAdapter;
import com.lakala.pos.adapter.PersonnelAdapter;
import com.lakala.pos.adapter.SummaryAdapter;
import com.lakala.pos.bean.BossInfoBean;
import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.IShiftChangeView;
import com.lakala.pos.manager.ThreadPoolManager;
import com.lakala.pos.presente.ShiftChangePresenter;
import com.lakala.pos.sqlite.SaveDataToDatabase;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.ui.MyApplication;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.PreferencesUtils;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShiftChangeActivity extends MVPActivity<IShiftChangeView, ShiftChangePresenter> implements IShiftChangeView ,BossPersonnelAdapter.OnItemClickListion{


    BossPersonnelAdapter bossPersonnelAdapter;
    PersonnelAdapter adapter;
    @BindView(R.id.recycler_boss)
    RecyclerView recycler_boss;

    @BindView(R.id.recycler_acc)
    RecyclerView recycler_acc; //会计

    @BindView(R.id.recycler_cas)
    RecyclerView recycler_cas; // 收银



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
        getBossInfo();
    }


    @OnClick({R.id.back_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();


                break;


        }
    }


    // 获取老板
    private void getBossInfo() {
        try {
            JSONObject object = new JSONObject();
            object.put("deviceCode", Global.DEVICE_ID);
            mPresenter.queryByDivice(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBossInfoResult(BossInfoBean bean) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_boss.setLayoutManager(gridLayoutManager);
        bossPersonnelAdapter = new BossPersonnelAdapter(this, bean.getData());
        recycler_boss.setAdapter(bossPersonnelAdapter);
    }

    @Override
    public void onItemClick(String name, String pwd) {
        LogUtil.i("换班为 ： "+name );
        onChangePerson(true,name,pwd);
    }


    // 获取员工
    public void onGetVlucoInfoDatabase() {
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                getUserInfo();
            }
        });
    }

    //    ArrayList<UserInfoBean> infolist;
    private void getUserInfo() {
        LogUtil.i("===========================================读取数据库里的数据========================================" + "[" + Thread.currentThread().getName() + "线程--");
        ArrayList<UserInfoBean> accInfolist = new ArrayList<>();
        ArrayList<UserInfoBean> casInfolist =  new ArrayList<>();
        int type = 0;
        Cursor cursor = null;
        try {
            //扫描数据库,将数据库信息放入infolist
            cursor = MyApplication.db.query("User_Info", null, null, null, null, null, null);
            //调用moveToFirst()将数据指针移动到第一行的位置。
            while (cursor.moveToNext()) {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                type = cursor.getInt(cursor.getColumnIndexOrThrow("Type"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String pwd = cursor.getString(cursor.getColumnIndexOrThrow("Password"));

                // 2会计  3收银
                if (type == 2) {
                    UserInfoBean infoBean = new UserInfoBean(type, name, pwd);
                    accInfolist.add(infoBean);//把数据库的每一行加入数组中
                    LogUtil.i("  会计  扫描数据库,将数据库信息放入infolist " + infoBean.toString());
                    LogUtil.i("  会计  扫描数据库,将数据库信息放入infolist " + accInfolist.toString());
                }
                if (type == 3) {
                    UserInfoBean infoBean = new UserInfoBean(type, name, pwd);
                    casInfolist.add(infoBean);//把数据库的每一行加入数组中
                    LogUtil.i(" 收银   扫描数据库,将数据库信息放入infolist " + infoBean.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("请求数据库 中压缩机故障停机信息失败" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            LogUtil.i("finally      cursor.close()");

                setAccAdapter(accInfolist);
                setCasAdapter(casInfolist);

        }
    }


    private void setAccAdapter( ArrayList<UserInfoBean> accInfolist) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_acc.setLayoutManager(gridLayoutManager);

            adapter = new PersonnelAdapter(this, accInfolist);
            recycler_acc.setAdapter(adapter);

    }

    private void setCasAdapter(ArrayList<UserInfoBean> casInfolist ) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycler_cas.setLayoutManager(gridLayoutManager);

            adapter = new PersonnelAdapter(this, casInfolist);
            recycler_cas.setAdapter(adapter);

    }


    private AlertDialog dialog;

    // 登录窗口
    private void onChangePerson(boolean isBoos,String name ,String pwd) {
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

        TextView et_name = layout.findViewById(R.id.et_name);
        et_name.setText(name);
        EditText et_pwd = layout.findViewById(R.id.et_password);
//        ll_pw=layout.findViewById(R.id.ll_pw);
//        rl_name=layout.findViewById(R.id.rl_name);
        TextView tv_cancel = layout.findViewById(R.id.tv_developer_cancel);
        TextView tv_login = layout.findViewById(R.id.tv_developer_login);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChange(isBoos, name, pwd, et_pwd.getText().toString());
            }
        });
    }

    // 确认换班
    private void onChange(boolean isBoos,String name,String customarPwd,String pwd) {

       if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast("请输入密码！");
        } else {
           if (customarPwd.equals(pwd)){
               PreferencesUtils.setPreferenceBoolean("role_boss", isBoos);
               PreferencesUtils.setPreference("admin", name); // 收款人 换班人
               dialog.dismiss();
           } else {
               ToastUtil.showToast("密码不正确");
           }

       }
    }


    public void onInspectUser(int type, String in_name, String in_pwd) {
        LogUtil.i("输入的名字 = " + in_name + "          输入的密码 = " + in_pwd);
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

                            LogUtil.i(name + "  " + pwd);

                            if (in_name.equals(name) && in_pwd.equals(pwd)) {
                                LogUtil.i(name + "  " + pwd + "   数据库中有");
                            } else {

                                LogUtil.i(name + "  " + pwd + "   数据库中没有");
                                SaveDataToDatabase.getInstance().onSaveData(1, in_name, in_pwd);
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



}