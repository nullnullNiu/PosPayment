package com.lakala.pos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lakala.pos.R;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.presente.TransPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.ui.fragment.TranQueryFragment;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

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
    }


    @OnClick({R.id.back_tv, R.id.img_summary,R.id.img_select_more})
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

                ToastUtil.showToast("更多");

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
    public void queryOrdersResult(TranQueryBean bean) {

    }
}