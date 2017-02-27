package com.campussecurity.app.patrol;

import android.os.Bundle;

import com.campussecurity.app.App;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.hao.common.manager.AppManager;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.rx.RESTResultTransformerList;

/**
 * @Package com.campussecurity.app.patrol
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月21日  15:22
 */
public class PatrolListPresenter extends LoadPresenter {
    private User mUser = ((App) AppManager.getApp()).cacheUser;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        loadList(RestDataSoure.newInstance().getPatrolTask(mUser.accountGuid).compose(new RESTResultTransformerList<PatrolTask>()));
    }

    public void loadPatrolList(){
        loadList(RestDataSoure.newInstance().getPatrolTask(mUser.accountGuid).compose(new RESTResultTransformerList<PatrolTask>()));
    }

}
