package com.campussecurity.app.login;

import android.os.Bundle;
import android.widget.EditText;

import com.campussecurity.app.App;
import com.campussecurity.app.Constant;
import com.campussecurity.app.R;
import com.campussecurity.app.main.MainActivity;
import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.base.BaseActivity;
import com.hao.common.manager.AppManager;
import com.hao.common.rx.RESTResultTransformerModel;
import com.hao.common.rx.RxUtil;
import com.hao.common.utils.AESUtil;
import com.hao.common.utils.SPUtil;
import com.hao.common.utils.StringUtil;
import com.hao.common.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import rx.functions.Action1;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    EditText mUserEditText;
    EditText mPasswordEditText;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mUserEditText = (EditText) findViewById(R.id.email);
        mPasswordEditText = (EditText) findViewById(R.id.password);
    }

    @Override
    protected void setListener() {
        setOnClick(R.id.email_sign_in_button, new Action1() {
            @Override
            public void call(Object o) {
                if (StringUtil.isEmpty(mUserEditText)) {
                    ToastUtil.show(R.string.tip_user_not_null);
                    return;
                }
                if (StringUtil.isEmpty(mPasswordEditText)) {
                    ToastUtil.show(R.string.tip_password_not_null);
                    return;
                }
                showLoadingDialog();
                RestDataSoure.newInstance().login(StringUtil.getEditTextStr(mUserEditText), StringUtil.getEditTextStr(mPasswordEditText))
                        .compose(new RESTResultTransformerModel<>())
                        .compose(RxUtil.applySchedulersJobUI())
                        .subscribe(user -> {
                            dismissLoadingDialog();
                            ((App) AppManager.getApp()).cacheUser = user;
                            try {
                                SPUtil.putString(Constant.APP_USER_NAME, StringUtil.getEditTextStr(mUserEditText));
                                SPUtil.putString(Constant.APP_USER_PASSWORD, AESUtil.EncodeAES(StringUtil.getEditTextStr(mPasswordEditText), Constant.ENCRYPTION_KEY));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mSwipeBackHelper.forwardAndFinish(MainActivity.class);
                        }, throwable -> {
                            Logger.e(throwable.toString());
                            ToastUtil.show(throwable.getMessage());
                            dismissLoadingDialog();
                        });
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}

