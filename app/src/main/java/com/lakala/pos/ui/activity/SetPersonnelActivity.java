package com.lakala.pos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.lakala.pos.R;
import com.lakala.pos.interfaces.ISetingView;
import com.lakala.pos.presente.SetPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPersonnelActivity extends MVPActivity<ISetingView, SetPresenter> implements ISetingView , View.OnClickListener {

    @BindView(R.id.type_name)
    TextView typeName;

    @BindView(R.id.iv_look)
    ImageView ivLook;

    @BindView(R.id.add_connect)
    LinearLayout addView;

    ImageView inIvLook,inIvReduce;

    @BindView(R.id.et_account)
    EditText account;

    @BindView(R.id.et_pwd)
    EditText pwd;

    EditText inAccount,inPwd;

    int type = 0;
    private boolean isLook = false;
    private boolean inIsLook = false;
    @Override
    protected SetPresenter createPresenter() {
        return new SetPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_personnel);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type",0);
        initTypeName(type);

    }


    private void initTypeName(int type){
        LogUtil.i("type=   " + type);
        switch (type){
            case 1:
                typeName.setVisibility(View.GONE);
                break;
            case 2:
                typeName.setVisibility(View.VISIBLE);
                typeName.setText("会计");
                break;
            case 3:
                typeName.setVisibility(View.VISIBLE);
                typeName.setText("收银");
                break;
            default:
                typeName.setVisibility(View.GONE);
                break;
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @OnClick({R.id.back_tv,R.id.iv_add,R.id.iv_look,R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回
                finish();
                break;
            case R.id.iv_add:// 添加
                addAgentLayout();
                break;

            case R.id.iv_look:// 看
                if (isLook){
                    isLook = false;
                    ivLook.setImageResource(R.mipmap.no_look);
                } else {
                    isLook = true;
                    ivLook.setImageResource(R.mipmap.look);
                }
                break;
            case R.id.submit_tv:
                Intent i = new Intent(this,SetingActivity.class);
                i.putExtra("type",type);
                i.putExtra("name","");
                startActivity(i);
                finish();
                break;
        }
    }

    // 添加代理人
    private void addAgentLayout() {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.include_personnel_layout, null);
        addView.removeAllViews();
        addView.addView(view, layoutParams);
        initaddView(view);
    }

    private void initaddView(View view){
        inIvReduce =view.findViewById(R.id.in_iv_reduce);
        inIvLook =view.findViewById(R.id.in_iv_look);

        inAccount =view.findViewById(R.id.et_account_name);
        inPwd =view.findViewById(R.id.et_pwd);
        inIvReduce.setOnClickListener(this);
        inIvLook.setOnClickListener(this);
    }

    @Override
    public void versionAppUpdateView() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.in_iv_reduce:
                addView.removeAllViews();
                break;

            case R.id.in_iv_look:
                if (inIsLook){
                    inIsLook = false;
                    inIvLook.setImageResource(R.mipmap.no_look);
                } else {
                    inIsLook = true;
                    inIvLook.setImageResource(R.mipmap.look);
                }
                break;

        }
    }
}