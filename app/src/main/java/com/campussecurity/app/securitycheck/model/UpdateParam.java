package com.campussecurity.app.securitycheck.model;

/**
 * @Package com.campussecurity.app.securitycheck.model
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年03月15日  19:37
 */


public class UpdateParam {
    private String  accountGuid;
    private String path;

    public UpdateParam(String accountGuid, String path) {
        this.accountGuid = accountGuid;
        this.path = path;
    }

    public String getAccountGuid() {
        return accountGuid;
    }

    public void setAccountGuid(String accountGuid) {
        this.accountGuid = accountGuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
