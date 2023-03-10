package com.lakala.pos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lakala.pos.R;
import com.lakala.pos.adapter.SpinnerNameAdapter;
import com.lakala.pos.bean.TransDetailsBean;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.presente.TransPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.ui.fragment.TranQueryFragment;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TranQueryActivity extends MVPActivity<ITransView, TransPresenter> implements ITransView, TextWatcher {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.et_transaction_no)
    EditText tranNo;

    @BindView(R.id.tran_numb)
    TextView tran_numb;

    @BindView(R.id.time_layout)
    ConstraintLayout time_layout;

    @BindView(R.id.start_time)
    TextView startTime;

    @BindView(R.id.end_time)
    TextView endTime;

    public String voucherNo = "";
    public String sTime = "";
    public String eTime = "";
    public boolean screen = false;
    private String[] mTitle = {"全部", "未开票", "待填报", "已开票","已退单"};
    private int[] mState = {-1,0, 1, 2, 3};

    @Override
    protected TransPresenter createPresenter() {
        return new TransPresenter();
    }
    public static TranQueryActivity instance;

    public static TranQueryActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_query);
        ButterKnife.bind(this);
        instance = this;
        mViewPager.setOffscreenPageLimit(0);
        initTab();

        tranNo.addTextChangedListener(this);
        getCountByDeviceId();
    }

    private void getCountByDeviceId() {
        try {
            JSONObject object = new JSONObject();
            object.put("deviceCode", Global.DEVICE_ID);
            mPresenter.countByDeviceId(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.back_tv, R.id.img_summary,R.id.img_select, R.id.img_select_more, R.id.start_time, R.id.end_time, R.id.time_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.img_summary:// 汇总

                startActivity(new Intent(this, SummaryActivity.class));

                break;
            case R.id.img_select:// 搜索
                if (TextUtils.isEmpty(voucherNo)) {
                    ToastUtil.showToast("请输入交易凭证号");
                    return;
                }
//                TranQueryFragment.getInstance(-1);
//                ToastUtil.showToast("搜索");
                LogUtil.i("凭证号搜索： " + voucherNo);
                onScreenClickListion.onScreenClick(sTime,eTime,true);
                break;

            case R.id.img_select_more:// 时间筛选
                if (time_layout.getVisibility() == View.GONE) {
                    time_layout.setVisibility(View.VISIBLE);
                } else {
                    time_layout.setVisibility(View.GONE);
                    startTime.setText("");
                    endTime.setText("");
                    screen = false;
                    sTime = "";
                    eTime = "";
                    onScreenClickListion.onScreenClick(sTime,eTime,false);
                }
                break;
            case R.id.start_time:
                mPresenter.onDateSelection(this, 1);
                break;

            case R.id.end_time:
                mPresenter.onDateSelection(this, 2);
                break;

            case R.id.time_tv:
                if (TextUtils.isEmpty(sTime) || TextUtils.isEmpty(startTime.getText().toString())) {
                    ToastUtil.showToast("请选择开始日期");
                    return;
                }

                if (TextUtils.isEmpty(eTime) || TextUtils.isEmpty(endTime.getText().toString())) {
                    ToastUtil.showToast("请选择结束日期");
                    return;
                }
                screen = true;
                onScreenClickListion.onScreenClick(sTime,eTime,true);
                break;
        }
    }

    @Override
    public void getDate(String date, int type) {
        switch (type) {
            case 1:
                startTime.setText(date);
                sTime = date;
                break;
            case 2:
                endTime.setText(date);
                eTime = date;
                break;
        }
    }


    private void initTab() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                //创建Fragment并返回
                TranQueryFragment fragment  = TranQueryFragment.getInstance(mState[position % mTitle.length]);
                LogUtil.e("切换ViewPager  getItem  " + mState[position % mTitle.length] +"   screen: " +screen);
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });

        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void countByDeviceIdResult(String result) {
        LogUtil.i("获取订单笔数： " + result);
        tran_numb.setText("共" + result + "笔交易");
    }

    @Override
    public void queryOrdersDetailsResult(TransDetailsBean bean) {

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
        voucherNo = s.toString();
    }


    public interface OnScreenClickListion {
        void onScreenClick(String start,String end,boolean screen);
    }
    private OnScreenClickListion onScreenClickListion;

    public void setOnScreenClickListion (OnScreenClickListion onScreenClickListion) {
        this.onScreenClickListion = onScreenClickListion;
    }
}