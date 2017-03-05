package com.campussecurity.app.net;

import com.campussecurity.app.BuildConfig;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.campussecurity.app.securitycheck.SecurityTaskModel;
import com.hao.common.net.AbRestNetDataSource;
import com.hao.common.net.result.RESTResult;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 类名称：${type_name}
 * 类描述：
 * 创建人：linguoding
 * 创建时间：${date} ${time}
 */
public class RestDataSoure extends AbRestNetDataSource {
    private final static RestDataSoure INSTANCE = new RestDataSoure();
    private API api;

    public static RestDataSoure newInstance() {
        return INSTANCE;
    }

    @Override
    public void newRequest() {
        retrofit = new Retrofit.Builder().baseUrl(BuildConfig.DEBUG ? API.DEBUG_URL : API.BASE_URL).addConverterFactory(GsonConverterFactory.create())//使用Gson作为数据转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//使用RxJava作为回调适配器
                .client(okHttpClient).build();
        api = retrofit.create(API.class);
    }

    public Observable<RESTResult<User>> login(String userName, String password) {
        return api.login(userName, password);
    }

    //我的巡逻
    public Observable<RESTResult<PatrolTask>> getPatrolTask(String accountGuid) {
        return api.getPatrolTask(accountGuid);
    }

    //巡逻详细
    public Observable<RESTResult<PatrolTaskDetails>> getPatrolTaskDetails(int patrolTaskId) {
        return api.getPatrolTaskDetails(patrolTaskId);
    }

    //刷卡
    public Observable<RESTResult> scanCard(String accountGuid, int patrolTaskId, int patrolTaskItemId, String code) {
        return api.scanCard(accountGuid, patrolTaskId, patrolTaskItemId, code);
    }

    //得到安全检查列表
    public Observable<RESTResult<SecurityTaskModel>> getSecurityTaskList(String accountGuid) {
        return api.getSecurityTaskList(accountGuid);
    }


}
