package com.lakala.pos.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.lakala.pos.R;
import com.lakala.pos.interfaces.ChangePwdView;
import com.lakala.pos.presente.ChangePwdPresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePwdActivity extends MVPActivity<ChangePwdView, ChangePwdPresenter> implements ChangePwdView {


    @BindView(R.id.et_old_pwd)
    EditText oldPwd;

    @BindView(R.id.et_new_pwd)
    EditText newPwd;

    @BindView(R.id.et_renew_pwd)
    EditText reNewPwd;

    @Override
    protected ChangePwdPresenter createPresenter() {
        return new ChangePwdPresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.back_tv,R.id.submit_modi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv://返回
                finish();
                break;
            case R.id.submit_modi://提交修改密码
                checkPwd();
                break;

        }
    }


    private void checkPwd() {
        String oldP = oldPwd.getText().toString();
        String newP = newPwd.getText().toString();
        String reNewP = reNewPwd.getText().toString();

        if (TextUtils.isEmpty(oldP)) {
            ToastUtil.showToast("旧密码不能为空");
        } else if (TextUtils.isEmpty(newP)) {
            ToastUtil.showToast("新密码不能为空");
        } else if (TextUtils.isEmpty(reNewP)) {
            ToastUtil.showToast("确认密码不能为空");
        } else {
            if (newP.equals(reNewP)) {
                mPresenter.subNewPwd(oldP, newP);
            } else {
                ToastUtil.showToast("两次输入密码不一致请重新输入");
            }
        }

    }

    @Override
    public void changePwdResult(String resu) {
        LogUtil.i("修改密码成功回调信息： " + resu);

    }
}