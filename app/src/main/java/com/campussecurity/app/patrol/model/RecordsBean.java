package com.campussecurity.app.patrol.model;

/**
 * @Package com.campussecurity.app.patrol.model
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月21日  11:53
 */


public class RecordsBean {

    /**
     * patrolItemRecordId : 2
     * accountGuid : 77b3d076-05a4-4aa9-9ddb-69823a107e9f
     * patrolRecordId : 2
     * patrolTaskItemId : 15
     * patrolTaskId : 4
     * createDate : 2017-03-11 05:24:30
     */

    private int patrolItemRecordId;
    private String accountGuid;
    private int patrolRecordId;
    private int patrolTaskItemId;
    private int patrolTaskId;
    private String createDate;

    public int getPatrolItemRecordId() {
        return patrolItemRecordId;
    }

    public void setPatrolItemRecordId(int patrolItemRecordId) {
        this.patrolItemRecordId = patrolItemRecordId;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public void setAccountGuid(String accountGuid) {
        this.accountGuid = accountGuid;
    }

    public int getPatrolRecordId() {
        return patrolRecordId;
    }

    public void setPatrolRecordId(int patrolRecordId) {
        this.patrolRecordId = patrolRecordId;
    }

    public int getPatrolTaskItemId() {
        return patrolTaskItemId;
    }

    public void setPatrolTaskItemId(int patrolTaskItemId) {
        this.patrolTaskItemId = patrolTaskItemId;
    }

    public int getPatrolTaskId() {
        return patrolTaskId;
    }

    public void setPatrolTaskId(int patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "RecordsBean{" +
                "patrolItemRecordId=" + patrolItemRecordId +
                ", accountGuid='" + accountGuid + '\'' +
                ", patrolRecordId=" + patrolRecordId +
                ", patrolTaskItemId=" + patrolTaskItemId +
                ", patrolTaskId=" + patrolTaskId +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
