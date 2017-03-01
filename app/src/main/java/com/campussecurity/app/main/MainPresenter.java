package com.campussecurity.app.main;

import android.os.Bundle;

import com.campussecurity.app.R;
import com.campussecurity.app.main.model.IconModel;
import com.campussecurity.app.message.SystemMessageActivity;
import com.campussecurity.app.patrol.PatrolListActivity;
import com.campussecurity.app.record.HistoryRecordActivity;
import com.campussecurity.app.rfidjni.MainRFIDActivity;
import com.campussecurity.app.securitycheck.SecurityCheckListActivity;
import com.hao.common.nucleus.presenter.RxPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

/**
 * @Package com.campussecurity.app.main
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月13日  17:17
 */


public class MainPresenter extends RxPresenter<MainActivity> {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        loadModule();
    }

    public void loadModule() {
        restartableFirst(1, new Func0<Observable<List<IconModel>>>() {

            @Override
            public Observable<List<IconModel>> call() {
                return Observable.just(getModuleList());
            }
        }, new Action2<MainActivity, List<IconModel>>() {
            @Override
            public void call(MainActivity mainActivity, List<IconModel> iconModels) {
                mainActivity.showModuleToUI(iconModels);
            }
        });
        start(1);
    }

    public List<IconModel> getModuleList() {
        List<IconModel> list = new ArrayList<>();
        list.add(new IconModel.Builder()
                .setName("巡逻任务")
                .setIcon(R.mipmap.ic_grid_patrol)
                .setIconBg(R.mipmap.ic_grid_patrol_bg)
                .setRemindCount(98)
                .setToActivity(PatrolListActivity.class)
                .builder()
        );
        list.add(new IconModel.Builder()
                .setName("安全检查")
                .setIcon(R.mipmap.ic_grid_safety_check)
                .setIconBg(R.mipmap.ic_grid_safety_check_bg)
                .setRemindCount(99)
                .setToActivity(SecurityCheckListActivity.class)
                .builder()
        );
        list.add(new IconModel.Builder()
                .setName("系统消息")
                .setIcon(R.mipmap.ic_grid_system_message)
                .setIconBg(R.mipmap.ic_grid_system_message_bg)
                .setToActivity(SystemMessageActivity.class)
                .builder()
        );

        list.add(new IconModel.Builder()
                .setName("历史纪录")
                .setIcon(R.mipmap.ic_grid_message_history)
                .setIconBg(R.mipmap.ic_grid_message_history_bg)
                .setToActivity(HistoryRecordActivity.class)
                .builder()
        );

        list.add(new IconModel.Builder()
                .setName("刷卡测试")
                .setIcon(R.mipmap.ic_grid_system_message)
                .setIconBg(R.mipmap.ic_grid_system_message_bg)
                .setToActivity(MainRFIDActivity.class)
                .builder()
        );
        return list;
    }

}
