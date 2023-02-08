package com.lakala.pos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lakala.pos.R;
import com.lakala.pos.bean.BindDeviceInfoBean;
import com.lakala.pos.bean.EnterpriseInfoBean;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.interfaces.IDeviceBindView;
import com.lakala.pos.presente.DeviceBindingPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.PreferencesUtils;
import com.lakala.pos.utils.ToastUtil;
import com.lakala.pos.utils.ToolsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceBindingActivity extends MVPActivity<IDeviceBindView, DeviceBindingPresenter> implements IDeviceBindView , TextWatcher {

    @BindView(R.id.enterprise_name)
    EditText et_entName; // 企业名称
    @BindView(R.id.submit_modify)
    TextView submit_modify; // 确认修改
    @BindView(R.id.tv_tax_number)
    TextView tax_number; // 企业税号
    @BindView(R.id.et_industry)
    EditText et_industry; // 行业
    @BindView(R.id.et_address)
    EditText et_address; // 门店地址
    @BindView(R.id.et_drawer)
    EditText et_drawer; // 开票人
    @BindView(R.id.et_reviewed)
    EditText et_reviewed; // 审核人
    @BindView(R.id.et_admin)
    EditText et_admin; // 管理员
    @BindView(R.id.et_phone)
    EditText et_phone; // 手机号

    @BindView(R.id.back_tv)
    TextView back_tv; // 返回

    private String name = "";

    private String etPhone;
    private String etAdmin;

    private int typeCode = 0;
    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            LogUtil.i("在这里调用服务器的接口 获取数据" );
            onCompanySearch();
        }
    };


    @Override
    protected DeviceBindingPresenter createPresenter() {
        return new DeviceBindingPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_binding);
        ButterKnife.bind(this);

        if (getIntent().resolveActivity(getPackageManager()) != null){
            if (getIntent().hasExtra("typeCode")){
                typeCode = getIntent().getExtras().getInt("typeCode", 0);
                if (typeCode == -1){
                    back_tv.setVisibility(View.GONE);
                }
            }
        }

        et_entName.addTextChangedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (typeCode != -1){
            super.onBackPressed();
        }
    }

    private void onCompanySearch(){
        if (TextUtils.isEmpty(name)){
            ToastUtil.showToast("请输入企业名称");
            return;
        }
        String entName = "{\"companyName\":\"" + name + "\"}";
        mPresenter.companySearch(entName);
    }


    @Override
    public void companySearch(EnterpriseInfoBean bean) {
        LogUtil.i("根据公司名称返回抬头信息:  " + bean);
        tax_number.setText(bean.getData().get(0).getTaxId());
        et_address.setText(bean.getData().get(0).getLocation());
    }



    @OnClick({R.id.back_tv, R.id.submit_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回
                finish();
                break;

            case R.id.submit_modify:// 绑定、修改

                bindDevice();
                break;

        }
    }


    private void bindDevice() {
       String tax = tax_number.getText().toString();
       if (TextUtils.isEmpty(tax)){
           ToastUtil.showToast("企业税号不能为空");
           return;
       }

       String entName = et_entName.getText().toString();
       if (TextUtils.isEmpty(entName)){
           ToastUtil.showToast("企业名称不能为空");
           return;
       }

       String etAddress = et_address.getText().toString();
       if (TextUtils.isEmpty(etAddress)){
           ToastUtil.showToast("门店地址不能为空");
           return;
       }

       String etDrawer = et_drawer.getText().toString();
       if (TextUtils.isEmpty(etDrawer)){
           ToastUtil.showToast("开票人不能为空");
           return;
       }


       String etReviewed = et_reviewed.getText().toString();
       if (TextUtils.isEmpty(etReviewed)){
           ToastUtil.showToast("审核人不能为空");
           return;
       }

        etAdmin = et_admin.getText().toString();
       if (TextUtils.isEmpty(etAdmin)){
           ToastUtil.showToast("管理员不能为空");
           return;
       }

       etPhone = et_phone.getText().toString();
       if (TextUtils.isEmpty(etPhone)){
           ToastUtil.showToast("手机号不能为空");
           return;
       }

        if (!ToolsUtil.isPhoneNumber(etPhone)) {
            ToastUtil.showToast("电话号码格式不对");
            return ;
        }


        BindDeviceInfoBean bean = new BindDeviceInfoBean();
//        bean.setDeviceCode("123");
//        bean.setTaxNo("12312313123");
//        bean.setAddress("门店地址");
//
//        bean.setDrawer("开票人");
//        bean.setChecker("审核人");
//        bean.setSellerName("商户名称");
//        bean.setSellerNo("2111");
//        bean.setBossName("张三");
//        bean.setBossPhone("13231917723");

        bean.setDeviceCode("123");
        bean.setTaxNo(tax);
        bean.setAddress(etAddress);
        bean.setDrawer(etDrawer);
        bean.setChecker(etReviewed);
        bean.setSellerName(entName);
        bean.setSellerNo("2111");
        bean.setBossName(etAdmin);
        bean.setBossPhone(etPhone);

        String bindInfo = new Gson().toJson(bean);
        mPresenter.onBindDevice(bindInfo);
    }

    @Override
    public void bindResult(String result) {
        LogUtil.i("绑定设备信息:  " + result);

        if (TextUtils.isEmpty(etPhone)){
            ToastUtil.showToast("手机号不能为空");
            return;
        }

        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setLoginName(etPhone);
        userInfoBean.setPassword("123456");
        String user = new Gson().toJson(userInfoBean);
        mPresenter.onLogin(user);
    }

    @Override
    public void loginResult(String token) {
        LogUtil.i("tk" + token);
        PreferencesUtils.setPreference("admin", etAdmin);
        PreferencesUtils.setPreference("phone", etPhone);
        PreferencesUtils.setPreference("possword", "123456");
        PreferencesUtils.setPreference("access_token", token);
        ToastUtil.showToast("绑定成功。");
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
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
        if(delayRun!=null){
            //每次editText有变化的时候，则移除上次发出的延迟线程
            handler.removeCallbacks(delayRun);
            LogUtil.i("移除上次发出的延迟线程" );
        }
        name = s.toString();
        //延迟800ms，如果不再输入字符，则执行该线程的run方法
        handler.postDelayed(delayRun, 6000);
    }
}