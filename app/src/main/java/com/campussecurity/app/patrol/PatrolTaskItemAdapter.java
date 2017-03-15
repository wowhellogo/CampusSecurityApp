package com.campussecurity.app.patrol;

import android.support.v7.widget.RecyclerView;

import com.campussecurity.app.R;
import com.campussecurity.app.patrol.model.PatrolTaskItemBean;
import com.daimajia.swipe.SwipeLayout;
import com.hao.common.adapter.BaseRecyclerViewAdapter;
import com.hao.common.adapter.BaseViewHolderHelper;
import com.hao.common.utils.CalendarUtil;
import com.hao.common.utils.DateUtils;

import java.util.Date;

/**
 * @Package com.campussecurity.app.patrol
 * @作 用:巡检点Adapter
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/2/28 0028
 */

public class PatrolTaskItemAdapter extends BaseRecyclerViewAdapter<PatrolTaskItemBean> {


    public PatrolTaskItemAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return R.layout.item_start_patrol_content;
        } else {
            return R.layout.item_patrol_content;
        }
    }

    @Override
    protected void fillData(BaseViewHolderHelper helper, int position, PatrolTaskItemBean model) {
        ((SwipeLayout) helper.getView(R.id.swipe_layout)).close();
        Date date = null;
        if (model.getRecord() != null) {
            date = DateUtils.getStr2LDate(model.getRecord().getCreateDate());
        }
        if(model.mProcessorModel!=null){
            helper.setText(R.id.tv_operation_manageer, model.mProcessorModel.getName());
        }

        if (position == 0) {
            helper.setText(R.id.tvName, model.getName() + mContext.getString(R.string.patrol_start));
            if (date != null)
                helper.setText(R.id.tv_time_state, String.format("%s 开始巡逻了", CalendarUtil.formatHourMinute(date)));
        } else if (position == (getItemCount() - 1)) {
            helper.setText(R.id.tvName, model.getName() + mContext.getString(R.string.end_patrol));
            if (date != null)
                helper.setText(R.id.tv_time_state, String.format("%s 巡逻结束了", CalendarUtil.formatHourMinute(date)));
        } else {
            helper.setText(R.id.tvName, model.getName() + "");
            if (date != null)
                helper.setText(R.id.tv_time_state, String.format("%s 已经到 %s", CalendarUtil.formatHourMinute(date), model.getName()));
        }
        if (!model.isIsRecord()) {
            helper.setImageResource(R.id.im_flag, R.mipmap.ic_inactive_point);
        } else {
            helper.setImageResource(R.id.im_flag, R.mipmap.ic_active_point);
        }
        if (position == 0)
            helper.setImageResource(R.id.im_flag, R.mipmap.ic_active_point);
        helper.setItemChildClickListener(R.id.tv_operation);
        helper.setItemChildClickListener(R.id.root_layout);
        helper.setItemChildClickListener(R.id.tv_operation_manageer);

    }


    //刷新
    public void setRecord(int position) {
        getItem(position).setIsRecord(true);
        notifyDataSetChangedWrapper();
    }
}
