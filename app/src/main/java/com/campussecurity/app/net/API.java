package com.campussecurity.app.net;

import com.campussecurity.app.login.model.User;
import com.campussecurity.app.patrol.model.PatrolTask;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.campussecurity.app.securitycheck.ProcessorModel;
import com.campussecurity.app.securitycheck.SecurityCheckDetailModel;
import com.campussecurity.app.securitycheck.SecurityTaskModel;
import com.hao.common.net.result.RESTResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Observable<RESTResult> scanCard(@Field("accountGuid") String accountGuid, @Field("patrolTaskId") int patrolTaskId,
                                    @Field("patrolTaskItemId") int patrolTaskItemId, @Field("code") String code);

    //安全任务详细
    @GET("/api/SecurityTask")
    Observable<RESTResult<SecurityCheckDetailModel>> getSecurityTaskContent(@Query("securityTaskId")String securityTaskId);

    //得到安全检查
    @GET("/api/SecurityTask")
    Observable<RESTResult<SecurityTaskModel>> getSecurityTaskList(@Query("accountGuid") String accountGui);



    //添加安全任务
    @FormUrlEncoded
    @POST("/api/SecurityTask")
    Observable<RESTResult> addSecurityTask(@Field("authorAccountGuid") String authorAccountGuid, @Field("accountGuid") String accountGuid,
                                           @Field("schoolId") String schoolId, @Field("name") String name, @Field("patrolsId")String patrolsId,
                                           @Field("description") String description,
                                           @Field("pictrueStr") String pictrueStr,@Field("createDate")String createDate);

    //得到安保人员
    @GET("/api/SecurityTaskSet")
    Observable<RESTResult<ProcessorModel>> getSecurityTaskSet(@Query("schoolId") String schoolId);

    //转签安全任务
    @FormUrlEncoded
    @POST("/api/SecurityTaskSet")
    Observable<RESTResult> setSecurityTaskSet(@Field("securityTaskId")String securityTaskId,@Field("accountGuid")String accountGuid);

    //开始安全任务
    @DELETE("/api/SecurityTaskSet")
    Observable<RESTResult> startSecurityTaskSet(@Query("securityTaskId")String securityTaskId);

    //完成安全任务
    @FormUrlEncoded
    @POST("/api/SecurityTaskSet")
    Observable<RESTResult> endSecurityTaskSet(@Field("securityTaskId") String securityTaskId,@Field("accountGuid") String accountGuid,
                                              @Field("reply") String reply,@Field("pictrueStr") String pictrueStr);


    //上传图片
    @POST("/api/Image")
    Observable<RESTResult<String>> updateImage(@Body RequestBody body);


    //删除安全任务图片
    @DELETE("/api/SecurityTaskItem")
    Observable<RESTResult> deleteSecurityTaskItem(@Query("securityTaskId")String securityTaskId);

    //提交安全任务图片
    @FormUrlEncoded
    @POST("/api/SecurityTaskItem")
    Observable<RESTResult> completeSecurityTaskItem(@Field("securityTaskId")String securityTaskId,@Field("picture")String picture);

    //修改密码
    @FormUrlEncoded
    @POST("/api/DutyTree")
    Observable<RESTResult> dutyTree(@Field("userName")String userName,@Field("oldPassword")String oldPassword,@Field("password")String password,@Field("code")String code);

    //得到版本
    @FormUrlEncoded
    @POST("/api/AppVersion")
    Observable<RESTResult> getVersion();


    //设置头像
    @FormUrlEncoded
    @PUT("/api/Image")
    Observable<RESTResult> updateUserImage(@Field("accountGuid") String accountGuid, @Field("avatar") String avatar);
}
