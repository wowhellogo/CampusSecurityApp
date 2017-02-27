package com.campussecurity.app.patrol.model;

import java.io.Serializable;

/**
 * @Package com.campussecurity.app.patrol.model
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月21日  11:54
 */


public class PatrolTaskItemBean implements Serializable {
    /**
     * patrolTaskItemId : 1
     * patrolsId : 1
     * schoolId : 1
     * patrolTaskId : 1
     * name : 冬眠是是是试试
     * sort : 1
     * createDate : 2017-02-17 10:59:40
     */

    private int patrolTaskItemId;
    private int patrolsId;
    private int schoolId;
    private int patrolTaskId;
    private String name;
    private int sort;
    private String createDate;

    public void setPatrolTaskItemId(int patrolTaskItemId) {
        this.patrolTaskItemId = patrolTaskItemId;
    }

    public void setPatrolsId(int patrolsId) {
        this.patrolsId = patrolsId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setPatrolTaskId(int patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getPatrolTaskItemId() {
        return patrolTaskItemId;
    }

    public int getPatrolsId() {
        return patrolsId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public int getPatrolTaskId() {
        return patrolTaskId;
    }

    public String getName() {
        return name;
    }

    public int getSort() {
        return sort;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "PatrolTaskItemBean{" +
                "patrolTaskItemId=" + patrolTaskItemId +
                ", patrolsId=" + patrolsId +
                ", schoolId=" + schoolId +
                ", patrolTaskId=" + patrolTaskId +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
