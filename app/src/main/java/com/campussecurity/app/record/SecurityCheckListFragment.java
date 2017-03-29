package com.campussecurity.app.record;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.securitycheck.SecurityCheckAdapter;
import com.campussecurity.app.securitycheck.SecurityCheckDetailsActivity;
import com.campussecurity.app.securitycheck.SecurityTaskModel;
import com.hao.common.base.BaseLoadFragment;
import com.hao.common.manager.AppManager;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.rx.RESTResultTransformerList;

/**
 * @Package com.campussecurity.app.record
 * @作 用:安全检查界面
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年03月18日  15:55
 */

@RequiresPresenter(LoadPresenter.class)
public class SecurityCheckListFragment extends BaseLoadFragment<SecurityTaskModel> {
    private User mUser;
    @Override
    protected void createAdapter() {
        mAdapter = new SecurityCheckAdapter(mRecyclerView, R.layout.item_security_check_list);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);
        mUser = ((App) AppManager.getApp()).cacheUser;
        onRefresh();
    }

    @Override
    public void onReload(View v) {
        onRefresh();
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Intent intent= SecurityCheckDetailsActivity.newIntent(getBaseActivity(),mAdapter.getItem(position).getSecurityTaskId()+"");
        getBaseActivity().mSwipeBackHelper.forward(intent);
    }

    @Override
    protected void onLazyLoadOnce() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getPresenter()
                .restPageSize()
                .setShowing(true)
                .loadList(RestDataSoure.newInstance()
                .getSecurityTaskRecord(mUser.accountGuid,getPresenter().startIndex,getPresenter().pageSize)
                .compose(new RESTResultTransformerList<SecurityTaskModel>()));
    }

    @Override
    public void onLoadMore() {
        getPresenter()
                .setPageSize()
                .setShowing(false)
                .loadList(RestDataSoure.newInstance()
                .getSecurityTaskRecord(mUser.accountGuid,getPresenter().startIndex,getPresenter().pageSize)
                .compose(new RESTResultTransformerList<SecurityTaskModel>()));
    }

}
