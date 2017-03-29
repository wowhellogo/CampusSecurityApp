package com.campussecurity.app.message;

import android.support.v7.widget.RecyclerView;

import com.campussecurity.app.R;
import com.hao.common.adapter.BaseRecyclerViewAdapter;
import com.hao.common.adapter.BaseViewHolderHelper;

/**
 * @Package com.campussecurity.app.message
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/18 0018
 */

public class AppPushMessageAdapter extends BaseRecyclerViewAdapter<AppPushMessage> {
    public AppPushMessageAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
    }

    @Override
    protected void fillData(BaseViewHolderHelper helper, int position, AppPushMessage model) {
        helper.setText(R.id.tvName, model.getTitle());
        helper.setText(R.id.tvDate, model.getCreateDate());
        helper.setText(R.id.tv_description, model.getDescription());
        helper.setText(R.id.tvRead, model.isIsRead() ? mContext.getString(R.string.str_read) : mContext.getString(R.string.un_read));
        helper.setTextColor(R.id.tvRead, model.isIsRead() ? mContext.getResources().getColor(R.color.colorPatrolGreen)
                : mContext.getResources().getColor(R.color.colorPatrolRed));
    }
}
