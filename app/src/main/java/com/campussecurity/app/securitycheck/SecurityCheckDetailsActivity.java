package com.campussecurity.app.securitycheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.campussecurity.app.R;
import com.campussecurity.app.databinding.ActivitySecurityCheckDetailsBinding;
import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.base.BaseDataBindingActivity;
import com.hao.common.nucleus.factory.RequiresPresenter;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.nucleus.view.loadview.ILoadDataView;
import com.hao.common.rx.RESTResultTransformerModel;
import com.hao.common.utils.StorageUtil;
import com.hao.common.utils.ToastUtil;
import com.hao.common.widget.BGASortableNinePhotoLayout;
import com.hao.common.widget.LoadingLayout;
import com.hao.common.widget.titlebar.TitleBar;
import com.picker.view.activity.PhotoPickerActivity;
import com.picker.view.activity.PhotoPickerPreviewActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;



/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:安全任务详情处理
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/10 0010
 */
@RequiresPresenter(LoadPresenter.class)
public class SecurityCheckDetailsActivity extends BaseDataBindingActivity<LoadPresenter, ActivitySecurityCheckDetailsBinding> implements
        ILoadDataView<SecurityCheckDetailModel>, BGASortableNinePhotoLayout.Delegate {
    private static final int REQUEST_CODE_PHOTO_PREVIEW=2;
    private static int REQUEST_CODE_CHOOSE_PHOTO=3;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private String securityTaskId;
    private SecurityCheckDetailModel checkDetailModel;
    private static final int MAX_PHOTO_COUNT=4;
    List<SecurityCheckDetailModel.PictureModel> pictureModels = new ArrayList<>();//网络图片
    private ArrayList<String> pictureStrList = new ArrayList<>();

    public static Intent newIntent(Context context, String securityTaskId) {
        Intent intent = new Intent(context, SecurityCheckDetailsActivity.class);
        intent.putExtra("securityTaskId", securityTaskId);
        return intent;
    }

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_security_check_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBinding.sortableNinePhoneLayout.setDelegate(this);

    }

    @Override
    protected void setListener() {
        mBinding.titleBar.setDelegate(new TitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                mSwipeBackHelper.backward();
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        securityTaskId = getIntent().getStringExtra("securityTaskId");
        getPresenter().loadModel(RestDataSoure.newInstance().getSecurityTaskContent(securityTaskId).compose(new RESTResultTransformerModel<SecurityCheckDetailModel>()));
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
    public void loadDataToUI(SecurityCheckDetailModel securityCheckDetailModel) {
        checkDetailModel = securityCheckDetailModel;
        mBinding.setModel(securityCheckDetailModel);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            ArrayList<String> selectedImages=PhotoPickerActivity.getSelectedImages(data);
            mBinding.sortableNinePhoneLayout.setData(selectedImages);
            filterList(selectedImages);
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            ArrayList<String> selectedImages=PhotoPickerActivity.getSelectedImages(data);
            mBinding.sortableNinePhoneLayout.setData(selectedImages);
            filterList(selectedImages);
        }

    }

    public void filterList(List<String> pictrueList) {
        if (pictrueList != null && pictrueList.size() > 0) {
            for (int i = 0; i < pictrueList.size(); i++) {
                String str = pictrueList.get(i);
                if (!str.startsWith("http")) {
                    if (pictureStrList != null && pictureStrList.size() < 9) {
                        pictureStrList.add(str);
                    }
                }
            }
        }
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mSwipeBackHelper.forward(PhotoPickerActivity.newIntent(this, StorageUtil.getImageDir(), MAX_PHOTO_COUNT, mBinding.sortableNinePhoneLayout.getData(),
                    getString(R.string.confirm)),REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.image_selection_requires_the_following_permissions_photo_on_the_access_device),
                    REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        choicePhotoWrapper();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .build()
                    .show();
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }


    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mBinding.sortableNinePhoneLayout.removeItem(position);
        if (model.startsWith("http")) {
            pictureModels.remove(position);
        }
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mSwipeBackHelper.forward(PhotoPickerPreviewActivity.newIntent(this, MAX_PHOTO_COUNT, models, models, position, getString(R.string.confirm), false), REQUEST_CODE_PHOTO_PREVIEW);
    }
}
