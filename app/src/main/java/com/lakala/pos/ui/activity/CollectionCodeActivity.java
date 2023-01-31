package com.lakala.pos.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lakala.pos.R;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.presente.MainActivityPresenter;
import com.lakala.pos.ui.MVPActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CollectionCodeActivity extends MVPActivity<IHomeView, MainActivityPresenter> implements IHomeView {


//    @BindView(R.id.my_qr)
//    ImageView my_qr;

    double amount = 0;

    RequestOptions options = new RequestOptions().bitmapTransform(new RoundedCorners(8)).error(R.drawable.default_image);

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_code);
        ButterKnife.bind(this);

        ImageView my_qr = findViewById(R.id.my_qr);
        amount = getIntent().getDoubleExtra("amount",0);
        // 生成支付码
        Glide.with(this).load("qrcode_url").apply(options).into(my_qr);

    }


    @OnClick({R.id.back_tv, R.id.return_tv, R.id.select_result_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 推出

                finish();

                break;


            case R.id.return_tv:// 返回



                break;

            case R.id.select_result_tv:// 查询结果


                showProposalView(this);

                break;

        }
    }

    @Override
    public void versionAppUpdateView() {

    }


    /**
     * 检卡超时 信息反馈
     */
    public void showProposalView(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.query_failed_dialog, null);
        builder.setView(dialogView);
        TextView tv_determine = dialogView.findViewById(R.id.tv_determine);
        TextView cancel = dialogView.findViewById(R.id.tv_cancel);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.show();

        tv_determine.setOnClickListener(new View.OnClickListener() {
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

}