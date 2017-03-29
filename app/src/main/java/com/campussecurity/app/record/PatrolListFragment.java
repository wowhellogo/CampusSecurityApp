package com.campussecurity.app.record;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.patrol.PatrolContentActivity;
import com.campussecurity.app.patrol.PatrolTaskAdapter;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.hao.common.base.BaseLoadFragment;
import com.hao.common.manager.AppManager;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.rx.RESTResultTransformerList;

/**
 * @Package com.campussecurity.app.record
 * @作 用:巡逻纪录界面
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年03月18日  15:57
 */

@RequiresPresenter(LoadPresenter.class)
public class PatrolListFragment extends BaseLoadFragment<PatrolTask> {
    private User mUser;


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mUser = ((App) AppManager.getApp()).cacheUser;
    }



    @Override
    protected void createAdapter() {
        mAdapter = new PatrolTaskAdapter(mRecyclerView, R.layout.item_patrol);
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        getBaseActivity().mSwipeBackHelper.forward(PatrolContentActivity.newIntent(getBaseActivity(), mAdapter.getItem(position).getPatrolTaskId()));
    }

    @Override
    protected void onLazyLoadOnce() {

        onRefresh();
    }

    @Override
    public void onRefresh() {
        getPresenter()
                .restPageSize()
                .setShowing(true).loadList(RestDataSoure.newInstance()
                .getPatrolTaskRecord(mUser.accountGuid,getPresenter().startIndex,getPresenter().pageSize)
                .compose(new RESTResultTransformerList<>()));
    }

    @Override
    public void onLoadMore() {
        getPresenter()
                .setPageSize()
                .setShowing(false)
                .loadList(RestDataSoure.newInstance()
                .getPatrolTaskRecord(mUser.accountGuid,getPresenter().startIndex,getPresenter().pageSize)
                .compose(new RESTResultTransformerList<>()));
    }
}
