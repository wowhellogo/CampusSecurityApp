package com.campussecurity.app.login;

import android.os.Bundle;

import com.campussecurity.app.App;
import com.campussecurity.app.Constant;
import com.campussecurity.app.R;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.main.MainActivity;
import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.base.BaseActivity;
import com.hao.common.manager.AppManager;
import com.hao.common.rx.RESTResultTransformerModel;
import com.hao.common.rx.RxUtil;
import com.hao.common.utils.AESUtil;
import com.hao.common.utils.SPUtil;
import com.hao.common.utils.ToastUtil;

import rx.Subscriber;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {
    private String userName;
    private String password;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        launchActivity();
    }

    private void launchActivity() {
        userName = SPUtil.getString(Constant.APP_USER_NAME);
        password = SPUtil.getString(Constant.APP_USER_PASSWORD);
        if (null != userName&&!userName.equals("") && null != password&&!password.equals("")) {
            try {
                password = AESUtil.DeCodeAES(password, Constant.ENCRYPTION_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RestDataSoure.newInstance().login(userName, password).compose(RxUtil.applySchedulersJobUI()).compose(new RESTResultTransformerModel<User>()).subscribe(new Subscriber<User>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtil.show(e.getMessage());
                }

                @Override
                public void onNext(User user) {
                    ((App) AppManager.getApp()).cacheUser = user;
                    mSwipeBackHelper.forwardAndFinish(MainActivity.class);
                }
            });
        } else {
            mSwipeBackHelper.forwardAndFinish(LoginActivity.class);
        }

    }

}
