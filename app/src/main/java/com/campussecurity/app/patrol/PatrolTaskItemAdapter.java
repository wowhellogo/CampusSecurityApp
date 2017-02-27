package com.campussecurity.app.patrol;

import android.support.v7.widget.RecyclerView;

import com.campussecurity.app.R;
import com.campussecurity.app.patrol.model.PatrolTaskItemBean;
import com.hao.common.adapter.BaseRecyclerViewAdapter;
import com.hao.common.adapter.BaseViewHolderHelper;

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

    }
}
