package com.campussecurity.app.message;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.campussecurity.app.securitycheck.SecurityCheckDetailsActivity;
import com.google.gson.GsonBuilder;
import com.hao.common.base.BaseLoadActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.manager.AppManager;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.rx.RESTResultTransformBoolean;
import com.hao.common.rx.RESTResultTransformerList;
import com.hao.common.rx.RxUtil;

@RequiresPresenter(LoadPresenter.class)
public class SystemMessageActivity extends BaseLoadActivity<LoadPresenter, AppPushMessage> {
    private User mUser;
    @Override
    protected void createAdapter() {
        mAdapter = new AppPushMessageAdapter(mRecyclerView, R.layout.item_app_push_message);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        super.processLogic(savedInstanceState);
        setTitle(getString(R.string.title_message));
        mUser = ((App) AppManager.getApp()).cacheUser;
        onRefresh();
    }

    @Override
    public void onReload(View v) {
        onRefresh();
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        AppPushMessage message=mAdapter.getItem(position);
        RestDataSoure.newInstance().readAppPushRecord(message.getAppPushId()).compose(RxUtil.applySchedulersJobUI())
                .compose(new RESTResultTransformBoolean())
                .subscribe(aBoolean -> {
                    onRefresh();
                });
        PatrolTask patrolTask = null;
        if(message.getData()!=null&&!message.getData().equals("")){
            GsonBuilder builder=new GsonBuilder();
            patrolTask=builder.create().fromJson(message.getData(), PatrolTask.class);
        }
        if(patrolTask!=null){
            mSwipeBackHelper.forward(SecurityCheckDetailsActivity.newIntent(this,patrolTask.getSchoolId()+""));
        }

        /*switch (message.getType()){
            case 0://安全任务

                break;
            case 1://每天巡检任务

                break;
            case 2://临时巡检任务

                break;
            case 3://紧急巡检任务

                break;
        }*/
    }

    @Override
    public void onRefresh() {
        getPresenter()
                .restPageSize()
                .setShowing(true)
                .loadList(RestDataSoure.newInstance()
                        .getAppPushRecord(mUser.accountGuid,getPresenter().startIndex,getPresenter().pageSize)
                        .compose(new RESTResultTransformerList<AppPushMessage>()));
    }

    @Override
    public void onLoadMore() {
        getPresenter()
                .setPageSize()
                .setShowing(false)
                .loadList(RestDataSoure.newInstance()
                        .getAppPushRecord(mUser.accountGuid,getPresenter().startIndex,getPresenter().pageSize)
                        .compose(new RESTResultTransformerList<AppPushMessage>()));
    }


}
