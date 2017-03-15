package com.pw;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.campussecurity.app.R;
import com.hao.common.utils.ViewUtils;
import com.picker.model.ImageFolderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.daoda.aijiacommunity.common.pw
 * @作 用:图片选择界面中的图片目录选择窗口
 * @创 建 人: linguoding
 * @日 期: 2016/3/29 0029
 */
public class PhotoFolderPw extends BasePopupWindow implements AdapterView.OnItemClickListener {
    public static final int ANIM_DURATION = 300;
    private LinearLayout mRootLl;
    private ListView mContentLv;
    private FolderAdapter mFolderAdapter;
    private Callback mCallback;
    private int mCurrentPosition;

    public PhotoFolderPw(Activity activity, View anchorView, Callback callback) {
        super(activity, R.layout.pw_photo_folder, anchorView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mCallback = callback;
    }

    @Override
    protected void initView() {
        mRootLl = getViewById(R.id.root_ll);
        mContentLv = getViewById(R.id.content_lv);
    }

    @Override
    protected void setListener() {
        mRootLl.setOnClickListener(this);
        mContentLv.setOnItemClickListener(this);
    }

    @Override
    protected void processLogic() {
        setAnimationStyle(android.R.style.Animation);
        setBackgroundDrawable(new ColorDrawable(0x90000000));

        mFolderAdapter = new FolderAdapter();
        mContentLv.setAdapter(mFolderAdapter);
    }

    /**
     * 设置目录数据集合
     *
     * @param datas
     */
    public void setDatas(List<ImageFolderModel> datas) {
        mFolderAdapter.setDatas(datas);
    }

    @Override
    public void show() {
        showAsDropDown(mAnchorView);
        ViewCompat.animate(mContentLv).translationY(-mWindowRootView.getHeight()).setDuration(0).start();
        ViewCompat.animate(mContentLv).translationY(0).setDuration(ANIM_DURATION).start();
        ViewCompat.animate(mRootLl).alpha(0).setDuration(0).start();
        ViewCompat.animate(mRootLl).alpha(1).setDuration(ANIM_DURATION).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCallback != null && mCurrentPosition != position) {
            mCallback.onSelectedFolder(position);
        }
        mCurrentPosition = position;
        dismiss();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.root_ll) {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        ViewCompat.animate(mContentLv).translationY(-mWindowRootView.getHeight()).setDuration(ANIM_DURATION).start();
        ViewCompat.animate(mRootLl).alpha(1).setDuration(0).start();
        ViewCompat.animate(mRootLl).alpha(0).setDuration(ANIM_DURATION).start();

        if (mCallback != null) {
            mCallback.executeDismissAnim();
        }

        mContentLv.postDelayed(new Runnable() {
            @Override
            public void run() {
                PhotoFolderPw.super.dismiss();
            }
        }, ANIM_DURATION);
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    private class FolderAdapter extends BaseAdapter {
        private List<ImageFolderModel> mDatas;
        private int mImageSize;

        public FolderAdapter() {
            mDatas = new ArrayList<>();
            mImageSize = ViewUtils.getScreenWidth(mActivity) / 4;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public ImageFolderModel getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FolderViewHolder folderViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_folder, parent, false);
                folderViewHolder = new FolderViewHolder();
                folderViewHolder.photoIv = (ImageView) convertView.findViewById(R.id.photo_iv);
                folderViewHolder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
                folderViewHolder.countTv = (TextView) convertView.findViewById(R.id.count_tv);
                convertView.setTag(folderViewHolder);
            } else {
                folderViewHolder = (FolderViewHolder) convertView.getTag();
            }

            ImageFolderModel imageFolderModel = getItem(position);
            folderViewHolder.nameTv.setText(imageFolderModel.name);
            folderViewHolder.countTv.setText(String.valueOf(imageFolderModel.getCount()));
            Glide.with(mActivity).load("file://" + imageFolderModel.coverPath).override(mImageSize,mImageSize).dontAnimate().placeholder(R.mipmap.ic_defualt_loading).into(folderViewHolder.photoIv);
            /*ImageLoader.getInstance().displayImage("file://" + imageFolderModel.coverPath, new ImageViewAware(folderViewHolder.photoIv), mDisplayImageOptions, mImageSize, null, null);
*/
            return convertView;
        }

        public void setDatas(List<ImageFolderModel> datas) {
            if (datas != null) {
                mDatas = datas;
            } else {
                mDatas.clear();
            }
            notifyDataSetChanged();
        }
    }

    private class FolderViewHolder {
        public ImageView photoIv;
        public TextView nameTv;
        public TextView countTv;
    }

    public interface Callback {
        void onSelectedFolder(int position);

        void executeDismissAnim();
    }
}