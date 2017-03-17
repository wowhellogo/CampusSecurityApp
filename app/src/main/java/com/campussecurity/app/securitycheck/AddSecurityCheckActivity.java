package com.campussecurity.app.securitycheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.utils.RxTransforemerUtisl;
import com.hao.common.base.BaseActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.manager.AppManager;
import com.hao.common.rx.RESTResultTransformBoolean;
import com.hao.common.rx.RxUtil;
import com.hao.common.utils.StorageUtil;
import com.hao.common.utils.ToastUtil;
import com.hao.common.widget.BGASortableNinePhotoLayout;
import com.orhanobut.logger.Logger;
import com.picker.view.activity.PhotoPickerActivity;
import com.picker.view.activity.PhotoPickerPreviewActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:添加安全任务
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/11 0011
 */

public class AddSecurityCheckActivity extends BaseActivity implements View.OnClickListener, BGASortableNinePhotoLayout.Delegate {
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    private static final int MAX_PHOTO_COUNT = 8;
    private static int REQUEST_CODE_CHOOSE_PHOTO = 3;
    EditText edTitle;
    EditText edExplain;
    BGASortableNinePhotoLayout mBGASortableNinePhotoLayout;

    private User mUser;
    private String authorAccountGuid;
    private String schoolId;
    private String patrolsId;//地点ID

    List<SecurityCheckDetailModel.PictureModel> pictureModels = new ArrayList<>();//网络图片
    private ArrayList<String> pictureStrList = new ArrayList<>();

    public static Intent newItent(Context context, String authorAccountGuid, String schoolId, String patrolsId) {
        Intent intent = new Intent(context, AddSecurityCheckActivity.class);
        intent.putExtra("authorAccountGuid", authorAccountGuid);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("patrolsId", patrolsId);

        return intent;
    }

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_add_security_check;
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(getString(R.string.title_add_security_check));
        edTitle = (EditText) getViewById(R.id.ed_title);
        edExplain = (EditText) getViewById(R.id.ed_explain);
        mBGASortableNinePhotoLayout = (BGASortableNinePhotoLayout) getViewById(R.id.sortable_nine_phone_layout);
    }

    @Override
    protected void setListener() {
        getViewById(R.id.ff_dispose).setOnClickListener(this);
        mBGASortableNinePhotoLayout.setDelegate(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mUser = ((App) AppManager.getApp()).cacheUser;
        schoolId = getIntent().getStringExtra("schoolId");
        authorAccountGuid = getIntent().getStringExtra("authorAccountGuid");
        patrolsId = getIntent().getStringExtra("patrolsId");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            ArrayList<String> selectedImages = PhotoPickerActivity.getSelectedImages(data);
            mBGASortableNinePhotoLayout.setData(selectedImages);
            filterList(selectedImages);
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            ArrayList<String> selectedImages = PhotoPickerActivity.getSelectedImages(data);
            mBGASortableNinePhotoLayout.setData(selectedImages);
            filterList(selectedImages);
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List perms) {
        choicePhotoWrapper();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .build()
                    .show();
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
            mSwipeBackHelper.forward(PhotoPickerActivity.newIntent(this, StorageUtil.getImageDir(), MAX_PHOTO_COUNT, mBGASortableNinePhotoLayout.getData(),
                    getString(R.string.confirm)), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.image_selection_requires_the_following_permissions_photo_on_the_access_device),
                    REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }


    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mBGASortableNinePhotoLayout.removeItem(position);
        if (model.startsWith("http")) {
            pictureModels.remove(position);
        }
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mSwipeBackHelper.forward(PhotoPickerPreviewActivity.newIntent(this, MAX_PHOTO_COUNT, models, models, position, getString(R.string.confirm), false), REQUEST_CODE_PHOTO_PREVIEW);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ff_dispose:
                attemptPost();
                break;
        }
    }

    public void attemptPost() {
        showLoadingDialog();
        StringBuffer sb = new StringBuffer();
        Observable.from(pictureStrList)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just(s)
                                .compose(RxTransforemerUtisl.compressPicture())//压缩图片
                                .compose(RxTransforemerUtisl.updatePicture());//上传图片
                    }
                }).all(s -> {
                    sb.append(s).append(",");
                    return s != null && !s.equals("");
                }).doOnError(throwable -> {
            dismissLoadingDialog();
            ToastUtil.show(getString(R.string.str_update_image_fial));
        }).flatMap(new Func1<Boolean, Observable<String>>() {
            @Override
            public Observable<String> call(Boolean aBoolean) {
                return Observable.just(sb.toString().substring(0,sb.toString().length()-1));
            }
        }).flatMap(new Func1<String, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(String s) {
                return  RestDataSoure.newInstance().addSecurityTask(authorAccountGuid,
                        mUser.accountGuid,
                        schoolId,
                        edTitle.getText().toString(),
                        patrolsId, edExplain.getText().toString(),s).
                        compose(RxUtil.applySchedulersJobUI())
                        .compose(new RESTResultTransformBoolean());
            }
        }).subscribe(aBoolean -> {
            dismissLoadingDialog();
            ToastUtil.show(getString(R.string.post_successful));
        }, throwable -> {
            dismissLoadingDialog();
            ToastUtil.show(getString(R.string.post_fail));
        });
    }
}
