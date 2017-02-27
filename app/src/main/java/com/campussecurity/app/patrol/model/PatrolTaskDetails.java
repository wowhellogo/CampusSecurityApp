package com.campussecurity.app.patrol.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Package com.campussecurity.app.patrol.model
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月21日  11:38
 */


public class PatrolTaskDetails implements Serializable {

    /**
     * patrolTaskId : 1
     * patrolTypeId : 1
     * schoolId : 1
     * accountGuid : 77b3d076-05a4-4aa9-9ddb-69823a107e9f
     * name : 每天巡检
     * patrols :
     * startTime : 01:00
     * endTime : 23:00
     * type : 0
     * state : 0
     * createDate : 2017-02-17 17:36:15
     * updateDate : 2017-02-17 17:36:15
     * items : [{"patrolTaskItemId":1,"patrolsId":1,"schoolId":1,"patrolTaskId":1,"name":"冬眠是是是试试","sort":1,"createDate":"2017-02-17 10:59:40"},{"patrolTaskItemId":2,"patrolsId":2,"schoolId":1,"patrolTaskId":1,"name":"试试","sort":2,"createDate":"2017-02-17 17:37:34"}]
     * records : [{"patrolTaskItemId":1,"patrolItemRecordId":1,"patrolRecordId":1,"patrolTaskId":1,"createDate":"2017-02-17 10:59:40"},{"patrolTaskItemId":2,"patrolItemRecordId":2,"patrolRecordId":1,"patrolTaskId":1,"createDate":"2017-02-17 17:37:34"}]
     */

    private int patrolTaskId;
    private int patrolTypeId;
    private int schoolId;
    private String accountGuid;
    private String name;
    private String patrols;
    private String startTime;
    private String endTime;
    private int type;
    private int state;
    private String createDate;
    private String updateDate;
    private List<PatrolTaskItemBean> items;
    private List<RecordsBean> records;

    public void setPatrolTaskId(int patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public void setPatrolTypeId(int patrolTypeId) {
        this.patrolTypeId = patrolTypeId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setAccountGuid(String accountGuid) {
        this.accountGuid = accountGuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatrols(String patrols) {
        this.patrols = patrols;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setItems(List<PatrolTaskItemBean> items) {
        this.items = items;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public int getPatrolTaskId() {
        return patrolTaskId;
    }

    public int getPatrolTypeId() {
        return patrolTypeId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public String getName() {
        return name;
    }

    public String getPatrols() {
        return patrols;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getType() {
        return type;
    }

    public int getState() {
        return state;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public List<PatrolTaskItemBean> getItems() {
        return items;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public String getTime() {
        return startTime + "-" + endTime;
    }

    @Override
    public String toString() {
        return "PatrolTaskDetails{" +
                "patrolTaskId=" + patrolTaskId +
                ", patrolTypeId=" + patrolTypeId +
                ", schoolId=" + schoolId +
                ", accountGuid='" + accountGuid + '\'' +
                ", name='" + name + '\'' +
                ", patrols='" + patrols + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", items=" + items +
                ", records=" + records +
                '}';
    }
}
