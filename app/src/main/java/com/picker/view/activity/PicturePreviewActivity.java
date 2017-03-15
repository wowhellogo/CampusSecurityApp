package com.picker.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.campussecurity.app.R;
import com.hao.common.base.BaseActivity;
import com.hao.common.utils.StatusBarUtil;

import uk.co.senab.photoview.PhotoView;


/**
 * 单张图片预览界面
 */
public class PicturePreviewActivity extends BaseActivity {
    PhotoView photoIv;
    private String path;
    public static final String PATH = "path";

    public static Intent newIntent(Context context, String path) {
        Intent intent = new Intent(context, PicturePreviewActivity.class);
        intent.putExtra(PATH, path);
        return intent;
    }


    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_picture_preview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (getIntent().getStringExtra(PATH) != null) {
            path = getIntent().getStringExtra(PATH);
        }
        setContentView(R.layout.activity_picture_preview);
        photoIv = (PhotoView) findViewById(R.id.photo_iv);
        Glide.with(this).load(path).dontAnimate().placeholder(R.mipmap.ic_defualt_loading).into(photoIv);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorForSwipeBack(this, getResources().getColor(R.color.photo_title_bg));
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onBackPressed() {
        mSwipeBackHelper.backward();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
