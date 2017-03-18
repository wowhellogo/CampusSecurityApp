package com.campussecurity.app.main.model;

import android.support.v4.app.Fragment;

/**
 * @Package com.daoda.aijiacommunity.main.model
 * @作用：Tab加Fragment的模型
 * @作者：linguoding
 * @日期:2016/4/20 10:40
 */
public class TabFragmentModel {
    private String name;
    private Fragment fragment;
    private int icon;

    public TabFragmentModel(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public TabFragmentModel(String name, Fragment fragment, int icon) {
        this.name = name;
        this.fragment = fragment;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
