package com.campussecurity.app.main.behavior;

/**
 * @Package com.behaviordemo
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年01月06日  11:18
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.campussecurity.app.R;
import com.hao.common.widget.CircleImageView;


@SuppressWarnings("unused")
public class AvatarImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private final static float MIN_AVATAR_PERCENTAGE_SIZE = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING = 80;

    private final static String TAG = "behavior";
    private Context mContext;

    private float mCustomFinalYPosition;
    private float mCustomStartXPosition;
    private float mCustomStartToolbarPosition;
    private float mCustomStartHeight;
    private float mCustomFinalHeight;

    private float mAvatarMaxSize;
    private float mFinalLeftAvatarPadding;
    private float mStartPosition;
    private int mStartXPosition;
    private float mStartToolbarPosition;
    private int mStartYPosition;
    private int mFinalYPosition;
    private int mStartHeight;
    private int mFinalXPosition;
    private float mChangeBehaviorPoint;

    public AvatarImageBehavior(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior);
            mCustomFinalYPosition = a.getDimension(R.styleable.AvatarImageBehavior_finalYPosition, 0);
            mCustomStartXPosition = a.getDimension(R.styleable.AvatarImageBehavior_startXPosition, 0);
            mCustomStartToolbarPosition = a.getDimension(R.styleable.AvatarImageBehavior_startToolbarPosition, 0);
            mCustomStartHeight = a.getDimension(R.styleable.AvatarImageBehavior_startHeight, 0);
            mCustomFinalHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalHeight, 0);

            a.recycle();
        }

        init();

        mFinalLeftAvatarPadding = context.getResources().getDimension(
                R.dimen.spacing_normal);
    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        maybeInitProperties(child, dependency);

        final int maxScrollDistance = (int) (mStartToolbarPosition);
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;//移动的比率
        Toolbar toolbar= (Toolbar) dependency;
        toolbar.setAlpha(1-expandedPercentageFactor);
        if (expandedPercentageFactor < mChangeBehaviorPoint) {//向左移动，大小改变
            float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;

            float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                    * heightFactor) + (child.getHeight() / 2);
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (child.getHeight() / 2);

            //头像的位置
            child.setX(mStartXPosition - distanceXToSubtract);
            child.setY(mStartYPosition - distanceYToSubtract);

            float heightToSubtract = ((mStartHeight - mCustomFinalHeight) * heightFactor);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            //头像的大小
            lp.width = (int) (mStartHeight - heightToSubtract);
            lp.height = (int) (mStartHeight - heightToSubtract);
            child.setLayoutParams(lp);
        } else {//向上移动
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (mStartHeight / 2);

            child.setX(mStartXPosition - child.getWidth() / 2);
            child.setY(mStartYPosition - distanceYToSubtract);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mStartHeight);
            lp.height = (int) (mStartHeight);
            child.setLayoutParams(lp);
        }
        return true;
    }

    private void maybeInitProperties(CircleImageView child, View dependency) {
        //初始化，第一次调用判断

        //X坐标点在
        if (mStartXPosition == 0)
            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));

        //Y开始坐标点依赖toolbar的Y坐标
        if (mStartYPosition == 0)
            mStartYPosition = (int) (dependency.getY());

        //
        if (mFinalXPosition == 0)
            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + ((int) mCustomFinalHeight / 2);

        //Y坐标点在toolbar中间
        if (mFinalYPosition == 0)
            mFinalYPosition = (dependency.getHeight() / 2);

        //头像控件开始的高度
        if (mStartHeight == 0)
            mStartHeight = child.getHeight();


        //Toolbar开始的位置
        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY();

        //改变行为的比率
        if (mChangeBehaviorPoint == 0) {
            mChangeBehaviorPoint = (child.getHeight() - mCustomFinalHeight) / (2f * (mStartYPosition - mFinalYPosition));
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
