package com.lakala.pos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lakala.pos.R;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.bean.TransDetailsBean;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.presente.MainActivityPresenter;
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


public class TranQueryActivity extends MVPActivity<ITransView, TransPresenter> implements ITransView {

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


    String sTime = "";
    String eTime = "";

    private String[] mTitle = {"全部", "未开票", "待填报", "已开票"};
    private int[] mState = {0, 1, 2, 3};

    @Override
    protected TransPresenter createPresenter() {
        return new TransPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_query);
        ButterKnife.bind(this);
        mViewPager.setOffscreenPageLimit(0);
        initTab();


        getCountByDeviceId();
    }

    private void getCountByDeviceId(){
        try {
            JSONObject object = new JSONObject();
            object.put("deviceCode", Global.DEVICE_ID);
            mPresenter.countByDeviceId(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.back_tv, R.id.img_summary,R.id.img_select_more,R.id.start_time,R.id.end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.img_summary:// 汇总

                startActivity(new Intent(this,SummaryActivity.class));

                break;
            case R.id.img_select:// 搜索
                if (TextUtils.isEmpty(tranNo.getText().toString())){
                    ToastUtil.showToast("请输入交易凭证号");
                    return;
                }

                ToastUtil.showToast("搜索");

                break;

            case R.id.img_select_more:// 更多
                if (time_layout.getVisibility() == View.GONE){
                    time_layout.setVisibility(View.VISIBLE);
                }else {
                    time_layout.setVisibility(View.GONE);
                }
                break;
            case R.id.start_time:
                mPresenter.onDateSelection(this,1);
                break;

            case R.id.end_time:
                mPresenter.onDateSelection(this,2);
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
                TranQueryFragment fragment = TranQueryFragment.getInstance(mState[position % mTitle.length]);
                LogUtil.e("切换ViewPager  getItem  " + mState[position % mTitle.length]);
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
}