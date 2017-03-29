package com.campussecurity.app.record;

import android.content.Context;
import android.os.Bundle;

import com.campussecurity.app.R;
import com.campussecurity.app.main.model.TabFragmentModel;
import com.hao.common.nucleus.presenter.RxPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @Package com.campussecurity.app.message
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年03月18日  15:50
 */


public class HistoryRecordPresenter extends RxPresenter<HistoryRecordActivity> {
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void loadTabFragmentModelList(Context context){
        restartableFirst(1, () -> {
            List<TabFragmentModel> list = new ArrayList<>();
            list.add(new TabFragmentModel(context.getString(R.string.fragment_security_check), SecurityCheckListFragment.instantiate(context
                    , SecurityCheckListFragment.class.getName())));
            list.add(new TabFragmentModel(context.getString(R.string.fragment_patrol_record), PatrolListFragment.instantiate(context
                    , PatrolListFragment.class.getName())));
            return Observable.just(list);
        }, HistoryRecordActivity::setTabViewToUI);
        start(1);
    }
}
