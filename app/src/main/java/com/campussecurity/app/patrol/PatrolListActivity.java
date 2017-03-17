package com.campussecurity.app.patrol;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.campussecurity.app.R;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.hao.common.adapter.BaseDivider;
import com.hao.common.adapter.OnRVItemClickListener;
import com.hao.common.base.BaseActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.view.loadview.ILoadPageListDataView;
import com.hao.common.utils.ToastUtil;
import com.hao.common.utils.ViewUtils;
import com.hao.common.widget.LoadingLayout;
import com.hao.common.widget.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * 巡逻任务
 */
@RequiresPresenter(PatrolListPresenter.class)
public class PatrolListActivity extends BaseActivity<PatrolListPresenter> implements ILoadPageListDataView<PatrolTask>,
        SwipeRefreshLayout.OnRefreshListener, LoadingLayout.OnReloadListener, OnRVItemClickListener {
    private SwipeRefreshLayout mRefreshLayout;
    private LoadingLayout mLoadingLayout;
    private RecyclerView mRecyclerView;
    private PatrolTaskAdapter mAdapter;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_patrol_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(R.string.title_activity_patrol_task);
        mRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mLoadingLayout = getViewById(R.id.loading_layout);
        mRecyclerView = getViewById(R.id.recycler_view);
        ViewUtils.initVerticalLinearRecyclerView(this, mRecyclerView);
        mAdapter = new PatrolTaskAdapter(mRecyclerView, R.layout.item_patrol);
        mRecyclerView.addItemDecoration(BaseDivider.newBitmapDivider());
        mRecyclerView.setAdapter(mAdapter);
        showLoadingView();
    }

    @Override
    public void onClickLeftCtv() {
        onBackPressed();
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mLoadingLayout.setOnReloadListener(this);
        mAdapter.setOnRVItemClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onRefreshDataToUI(List<PatrolTask> ms) {
        if (mAdapter != null) mAdapter.setData(ms);
    }

    @Override
    public void onLoadMoreDataToUI(List<PatrolTask> ms) {
        if (mAdapter != null) mAdapter.addMoreData(ms);
    }

    @Override
    public void onRefreshComplete() {
        if (mRefreshLayout != null) mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadComplete() {
        if (mRecyclerView instanceof XRecyclerView) {
            XRecyclerView xRecyclerView = (XRecyclerView) mRecyclerView;
            xRecyclerView.loadMoreComplete();
            if (mRefreshLayout != null) mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onNoDate() {
        if (mRefreshLayout != null) mRefreshLayout.setRefreshing(false);
        ToastUtil.show("暂无数据");
    }

    @Override
    public void onNoMoreLoad() {
        if (mRecyclerView instanceof XRecyclerView) {
            XRecyclerView xRecyclerView = (XRecyclerView) mRecyclerView;
            xRecyclerView.noMoreLoading();
            if (mRefreshLayout != null) mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public int getTotalItem() {
        return mAdapter.getItemCount();
    }

    @Override
    public void showLoadingView() {
        if (mLoadingLayout != null) mLoadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void showContentView() {
        if (mLoadingLayout != null) mLoadingLayout.setStatus(LoadingLayout.Success);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmptyView() {
        if (mLoadingLayout != null) mLoadingLayout.setStatus(LoadingLayout.Empty);
    }

    @Override
    public void showFailView() {
        if (mLoadingLayout != null) mLoadingLayout.setStatus(LoadingLayout.Error);
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
    public void onRefresh() {
        getPresenter().loadPatrolList();
    }

    @Override
    public void onReload(View v) {
        getPresenter().loadPatrolList();
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        mSwipeBackHelper.forward(PatrolContentActivity.newIntent(this, mAdapter.getItem(position).getPatrolTaskId()));
    }
}
