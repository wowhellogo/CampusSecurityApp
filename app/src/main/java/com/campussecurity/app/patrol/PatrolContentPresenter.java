package com.campussecurity.app.patrol;

import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.patrol.model.PatrolTaskDetails;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.hao.common.rx.RESTResultTransformerModel;

/**
 * @Package com.campussecurity.app.patrol
 * @作 用:
 * @创 建 人: linguoding 邮箱：linggoudingg@gmail.com
 * @日 期: 2017年02月23日  20:07
 */


public class PatrolContentPresenter extends LoadPresenter {
    public void loadPatrolContent(int patrolTaskId) {
        loadModel(RestDataSoure.newInstance().getPatrolTaskDetails(patrolTaskId).compose(new RESTResultTransformerModel<PatrolTaskDetails>()));
    }


}
