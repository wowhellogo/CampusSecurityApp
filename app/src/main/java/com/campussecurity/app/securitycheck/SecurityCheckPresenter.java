package com.campussecurity.app.securitycheck;

import android.os.Bundle;

import com.campussecurity.app.App;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.manager.AppManager;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.rx.RESTResultTransformerList;

/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/5 0005
 */

public class SecurityCheckPresenter extends LoadPresenter {
    private User mUser=null;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        mUser=((App) AppManager.getApp()).cacheUser;
        loadList(RestDataSoure.newInstance().getSecurityTaskList(mUser.accountGuid).compose(new RESTResultTransformerList<SecurityTaskModel>()));
    }

    public void loadSecurityTaskList() {
        loadList(RestDataSoure.newInstance().getSecurityTaskList(mUser.accountGuid).compose(new RESTResultTransformerList<SecurityTaskModel>()));
    }

}
