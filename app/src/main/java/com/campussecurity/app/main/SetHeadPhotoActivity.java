package com.campussecurity.app.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.LoginActivity;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.main.model.UpLoadImageEvent;
import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.utils.RxTransforemerUtisl;
import com.hao.common.base.BaseActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.image.ImageLoader;
import com.hao.common.manager.AppManager;
import com.hao.common.rx.RESTResultTransformBoolean;
import com.hao.common.rx.RxBus;
import com.hao.common.rx.RxUtil;
import com.hao.common.utils.StorageUtil;
import com.hao.common.utils.ToastUtil;
import com.hao.common.widget.CircleImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.functions.Func1;


public class SetHeadPhotoActivity extends BaseActivity implements View.OnClickListener {
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    CircleImageView mImIcon;
    private User mUser;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_set_head_photo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(getString(R.string.upload_picture));
        mUser = ((App) AppManager.getApp()).cacheUser;
        mImIcon = (CircleImageView) getViewById(R.id.im_icon);
        ImageLoader.getInstance().displayCricleImage(this, mUser.avatar, mImIcon);
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }

    @Override
    protected void setListener() {
        getViewById(R.id.btn_album).setOnClickListener(this);
        getViewById(R.id.btn_photo).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_album:// 从相册获取图片
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_UNSPECIFIED);
                mSwipeBackHelper.forward(intent,PHOTO_ZOOM);
                break;
            case R.id.btn_photo:// 从拍照获取图片
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(StorageUtil.getCacheDir() + "/temp.jpg")));
                mSwipeBackHelper.forward(intent1, PHOTO_GRAPH);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        // 拍照
        File picture = null;
        if (requestCode == PHOTO_GRAPH) {
            // 设置文件保存路径
            picture = new File(StorageUtil.getCacheDir() + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTO_RESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                String fileName = mUser.accountGuid + ".jpg";
                String filePath = StorageUtil.getImageDir() + File.separator + fileName;
                picture = new File(filePath);
                if (picture.exists()) {
                    picture.delete();
                } else {
                    try {
                        picture.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                            picture));
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);// (0-100)压缩文件
                    bos.flush();
                    bos.close();
                    mImIcon.setImageBitmap(photo); // 把图片显示在ImageView控件上
                    upLoadImage(picture.getAbsolutePath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upLoadImage(String path) {
        showLoadingDialog();
        StringBuffer url=new StringBuffer();
        Observable.just(path)
                .compose(RxTransforemerUtisl.compressPicture())//压缩图片
                .compose(RxTransforemerUtisl.updatePicture())//上传图片
                .all(s -> {
                    url.append(s);
                    return s!=null&&!s.equals("");
                })
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean b) {
                        return RestDataSoure.newInstance().updateUserImage(mUser.accountGuid, url.toString())
                                .compose(RxUtil.applySchedulersJobUI())
                                .compose(new RESTResultTransformBoolean());
                    }
                }).subscribe(aBoolean -> {
            dismissLoadingDialog();
            ToastUtil.show(getString(R.string.post_successful));
            RxBus.send(new UpLoadImageEvent(url.toString()));
        }, throwable -> {
            dismissLoadingDialog();
            ToastUtil.show(getString(R.string.post_fail));
        });
    }

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");// 调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");// 进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }


}
