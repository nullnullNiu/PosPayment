package com.lakala.pos.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.lakala.pos.R;
import com.lakala.pos.adapter.ViewPagerAdapter;
import com.lakala.pos.interfaces.IBankCardView;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.BankCardPresenter;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BankCardActivity extends MVPActivity<IBankCardView, BankCardPresenter> implements IBankCardView {

    @BindView(R.id.tv_money_lalue)
    TextView money;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.dot_1)
    RadioButton dot1;

    @BindView(R.id.dot_2)
    RadioButton dot2;

    @BindView(R.id.dot_3)
    RadioButton dot3;
    List<View> pages =  new ArrayList<>();
    View first,second,third;
    LayoutInflater layoutInflater;
    ViewPagerAdapter viewPagerAdapter;

    double amount = 0;
    @Override
    protected BankCardPresenter createPresenter() {
        return new BankCardPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        ButterKnife.bind(this);

        amount = getIntent().getDoubleExtra("amount",0);
        money.setText("￥" + amount);

        showProposalView(this);

        initView();
    }


    private void initView(){
        setDot(true,false,false);
        //获取布局填充
        layoutInflater  = getLayoutInflater();
        first = layoutInflater.inflate(R.layout.fragment_first,null);//第一个页面
        second = layoutInflater.inflate(R.layout.fragment_second,null);//第二个页面
        third = layoutInflater.inflate(R.layout.fragment_third,null);//第三个页面
        //创建一个集合来存放这三个页面
        pages.add(first);
        pages.add(second);
        pages.add(third);

        viewPagerAdapter = new ViewPagerAdapter(pages); //创建适配器对象
        viewPager.setAdapter(viewPagerAdapter); // 创建适配器

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面滑动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            //页面选中
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0 :
                        setDot(true,false,false);
                        break;
                    case 1:
                        setDot(false,true,false);
                        break;
                    case 2:
                        setDot(false,false,true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @OnClick({R.id.back_tv,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

        }
    }

    @Override
    public void versionAppUpdateView() {

    }



    /**
     * 查询结果 信息反馈
     */
    public void showProposalView(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.exception_reminder_dialog, null);
        builder.setView(dialogView);
        TextView reTry = dialogView.findViewById(R.id.tv_re_try);
        TextView cancel = dialogView.findViewById(R.id.tv_cancel);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.show();

        reTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });
    }


    //当圆点在选中与未选中时的背景（可以自己设置)
    private void setDot(boolean selectA, boolean selectB, boolean selectC) {

        if (selectA)  {
            dot1.setBackgroundResource(R.mipmap.dot_select);
        } else {
            dot1.setBackgroundResource(R.mipmap.dot_unchecked);
        }
        if (selectB)  {
            dot2.setBackgroundResource(R.mipmap.dot_select);
        } else {
            dot2.setBackgroundResource(R.mipmap.dot_unchecked);
        }
        if (selectC)  {
            dot3.setBackgroundResource(R.mipmap.dot_select);
        } else {
            dot3.setBackgroundResource(R.mipmap.dot_unchecked);
        }
    }

}