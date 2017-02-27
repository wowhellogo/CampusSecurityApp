package com.campussecurity.app.net;

import com.campussecurity.app.login.model.User;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.hao.common.net.result.RESTResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 类名称：com.mk.fortpeace.app.net
 * 类描述：
 * 创建人：linguoding
 */
public interface API {
    String BASE_URL = "http://campussafety.chinacloudsites.cn/";//线上Ip
    //String DEBUG_URL = "http://192.168.1.11";
    String DEBUG_URL = "http://campussafety.chinacloudsites.cn/";//线下Ip

    //登录请求
    @FormUrlEncoded
    @POST("/api/login")
    Observable<RESTResult<User>> login(@Field("userName") String userName, @Field("password") String password);

    //我的巡逻
    @GET("/api/PatrolTask")
    Observable<RESTResult<PatrolTask>> getPatrolTask(@Query("accountGuid") String accountGuid);

    //巡逻详细
    @GET("/api/PatrolTask")
    Observable<RESTResult<PatrolTaskDetails>> getPatrolTaskDetails(@Query("patrolTaskId") int patrolTaskId);

    //刷卡
    @FormUrlEncoded
    @POST("/api/ScanCard")
    Observable<RESTResult> scanCard(@Field("accountGuid") String accountGuid, @Field("patrolTaskId") int patrolTaskId, @Field("patrolTaskItemId") int patrolTaskItemId, @Field("code") String code);

}
