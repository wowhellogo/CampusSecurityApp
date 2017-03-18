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
     * appPushId : 1
     * schoolId : 1
     * accountGuid : 77b3d076-05a4-4aa9-9ddb-69823a107e9f
     * title : 推送标题
     * description : 描述
     * data : 数据JSON
     * isRead : true
     * type : 0
     * createDate : 2017-02-17 17:36:15
     */

    private int appPushId;
    private int schoolId;
    private String accountGuid;
    private String title;
    private String description;
    private String data;
    private String isRead;
    private int type;
    private String createDate;

    public void setAppPushId(int appPushId) {
        this.appPushId = appPushId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setAccountGuid(String accountGuid) {
        this.accountGuid = accountGuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getAppPushId() {
        return appPushId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getData() {
        return data;
    }

    public String getIsRead() {
        return isRead;
    }

    public int getType() {
        return type;
    }

    public String getCreateDate() {
        return createDate;
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
                ", isRead='" + isRead + '\'' +
                ", type=" + type +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
