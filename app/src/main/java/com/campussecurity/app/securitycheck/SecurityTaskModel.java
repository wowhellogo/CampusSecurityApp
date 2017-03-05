package com.campussecurity.app.securitycheck;

import java.util.List;

/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/5 0005
 */

public class SecurityTaskModel {

    /**
     * pictrues : []
     * securityTaskId : 1
     * accountGuid : 77b3d076-05a4-4aa9-9ddb-69823a107e9f
     * authorAccountGuid : ef5e8277-3300-4446-af82-62fceddf0188
     * patrolsId : 0
     * schoolId : 1
     * checkTaskId : 0
     * monthlyCheckId : 4
     * author : 颜路平
     * name : 校内环境安全
     * description : 安全疏散通道是否畅通，安全出口有无指示标志
     * reply :
     * picture :
     * state : 0
     * hasPicture : false
     * startTime : 2017-03-03 07:56:36
     * endTime : 2017-03-04 07:56:40
     * createDate : 2017-03-03 07:56:44
     * updateDate : 2017-03-03 07:56:44
     */

    private int securityTaskId;
    private String accountGuid;
    private String authorAccountGuid;
    private int patrolsId;
    private int schoolId;
    private int checkTaskId;
    private int monthlyCheckId;
    private String author;
    private String name;
    private String description;
    private String reply;
    private String picture;
    private int state;
    private boolean hasPicture;
    private String startTime;
    private String endTime;
    private String createDate;
    private String updateDate;
    private List<String> pictrues;

    public int getSecurityTaskId() {
        return securityTaskId;
    }

    public void setSecurityTaskId(int securityTaskId) {
        this.securityTaskId = securityTaskId;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public void setAccountGuid(String accountGuid) {
        this.accountGuid = accountGuid;
    }

    public String getAuthorAccountGuid() {
        return authorAccountGuid;
    }

    public void setAuthorAccountGuid(String authorAccountGuid) {
        this.authorAccountGuid = authorAccountGuid;
    }

    public int getPatrolsId() {
        return patrolsId;
    }

    public void setPatrolsId(int patrolsId) {
        this.patrolsId = patrolsId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getCheckTaskId() {
        return checkTaskId;
    }

    public void setCheckTaskId(int checkTaskId) {
        this.checkTaskId = checkTaskId;
    }

    public int getMonthlyCheckId() {
        return monthlyCheckId;
    }

    public void setMonthlyCheckId(int monthlyCheckId) {
        this.monthlyCheckId = monthlyCheckId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(boolean hasPicture) {
        this.hasPicture = hasPicture;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getPictrues() {
        return pictrues;
    }

    public void setPictrues(List<String> pictrues) {
        this.pictrues = pictrues;
    }
}
