package com.campussecurity.app.securitycheck;

import android.support.v7.widget.RecyclerView;

import com.campussecurity.app.R;
import com.hao.common.adapter.BaseRecyclerViewAdapter;
import com.hao.common.adapter.BaseViewHolderHelper;

/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/5 0005
 */

public class SecurityCheckAdapter extends BaseRecyclerViewAdapter<SecurityTaskModel> {
    public SecurityCheckAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
    }

    @Override
    protected void fillData(BaseViewHolderHelper helper, int position, SecurityTaskModel model) {
        helper.setText(R.id.tvName, model.getAuthor() + "");
        helper.setText(R.id.tvDescription, model.getDescription());
        helper.displayImageByUrl(R.id.iamgeView,model.getPicture());
        switch (model.getState()) {
            case 0://未处理
                helper.setText(R.id.tvDate, model.getCreateDate());
                helper.setText(R.id.tvSate, R.string.state_untreated);
                break;
            case 1://处理中
                helper.setText(R.id.tvDate, model.getStartTime());
                helper.setText(R.id.tvSate, R.string.state_processing);
                break;
            case 2://已处理
                helper.setText(R.id.tvDate, model.getEndTime());
                helper.setText(R.id.tvSate, R.string.state_processed);
                break;
        }

    }
}
