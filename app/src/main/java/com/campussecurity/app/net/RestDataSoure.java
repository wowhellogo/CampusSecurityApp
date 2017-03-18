package com.campussecurity.app.net;

import com.campussecurity.app.BuildConfig;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.campussecurity.app.securitycheck.ProcessorModel;
import com.campussecurity.app.securitycheck.SecurityCheckDetailModel;
import com.campussecurity.app.securitycheck.SecurityTaskModel;
import com.hao.common.net.AbRestNetDataSource;
import com.hao.common.net.result.RESTResult;
import com.hao.common.utils.DateUtils;

import java.io.File;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public Observable<RESTResult> updateUserImage(String accountGuid, String avatar) {
        return api.updateUserImage(accountGuid, avatar);
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


    //添加安全任务
    public Observable<RESTResult> addSecurityTask(String authorAccountGuid, String accountGuid,
                                                  String schoolId, String name, String patrolsId, String description,
                                                  String pictrueStr) {
        return api.addSecurityTask(authorAccountGuid, accountGuid, schoolId, name, patrolsId, description, pictrueStr, DateUtils.getDate2MStr(new Date()));
    }

    //得到安保人员
    public Observable<RESTResult<ProcessorModel>> getSecurityTaskSet(String schoolId) {
        return api.getSecurityTaskSet(schoolId);
    }

    //转签安全任务
    public Observable<RESTResult> setSecurityTaskSet(String securityTaskId, String accountGuid) {
        return api.setSecurityTaskSet(securityTaskId, accountGuid);
    }

    //开始安全任务
    public Observable<RESTResult> startSecurityTaskSet(String securityTaskId) {
        return api.startSecurityTaskSet(securityTaskId);
    }

    //完成安全任务
    public Observable<RESTResult> endSecurityTaskSet(String securityTaskId, String accountGuid,
                                                     String reply, String pictrueStr) {
        return api.endSecurityTaskSet(securityTaskId, accountGuid, reply, pictrueStr);
    }

    //安全任务详细
    public Observable<RESTResult<SecurityCheckDetailModel>> getSecurityTaskContent(String securityTaskId) {
        return api.getSecurityTaskContent(securityTaskId);
    }


    public Observable<RESTResult<String>> updateImage(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        return api.updateImage(requestBody);
    }


    //删除安全任务图片
    public Observable<RESTResult> deleteSecurityTaskItem(String securityTaskId) {
        return deleteSecurityTaskItem(securityTaskId);
    }

    //提交安全任务图片
    public Observable<RESTResult> completeSecurityTaskItem(String securityTaskId, String picture) {
        return api.completeSecurityTaskItem(securityTaskId, picture);
    }

    //修改密码
    public Observable<RESTResult> dutyTree(String userName, String oldPassword, String password, String code) {
        return api.dutyTree(userName, oldPassword, password, code);
    }

    //得到版本
    public Observable<RESTResult> getVersion() {
        return api.getVersion();
    }


    //我的巡逻纪录
    public Observable<RESTResult<PatrolTask>> getPatrolTaskRecord(String accountGuid, int startIndex,
                                                                 int pageSize, int state, String startDate, String endDate) {
        return api.getPatrolTaskRecord(accountGuid, startIndex, pageSize, state, startDate, endDate);
    }

    //我的安全任务历史记录
    Observable<RESTResult<SecurityTaskModel>> getSecurityTaskRecord(String accountGuid, int startIndex,
                                                                    int pageSize, int state,
                                                                    String startDate, String endDate) {
        return api.getSecurityTaskRecord(accountGuid, startIndex, pageSize, state, startDate, endDate);
    }

    //得到推送纪录
    Observable<RESTResult> getAppPushRecord(String accountGuid, int startIndex,
                                            int pageSize, int type,
                                            String startDate, String endDate) {
        return api.getAppPushRecord(accountGuid, startIndex, pageSize, type, startDate, endDate);
    }

    //读取推送
    Observable<RESTResult> readAppPushRecord(int appPushId) {
        return api.readAppPushRecord(appPushId);
    }


}
