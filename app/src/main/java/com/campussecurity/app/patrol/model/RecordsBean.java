package com.campussecurity.app.patrol.model;

import java.io.Serializable;

/**
 * @Package com.campussecurity.app.patrol.model
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月21日  11:53
 */


public class RecordsBean {
    /**
     * patrolTaskItemId : 1
     * patrolItemRecordId : 1
     * patrolRecordId : 1
     * patrolTaskId : 1
     * createDate : 2017-02-17 10:59:40
     */

    private int patrolTaskItemId;
    private int patrolItemRecordId;
    private int patrolRecordId;
    private int patrolTaskId;
    private String createDate;

    public void setPatrolTaskItemId(int patrolTaskItemId) {
        this.patrolTaskItemId = patrolTaskItemId;
    }

    public void setPatrolItemRecordId(int patrolItemRecordId) {
        this.patrolItemRecordId = patrolItemRecordId;
    }

    public void setPatrolRecordId(int patrolRecordId) {
        this.patrolRecordId = patrolRecordId;
    }

    public void setPatrolTaskId(int patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getPatrolTaskItemId() {
        return patrolTaskItemId;
    }

    public int getPatrolItemRecordId() {
        return patrolItemRecordId;
    }

    public int getPatrolRecordId() {
        return patrolRecordId;
    }

    public int getPatrolTaskId() {
        return patrolTaskId;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "RecordsBean{" +
                "patrolTaskItemId=" + patrolTaskItemId +
                ", patrolItemRecordId=" + patrolItemRecordId +
                ", patrolRecordId=" + patrolRecordId +
                ", patrolTaskId=" + patrolTaskId +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
