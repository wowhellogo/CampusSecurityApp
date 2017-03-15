package com.campussecurity.app.securitycheck;

/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/11 0011
 */

public class ProcessorEvent {
    public ProcessorModel mProcessorModel;
    public int position;

    public ProcessorEvent(ProcessorModel processorModel,int position) {
        mProcessorModel = processorModel;
        this.position=position;
    }
}
