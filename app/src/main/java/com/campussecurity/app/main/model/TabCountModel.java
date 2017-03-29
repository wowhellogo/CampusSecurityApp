package com.campussecurity.app.main.model;

/**
 * @Package com.campussecurity.app.main.model
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/19 0019
 */

public class TabCountModel {

    /**
     * securityTask : 34
     * patrolTask : 5
     */

    private int securityTask;
    private int patrolTask;

    public int getSecurityTask() {
        return securityTask;
    }

    public void setSecurityTask(int securityTask) {
        this.securityTask = securityTask;
    }

    public int getPatrolTask() {
        return patrolTask;
    }

    public void setPatrolTask(int patrolTask) {
        this.patrolTask = patrolTask;
    }
}
