package com.campussecurity.app.patrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.databinding.ActivityPatrolContentBinding;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.campussecurity.app.patrol.model.PatrolTaskItemBean;
import com.campussecurity.app.securitycheck.AddSecurityCheckActivity;
import com.campussecurity.app.securitycheck.ProcessorEvent;
import com.campussecurity.app.securitycheck.ProcessorListActivity;
import com.campussecurity.app.securitycheck.SecurityCheckListActivity;
import com.hao.common.adapter.OnItemChildClickListener;
import com.hao.common.base.BaseDataBindingActivity;
import com.hao.common.manager.AppManager;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.view.loadview.ILoadDataView;
import com.hao.common.rx.RESTResultTransformBoolean;
import com.hao.common.rx.RxBus;
import com.hao.common.rx.RxUtil;
import com.hao.common.utils.SPUtil;
import com.hao.common.utils.ToastUtil;
import com.hao.common.utils.ViewUtils;
import com.hao.common.widget.LoadingLayout;
import com.hao.common.widget.titlebar.TitleBar;
import com.orhanobut.logger.Logger;

import rx.functions.Action1;

@RequiresPresenter(PatrolContentPresenter.class)
public class PatrolContentActivity extends BaseDataBindingActivity<PatrolContentPresenter, ActivityPatrolContentBinding> implements ILoadDataView<PatrolTaskDetails>, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, LoadingLayout.OnReloadListener {
    private final static String PATROLTASK_ID = "patrolTaskId";
    private int patrolTaskId;
    private PatrolTaskItemAdapter mPatrolTaskItemAdapter;
    private User mUser = ((App) AppManager.getApp()).cacheUser;


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
        mBinding.titlebar.setTitleText(getString(R.string.title_patrol_contnet_activity));
        mBinding.titlebar.setDelegate(new TitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                mSwipeBackHelper.backward();
            }
        });
        if (SPUtil.getBoolean("tipIsGone", false)) {
            mBinding.tvTip.setVisibility(View.GONE);
        } else {
            mBinding.tvTip.setVisibility(View.VISIBLE);
        }
        ViewUtils.initVerticalLinearRecyclerView(this, mBinding.recyclerView);
        mPatrolTaskItemAdapter = new PatrolTaskItemAdapter(mBinding.recyclerView);
        mBinding.recyclerView.setAdapter(mPatrolTaskItemAdapter);
    }

    @Override
    protected void setListener() {
        mBinding.tvTip.setOnClickListener(this);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mBinding.loadingLayout.setOnReloadListener(this);
        mPatrolTaskItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                switch (childView.getId()) {
                    case R.id.tv_operation:
                        showScaCard(position);
                        break;
                    case R.id.tv_operation_manageer:
                        mSwipeBackHelper.forward(ProcessorListActivity.newIntent(getContext(), position, mPatrolTaskItemAdapter.getItem(position).getSchoolId() + ""));
                        break;
                    case R.id.root_layout:
                        checkSecurity(position);
                        break;
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        RxBus.toObservableAndBindToLifecycle(ProcessorEvent.class, this).subscribe(new Action1<ProcessorEvent>() {
            @Override
            public void call(ProcessorEvent processorEvent) {
                mPatrolTaskItemAdapter.getItem(processorEvent.position).mProcessorModel = processorEvent.mProcessorModel;
                mPatrolTaskItemAdapter.notifyItemChangedWrapper(processorEvent.position);
            }
        });
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
        mBinding.swipeRefreshLayout.setRefreshing(false);
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
        mBinding.swipeRefreshLayout.setRefreshing(false);
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
                mBinding.tvTip.setVisibility(View.GONE);
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


    private void checkSecurity(int position) {
        PatrolTaskItemBean patrolTaskItemBean = mPatrolTaskItemAdapter.getItem(position);
        new MaterialDialog.Builder(this).title(patrolTaskItemBean.getName() + "").content(patrolTaskItemBean.getDescription() + "").positiveText("全都正常").positiveColor(getResources().getColor(R.color.colorPatrolGreen)).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                //没有问题，提交
                postSecurityNormal(position);
            }
        }).negativeText("有安全问题").neutralColor(getResources().getColor(R.color.colorPatrolRed)).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                secrityException(position);
            }
        }).show();


    }

    private void postSecurityNormal(int position) {
        /*PatrolTaskItemBean patrolTaskItemBean = mPatrolTaskItemAdapter.getItem(position);
        if(patrolTaskItemBean.mProcessorModel==null){
            ToastUtil.show(R.string.str_select_processor);
            return;
        }else{
            showLoadingDialog();
            RestDataSoure.newInstance().addSecurityTask(patrolTaskItemBean.mProcessorModel.getAccountGuid(),
                    mUser.accountGuid,patrolTaskItemBean.getSchoolId()+"",
                    patrolTaskItemBean.getName(),
                    patrolTaskItemBean.getDescription(),"")
                    .compose(RxUtil.applySchedulersJobUI())
                    .compose(new RESTResultTransformBoolean())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            dismissLoadingDialog();
                            ToastUtil.show(R.string.post_successful);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            dismissLoadingDialog();
                            ToastUtil.show(throwable.getMessage());
                        }
                    });
        }*/

    }

    public void secrityException(int position) {
        mSwipeBackHelper.forward(AddSecurityCheckActivity.newItent(this,"111","1"));
    }

    private void showScaCard(int position) {
        showLoadingDialog(R.string.scan_carding);
        RestDataSoure.newInstance().scanCard(mUser.accountGuid, patrolTaskId, mPatrolTaskItemAdapter.getItem(position).getPatrolTaskItemId(),
                mPatrolTaskItemAdapter.getItem(position).getName())
                .compose(RxUtil.applySchedulersJobUI())
                .compose(new RESTResultTransformBoolean())
                .compose(bindToLifecycle()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                dismissLoadingDialog();
                mPatrolTaskItemAdapter.setRecord(position);


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                dismissLoadingDialog();
                Logger.e(throwable.getMessage());
                mPatrolTaskItemAdapter.notifyDataSetChangedWrapper();
                ToastUtil.show(throwable.getMessage());
            }
        });
    }

}
