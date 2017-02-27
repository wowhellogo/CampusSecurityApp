package com.campussecurity.app.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.campussecurity.app.R;
import com.campussecurity.app.main.model.IconModel;
import com.hao.common.adapter.BaseAdapterViewAdapter;
import com.hao.common.adapter.BaseBindingRecyclerViewAdapter;
import com.hao.common.adapter.BaseRecyclerViewAdapter;
import com.hao.common.adapter.BaseViewHolderHelper;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;


/**
 * @Package com.campussecurity.app.main
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月13日  17:51
 */


public class IconModelAdapter extends BaseRecyclerViewAdapter<IconModel> {


    public IconModelAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
    }

    @Override
    protected void fillData(BaseViewHolderHelper helper, int position, IconModel model) {
        helper.setText(R.id.tvTitle, model.name);
        helper.getTextView(R.id.tvTitle).setCompoundDrawablesRelativeWithIntrinsicBounds(0, model.icon, 0, 0);
        helper.setImageResource(R.id.im_icon_bg, model.iconBg);
        BGABadgeImageView bgaBadgeImageView = helper.getView(R.id.im_icon_bg);

        if (model.remindCount > 0) {
            if (model.remindCount >= 99) {
                bgaBadgeImageView.showTextBadge("99+");
            } else {
                bgaBadgeImageView.showTextBadge("" + model.remindCount);
            }
        } else {
            bgaBadgeImageView.hiddenBadge();
        }
    }


}
