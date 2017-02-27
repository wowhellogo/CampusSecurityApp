package com.campussecurity.app.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @Package com.daoda.aijiacommunity.common.model
 * @作 用:整个应用图标模型类
 * @创 建 人: linguoding
 * @日 期: 2016/3/16
 */
public class IconModel implements Serializable {
    public int id;
    public String name;//名字
    public int icon;//图标
    public int iconBg;//背景
    public Class<?> toActivity;//打开的activity入口类
    public Remind isRemind;
    public int remindCount = 0;//消息数

    public static class Builder {
        private IconModel mIconModel;

        public Builder() {
            mIconModel = new IconModel();
        }

        public Builder setId(int id) {
            mIconModel.id = id;
            return this;
        }

        public Builder setName(String name) {
            mIconModel.name = name;
            return this;
        }

        public Builder setIcon(int icon) {
            mIconModel.icon = icon;
            return this;
        }

        public Builder setIconBg(int iconBg) {
            mIconModel.iconBg = iconBg;
            return this;
        }

        public Builder setToActivity(Class<?> toActivity) {
            mIconModel.toActivity = toActivity;
            return this;
        }

        public Builder setIsRemind(Remind isRemind) {
            mIconModel.isRemind = isRemind;
            return this;
        }

        public Builder setRemindCount(int remindCount) {
            mIconModel.remindCount = remindCount;
            return this;
        }

        public IconModel builder() {
            return mIconModel;
        }
    }

}
