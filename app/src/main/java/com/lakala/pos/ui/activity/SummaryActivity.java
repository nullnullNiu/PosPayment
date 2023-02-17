package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lakala.pos.R;
import com.lakala.pos.adapter.SummaryAdapter;
import com.lakala.pos.bean.SummaryBean;
import com.lakala.pos.common.Global;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.interfaces.ISummaryView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.presente.SummaryPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SummaryActivity extends MVPActivity<ISummaryView, SummaryPresenter> implements ISummaryView {

    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @BindView(R.id.start_time)
    TextView startTime;

    @BindView(R.id.end_time)
    TextView endTime;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public String sTime = "";
    public String eTime = "";

    SummaryAdapter summaryAdapter;

    List<SummaryBean.Data> list;

    @Override
    protected SummaryPresenter createPresenter() {
        return new SummaryPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.back_tv,R.id.end_time, R.id.start_time, R.id.time_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

            case R.id.time_tv:// 查询

                if (TextUtils.isEmpty(sTime) || TextUtils.isEmpty(startTime.getText().toString())) {
                    ToastUtil.showToast("请选择开始日期");
                    return;
                }

                if (TextUtils.isEmpty(eTime) || TextUtils.isEmpty(endTime.getText().toString())) {
                    ToastUtil.showToast("请选择结束日期");
                    return;
                }

                try {
                    JSONObject object = new JSONObject();
                    object.put("deviceCode", Global.DEVICE_ID);
                    object.put("startDate",sTime.replace("-",""));
                    object.put("endDate",eTime.replace("-",""));
                    mPresenter.onCensus(object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;

            case R.id.start_time:
                mPresenter.onDateSelection(this, 1);
                break;

            case R.id.end_time:
                mPresenter.onDateSelection(this, 2);
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

    @Override
    public void onCensusResult(SummaryBean summaryBean) {

        if (summaryBean.getData() == null) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        tvEmpty.setVisibility(View.GONE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        summaryAdapter = new SummaryAdapter(this, summaryBean.getData());
        recyclerView.setAdapter(summaryAdapter);

    }


}