package com.campussecurity.app.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.LoginActivity;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.base.BaseActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.manager.AppManager;
import com.hao.common.rx.RESTResultTransformBoolean;
import com.hao.common.rx.RxUtil;
import com.hao.common.utils.StringUtil;
import com.hao.common.utils.ToastUtil;

/**
 * 修改密码
 */
public class PasswordEditActivity extends BaseActivity implements View.OnClickListener {

    EditText mTvOldPassword;
    EditText mTvNewPassword;
    EditText mTvNewPassword2;
    private User mUser;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_password_edit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(getString(R.string.password_edit));
        mTvOldPassword = (EditText) getViewById(R.id.tvOldPassword);
        mTvNewPassword = (EditText) getViewById(R.id.tvNewPassword);
        mTvNewPassword2 = (EditText) getViewById(R.id.tvNewPassword2);
    }


    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }

    @Override
    protected void setListener() {
        getViewById(R.id.btnPassworldEdit).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mUser = ((App) AppManager.getApp()).cacheUser;
    }


    @Override
    public void onClick(View v) {
        String oldPassword = mTvOldPassword.getText().toString();
        String newPassword = mTvNewPassword.getText().toString();
        if (!StringUtil.getEditTextStr(mTvNewPassword).equals(StringUtil.getEditTextStr(mTvNewPassword2))) {
            ToastUtil.show(R.string.str_password_different);
        }
        if (!StringUtil.isEmpty(mTvNewPassword) && !StringUtil.isEmpty(mTvOldPassword)) {
            RestDataSoure.newInstance().dutyTree(mUser.userName, oldPassword, newPassword, StringUtil.getEditTextStr(mTvNewPassword2))
                    .compose(RxUtil.applySchedulersJobUI())
                    .compose(new RESTResultTransformBoolean())
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            ToastUtil.show(getString(R.string.post_successful));
                            mSwipeBackHelper.forwardAndFinish(LoginActivity.class);
                        } else {
                            ToastUtil.show(getString(R.string.post_fail));
                        }
                    }, throwable -> {
                        ToastUtil.show(getString(R.string.post_fail));
                    });
        }
    }
}
