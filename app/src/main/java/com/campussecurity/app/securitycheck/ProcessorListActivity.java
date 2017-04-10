package com.campussecurity.app.securitycheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.campussecurity.app.R;
import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.adapter.BaseBindingRecyclerViewAdapter;
import com.hao.common.base.BaseLoadBindingActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.rx.RESTResultTransformerList;
import com.hao.common.rx.RxBus;
/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/11 0011
 */

@RequiresPresenter(LoadPresenter.class)
public class ProcessorListActivity extends BaseLoadBindingActivity<ProcessorModel> {
    private String schoolId;
    private int position;

    public static Intent newIntent(Context context,int position, String schoolId) {
        Intent intent = new Intent(context, ProcessorListActivity.class);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("position",position);
        return intent;
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }

    @Override
    public void onClickLeftCtv() {
        mSwipeBackHelper.backward();
    }


    @Override
    protected void createAdapter() {
        mAdapter = new BaseBindingRecyclerViewAdapter(R.layout.item_processor);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        setTitle(getString(R.string.title_process_list));
        mAdapter.setItemEventHandler(this);
        schoolId = getIntent().getStringExtra("schoolId");
        position=getIntent().getIntExtra("position",-1);
        getPresenter().loadList(RestDataSoure.newInstance()
                .getSecurityTaskSet(schoolId).compose(new RESTResultTransformerList<ProcessorModel>()));
    }


    public void onItemClick(ProcessorModel model) {
        RxBus.send(new ProcessorEvent(model,position));
        mSwipeBackHelper.backward();
    }

    @Override
    public void onReload(View v) {
        super.onReload(v);
        getPresenter().loadList(RestDataSoure.newInstance().getSecurityTaskSet(schoolId).compose(new RESTResultTransformerList<ProcessorModel>()));
    }

    @Override
    public void onRefresh() {
        getPresenter().loadList(RestDataSoure.newInstance().getSecurityTaskSet(schoolId).compose(new RESTResultTransformerList<ProcessorModel>()));
    }
}
