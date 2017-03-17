package com.campussecurity.app.utils.receiver;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.campussecurity.app.App;
import com.campussecurity.app.login.model.User;
import com.google.gson.GsonBuilder;
import com.hao.common.manager.AppManager;
import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushInterface;

/**
 * @Package com.mk.fortpeace.app.main.view.receiver
 * @作 用:极光推送通知广播接收器
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2016年09月07日  11:12
 */

public class MkJPushMessageReceiver extends BroadcastReceiver {
    public final static String POSITION_MODEL="positionModel";
    private NotificationManager nm;
    private User mUserModel;
    GsonBuilder builder=new GsonBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        mUserModel= ((App)AppManager.getApp()).cacheUser;
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.e("JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String message=bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Logger.e("接受到推送下来的自定义消息" + message);
            if(message!=null&&!message.equals("")){
                /*RxBus.getDefault().send(new NotificationEvent());*/
                //NotificationHelper.showCommonNotification(context,notification);
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.e("接受到推送下来的通知");
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.e("用户点击打开了通知");

        } else {
            Logger.e("Unhandled intent - " + intent.getAction());
        }
    }


}
