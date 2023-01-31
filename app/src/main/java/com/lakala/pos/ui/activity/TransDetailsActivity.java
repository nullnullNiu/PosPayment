package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lakala.pos.R;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TransDetailsActivity extends MVPActivity<IHomeView, MainActivityPresenter> implements IHomeView {

    @BindView(R.id.previous)
    LinearLayout previous;

    @BindView(R.id.next)
    LinearLayout next;


    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_details);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.back_tv, R.id.reprint_tv, R.id.draw_bill_tv, R.id.previous, R.id.next,R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.reprint_tv:// 重打印


                break;
            case R.id.draw_bill_tv:// 开发票


                break;


            case R.id.previous:// 上一页


                break;


            case R.id.next:// 下一页


                break;
            case R.id.submit_tv: // 确定


                break;

        }
    }

    @Override
    public void versionAppUpdateView() {

    }


}