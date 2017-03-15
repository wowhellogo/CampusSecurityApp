package com.campussecurity.app.patrol.model;

import com.campussecurity.app.securitycheck.ProcessorModel;

import java.io.Serializable;

/**
 * @Package com.campussecurity.app.patrol.model
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月21日  11:54
 */


public class PatrolTaskItemBean implements Serializable {


    /**
     * isRecord : false
     * patrolTaskItemId : 1
     * patrolsId : 1
     * schoolId : 1
     * patrolTaskId : 1
     * name : 冬眠是是是试试
     * sort : 1
     * createDate : 2017-02-17 10:59:40
     */

    private boolean isRecord;
    private int patrolTaskItemId;
    private int patrolsId;
    private int schoolId;
    private int patrolTaskId;
    private String name;
    private int sort;
    private String createDate;
    private String description;
    private RecordsBean record;
    public ProcessorModel mProcessorModel;


    public RecordsBean getRecord() {
        return record;
    }

    public void setRecord(RecordsBean record) {
        this.record = record;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsRecord() {
        return isRecord;
    }

    public void setIsRecord(boolean isRecord) {
        this.isRecord = isRecord;
    }

    public int getPatrolTaskItemId() {
        return patrolTaskItemId;
    }

    public void setPatrolTaskItemId(int patrolTaskItemId) {
        this.patrolTaskItemId = patrolTaskItemId;
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

    public int getPatrolTaskId() {
        return patrolTaskId;
    }

    public void setPatrolTaskId(int patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
