package com.lakala.pos.dialog;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lakala.pos.R;
import com.lakala.pos.utils.ButtonUtils;
import com.lakala.pos.utils.QRCodeUtil;


public class BillingCodeDialog extends DialogFragment {

    public BillingCodeDialog(){}

    ImageView payImg;
    TextView  tvBack,tv_time;

    private ConfirmListener confirmListener;

    public interface ConfirmListener {
        void onBack();
    }

    public void setConfirmListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public static BillingCodeDialog newInstance(String url) {
        BillingCodeDialog fragment = new BillingCodeDialog();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final String url = getArguments().getString("url");

        // 必须设置这两个,才能设置宽度
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        // 遮罩层透明度
        getDialog().getWindow().setDimAmount(0.5f);
        View view = inflater.inflate(R.layout.billing_code, null);

        payImg = view.findViewById(R.id.iv_img);
        tvBack = view.findViewById(R.id.tv_back);

        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(url, 300, 300);
        payImg.setImageBitmap(mBitmap);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ButtonUtils.isFastTimeClick()) {
//                    confirmListener.onBack();
                    dismiss();
                }
            }
        });

        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_VERTICAL;
//        params.y = -100;
        getDialog().getWindow().setAttributes(params);
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        //避免重复添加的异常 java.lang.IllegalStateException: Fragment already added
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }

        //避免状态丢失的异常 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }



}
