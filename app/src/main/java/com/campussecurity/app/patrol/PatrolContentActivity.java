package com.campussecurity.app.patrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.campussecurity.app.R;
import com.campussecurity.app.databinding.ActivityPatrolContentBinding;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.hao.common.base.BaseDataBindingActivity;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.view.loadview.ILoadDataView;
import com.hao.common.utils.SPUtil;
import com.hao.common.utils.ToastUtil;
import com.hao.common.utils.ViewUtils;
import com.hao.common.widget.LoadingLayout;
import com.orhanobut.logger.Logger;

@RequiresPresenter(PatrolContentPresenter.class)
public class PatrolContentActivity extends BaseDataBindingActivity<PatrolContentPresenter, ActivityPatrolContentBinding> implements ILoadDataView<PatrolTaskDetails>, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, LoadingLayout.OnReloadListener {
    private final static String PATROLTASK_ID = "patrolTaskId";
    private int patrolTaskId;
    private RecyclerView mRecyclerView;
    private PatrolTaskItemAdapter mPatrolTaskItemAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadingLayout mLoadingLayout;
    private View tip;


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
        setTitle(getString(R.string.title_patrol_contnet_activity));
        mRecyclerView = getViewById(R.id.recycler_view);
        mSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mLoadingLayout = getViewById(R.id.loading_layout);
        tip = getViewById(R.id.tv_tip);
        if (SPUtil.getBoolean("tipIsGone", false)) {
            tip.setVisibility(View.GONE);
        } else {
            tip.setVisibility(View.VISIBLE);
        }
        ViewUtils.initVerticalLinearRecyclerView(this, mRecyclerView);
        mPatrolTaskItemAdapter = new PatrolTaskItemAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mPatrolTaskItemAdapter);
    }

    @Override
    protected void setListener() {
        tip.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLoadingLayout.setOnReloadListener(this);
    }

    @Override
    public void onClickLeftCtv() {
        mSwipeBackHelper.backward();
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
        mPatrolTaskItemAdapter.setData(patrolTaskDetails.getItems());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tip:
                SPUtil.putBoolean("tipIsGone", true);
                tip.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onRefresh() {
        getPresenter().loadPatrolContent(patrolTaskId);
    }

    @Override
    public void onReload(View v) {
        getPresenter().loadPatrolContent(patrolTaskId);
    }
}
