package com.campussecurity.app.securitycheck;

import java.io.Serializable;

/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/11 0011
 */

public class ProcessorModel implements Serializable {

    /**
     * accountGuid : 77b3d076-05a4-4aa9-9ddb-69823a107e9f
     * departmentId : 1
     * dutyId : 1
     * schoolId : 1
     * name : 林国定
     * avatar : http://campussafety.chinacloudsites.cn/images/HeadPortrait.png
     * mobilePhone :
     * password : 70C6D9483AA0CD7F2B8F4A9210B3C059
     * userName : 15088138597
     * type : 2
     * isDisabled : false
     * createDate : 2017-02-15 11:54:28
     */

    private String accountGuid;
    private int departmentId;
    private int dutyId;
    private int schoolId;
    private String name;
    private String avatar;
    private String mobilePhone;
    private String password;
    private String userName;
    private int type;
    private boolean isDisabled;
    private String createDate;

    public String getAccountGuid() {
        return accountGuid;
    }

    public void setAccountGuid(String accountGuid) {
        this.accountGuid = accountGuid;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getDutyId() {
        return dutyId;
    }

    public void setDutyId(int dutyId) {
        this.dutyId = dutyId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
