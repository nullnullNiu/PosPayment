package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lakala.pos.R;
import com.lakala.pos.bean.BindDeviceInfoBean;
import com.lakala.pos.interfaces.IDeviceBindView;
import com.lakala.pos.presente.DeviceBindingPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceBindingActivity extends MVPActivity<IDeviceBindView, DeviceBindingPresenter> implements IDeviceBindView {

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


    @Override
    protected DeviceBindingPresenter createPresenter() {
        return new DeviceBindingPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_binding);
        ButterKnife.bind(this);


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
//       String tax = tax_number.getText().toString();
//       if (TextUtils.isEmpty(tax)){
//           ToastUtil.showToast("企业税号不能为空");
//           return;
//       }
//
//       String entName = et_entName.getText().toString();
//       if (TextUtils.isEmpty(entName)){
//           ToastUtil.showToast("企业名称不能为空");
//           return;
//       }
//
//       String etAddress = et_address.getText().toString();
//       if (TextUtils.isEmpty(etAddress)){
//           ToastUtil.showToast("门店地址不能为空");
//           return;
//       }
//
//       String etDrawer = et_drawer.getText().toString();
//       if (TextUtils.isEmpty(etDrawer)){
//           ToastUtil.showToast("开票人不能为空");
//           return;
//       }
//
//
//       String etReviewed = et_reviewed.getText().toString();
//       if (TextUtils.isEmpty(etReviewed)){
//           ToastUtil.showToast("审核人不能为空");
//           return;
//       }



        BindDeviceInfoBean bean = new BindDeviceInfoBean();
        bean.setDeviceCode("123");
        bean.setTaxNo("12312313123");
        bean.setAddress("门店地址");

        bean.setDrawer("开票人");
        bean.setChecker("审核人");
        bean.setSellerName("商户名称");
        bean.setSellerNo("2111");
        bean.setBossName("张三");
        bean.setBossPhone("13231917723");

        String bindInfo = new Gson().toJson(bean);
        mPresenter.onBindDevice(bindInfo);
    }


    private void onCompanySearch(){
//        String s = "北京百望商";
//        String str = "{\"companyName\":\"" + s + "\"}";
//        mPresenter.companySearch(str);
    }

    @Override
    public void companySearch(String result) {
        LogUtil.i("根据公司名称获取抬头信息:  " + result);

    }

    @Override
    public void bindResult(String result) {
        LogUtil.i("绑定设备信息:  " + result);
    }
}