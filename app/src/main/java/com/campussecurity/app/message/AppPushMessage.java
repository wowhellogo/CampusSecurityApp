package com.campussecurity.app.message;

import java.io.Serializable;

/**
 * @Package com.campussecurity.app.message
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年03月18日  14:58
 */


public class AppPushMessage implements Serializable {

    /**
     * appPushId : 34
     * schoolId : 0
     * accountGuid : 77b3d076-05a4-4aa9-9ddb-69823a107e9f
     * title : 您收到安全任务:周边环境安全
     * description : 开始时间：2017-03-17 22:40结束时间：2017-03-23 22:41/r/n周边是否有有毒、有害、易燃、易爆或其他危险品
     * data : {"securityTaskId":42,"accountGuid":"77b3d076-05a4-4aa9-9ddb-69823a107e9f","authorAccountGuid":"ef5e8277-3300-4446-af82-62fceddf0188","patrolsId":0,"schoolId":1,"checkTaskId":0,"monthlyCheckId":1,"author":"颜路平","name":"周边环境安全","description":"周边是否有有毒、有害、易燃、易爆或其他危险品","reply":"","picture":"","state":0,"hasPicture":false,"startTime":"2017-03-17 10:40:56","endTime":"2017-03-23 10:41:00","createDate":"2017-03-18 10:41:04","updateDate":"2017-03-18 10:41:04"}
     * type : 0
     * isRead : false
     * createDate : 2017-03-18 10:41:04
     */

    private int appPushId;
    private int schoolId;
    private String accountGuid;
    private String title;
    private String description;
    private String data;
    private int type;
    private boolean isRead;
    private String createDate;

    public int getAppPushId() {
        return appPushId;
    }

    public void setAppPushId(int appPushId) {
        this.appPushId = appPushId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public void setAccountGuid(String accountGuid) {
        this.accountGuid = accountGuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "AppPushMessage{" +
                "appPushId=" + appPushId +
                ", schoolId=" + schoolId +
                ", accountGuid='" + accountGuid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", data='" + data + '\'' +
                ", type=" + type +
                ", isRead=" + isRead +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
