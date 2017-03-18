package com.campussecurity.app.record;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.campussecurity.app.R;
import com.campussecurity.app.main.model.TabFragmentModel;
import com.hao.common.adapter.AppFragmentPagerAdapter;
import com.hao.common.base.BaseActivity;
import com.hao.common.nucleus.factory.RequiresPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史消息
 */
@RequiresPresenter(HistoryRecordPresenter.class)
public class HistoryRecordActivity extends BaseActivity<HistoryRecordPresenter> {
    private ViewPager vp;
    private TabLayout tab;

    AppFragmentPagerAdapter<TabFragmentModel> mAppFragmentPagerAdapter;
    List<TabFragmentModel> mTabFragmentModel = new ArrayList<>();

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_history_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tab = getViewById(R.id.tab);
        vp = getViewById(R.id.vp);
    }

    public void setTabView() {
        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab tabitem = tab.getTabAt(i);
            tabitem.setCustomView(getTabView(i));
        }
    }

    public View getTabView(int position) {
        TabFragmentModel tabModel = mTabFragmentModel.get(position);
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_tab_view, null);
        textView.setText(tabModel.getName());
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, tabModel.getIcon(), 0, 0);
        return textView;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getPresenter().loadTabFragmentModelList(this);
    }

    public void setTabViewToUI(List<TabFragmentModel> tabFragmentModels) {
        mTabFragmentModel = tabFragmentModels;
        mAppFragmentPagerAdapter = new AppFragmentPagerAdapter<TabFragmentModel>(getSupportFragmentManager(), mTabFragmentModel) {
            @Override
            public Fragment createItem(int position) {
                return data.get(position).getFragment();
            }
        };
        vp.setAdapter(mAppFragmentPagerAdapter);
        tab.setupWithViewPager(vp);
        vp.setOffscreenPageLimit(tab.getTabCount() - 1);
        setTabView();
    }

}
