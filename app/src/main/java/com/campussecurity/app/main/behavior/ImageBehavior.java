package com.campussecurity.app.main.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.campussecurity.app.R;
import com.hao.common.widget.titlebar.TitleBar;

/**
 * @Package com.behaviordemo
 * @作 用:ImageView 行为
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年01月07日  16:38
 */


public class ImageBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private Context mContext;
    private float mCustomFinalHeight;//头像最终的大小
    private float mCustomPositionX;

    private float mStartAvatarX;
    private float mStartAvatarY;

    //最终头像的Y
    private float mFinalAvatarY;
    private float mFinalAvatarX;

    private float mStartToolbarPositionY;
    private int mStartHeight;


    public ImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageBehavior);
        mCustomFinalHeight = array.getDimension(R.styleable.ImageBehavior_imageFinalHeight, 0);
        mCustomPositionX = array.getDimension(R.styleable.ImageBehavior_customPositionX, 0);
        array.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof TitleBar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        init(child, dependency);
        //计算toolbar从开始移动到最后的百分比
        float percent = (1 - (dependency.getY() / mStartToolbarPositionY));//toolbar移动的百分比

        float X = mStartAvatarX - (mStartAvatarX - mFinalAvatarX) * percent;
        float Y = mStartAvatarY - (mStartAvatarY - mFinalAvatarY) * percent;
        child.setY(Y);
        child.setX(X);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.width = (int) (mStartHeight - (mStartHeight - mCustomFinalHeight) * percent);
        layoutParams.height = (int) (mStartHeight - (mStartHeight - mCustomFinalHeight) * percent);
        child.setLayoutParams(layoutParams);
        return true;
    }

    private void init(ImageView child, View dependency) {
        if (mStartAvatarX == 0) {
            mStartAvatarX = child.getX();
        }
        if (mStartAvatarY == 0) {
            mStartAvatarY = dependency.getY();
        }
        if (mFinalAvatarY == 0) {
            mFinalAvatarY = dependency.getHeight() / 2 - mCustomFinalHeight / 2;
        }
        if (mFinalAvatarX == 0) {
            mFinalAvatarX = dependency.getWidth() / 2 + mCustomPositionX;
        }

        if (mStartToolbarPositionY == 0) {
            mStartToolbarPositionY = dependency.getY();
        }

        if (mStartHeight == 0) {
            mStartHeight = child.getHeight();
        }

    }
}
