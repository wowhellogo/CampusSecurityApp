package com.campussecurity.app.patrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.campussecurity.app.R;
import com.campussecurity.app.databinding.ActivityPatrolContentBinding;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.hao.common.base.BaseDataBindingActivity;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.view.loadview.ILoadDataView;
import com.hao.common.utils.ToastUtil;
import com.hao.common.widget.LoadingLayout;
import com.orhanobut.logger.Logger;

@RequiresPresenter(PatrolContentPresenter.class)
public class PatrolContentActivity extends BaseDataBindingActivity<PatrolContentPresenter, ActivityPatrolContentBinding> implements ILoadDataView<PatrolTaskDetails> {
    private final static String PATROLTASK_ID = "patrolTaskId";
    private int patrolTaskId;


    public static Intent newIntent(Context context, int patrolTaskId) {
        Intent intent = new Intent(context, PatrolContentActivity.class);
        intent.putExtra(PATROLTASK_ID, patrolTaskId);
        return intent;
    }

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_patrol_content;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        patrolTaskId = getIntent().getIntExtra(PATROLTASK_ID, 0);
        getPresenter().loadPatrolContent(patrolTaskId);
        showLoadingView();

    }

    @Override
    public void loadDataToUI(PatrolTaskDetails patrolTaskDetails) {
        Logger.e(patrolTaskDetails.toString());
        mBinding.setModel(patrolTaskDetails);

    }


    @Override
    public void showLoadingView() {
        mBinding.loadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void showContentView() {
        mBinding.loadingLayout.setStatus(LoadingLayout.Success);
    }

    @Override
    public void showEmptyView() {
        mBinding.loadingLayout.setStatus(LoadingLayout.Empty);
    }

    @Override
    public void showFailView() {
        mBinding.loadingLayout.setStatus(LoadingLayout.Error);
    }

    @Override
    public void showError(String message) {
        ToastUtil.show(message);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
