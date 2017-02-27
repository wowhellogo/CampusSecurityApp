package com.campussecurity.app.patrol;

import android.support.v7.widget.RecyclerView;

import com.campussecurity.app.R;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.hao.common.adapter.BaseRecyclerViewAdapter;
import com.hao.common.adapter.BaseViewHolderHelper;

/**
 * @Package com.campussecurity.app.patrol
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月21日  16:20
 */


public class PatrolTaskAdapter extends BaseRecyclerViewAdapter<PatrolTask> {
    public PatrolTaskAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
    }

    @Override
    protected void fillData(BaseViewHolderHelper helper, int position, PatrolTask model) {
        helper.setText(R.id.tvName, model.getName());
        helper.setText(R.id.tvDate, mContext.getString(R.string.patrol_time) + model.getStartTime() + "-" + model.getEndTime());
        switch (model.getState()) {
            case 0:
                helper.setText(R.id.tvSate, R.string.pre_start_patrol_state);
                break;
            case 1:
                helper.setText(R.id.tvSate, R.string.start_patrol);
                break;
            case 2:
                helper.setText(R.id.tvSate, R.string.patrol_complete);
                break;
        }
        switch (model.getType()) {
            case 0://每日巡逻
                helper.setText(R.id.tvType, mContext.getString(R.string.str_day_patrol));
                helper.setTextColor(R.id.tvType, mContext.getResources().getColor(R.color.colorPatrolGreen));
                helper.setTextColor(R.id.tvSate, mContext.getResources().getColor(R.color.colorPatrolGreen));
                helper.setTextColor(R.id.tvDate, mContext.getResources().getColor(R.color.colorPatrolGreen));
                break;
            case 1://临时巡逻
                helper.setText(R.id.tvType, mContext.getString(R.string.str_temporary_patrol));
                helper.setTextColor(R.id.tvType, mContext.getResources().getColor(R.color.colorPatrolYellow));
                helper.setTextColor(R.id.tvSate, mContext.getResources().getColor(R.color.colorPatrolYellow));
                helper.setTextColor(R.id.tvDate, mContext.getResources().getColor(R.color.colorPatrolYellow));
                break;
            case 2://紧急巡逻
                helper.setText(R.id.tvType, mContext.getString(R.string.str_emergency_patrol));
                helper.setTextColor(R.id.tvType, mContext.getResources().getColor(R.color.colorPatrolRed));
                helper.setTextColor(R.id.tvSate, mContext.getResources().getColor(R.color.colorPatrolRed));
                helper.setTextColor(R.id.tvDate, mContext.getResources().getColor(R.color.colorPatrolRed));
                break;
        }
    }
}
