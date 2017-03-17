package com.campussecurity.app.securitycheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.model.User;
import com.hao.common.base.BaseLoadActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.manager.AppManager;
import com.hao.common.nucleus.factory.RequiresPresenter;

/**
 *安全检查列表界面
 */
@RequiresPresenter(SecurityCheckPresenter.class)
public class SecurityCheckListActivity extends BaseLoadActivity<SecurityCheckPresenter, SecurityTaskModel> {
    private User mUser;
    @Override
    protected void createAdapter() {
        mAdapter = new SecurityCheckAdapter(mRecyclerView, R.layout.item_security_check_list);
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitle(getString(R.string.title_security_check_list));
        mUser=((App) AppManager.getApp()).cacheUser;
        /*mTitleBar.setRightDrawable(getResources().getDrawable(R.mipmap.ic_add));*/
    }

    @Override
    public void onClickLeftCtv() {
        mSwipeBackHelper.backward();
    }


    @Override
    public void onClickRightCtv() {

    }

    @Override
    public void onReload(View v) {
        super.onReload(v);
        ((SecurityCheckPresenter) getPresenter()).loadSecurityTaskList();
    }

    @Override
    public void onRefresh() {
        ((SecurityCheckPresenter) getPresenter()).loadSecurityTaskList();
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Intent intent=SecurityCheckDetailsActivity.newIntent(this,mAdapter.getItem(position).getSecurityTaskId()+"");
        mSwipeBackHelper.forward(intent);
    }
}
