package com.campussecurity.app.securitycheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.campussecurity.app.App;
import com.campussecurity.app.R;
import com.campussecurity.app.login.model.User;
import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.base.BaseActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.manager.AppManager;
import com.hao.common.rx.RxUtil;
import com.hao.common.widget.BGASortableNinePhotoLayout;

/**
 * @Package com.campussecurity.app.securitycheck
 * @作 用:添加安全任务
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/11 0011
 */

public class AddSecurityCheckActivity extends BaseActivity implements View.OnClickListener {
    EditText edTitle;
    EditText edExplain;
    BGASortableNinePhotoLayout mBGASortableNinePhotoLayout;

    private User mUser;
    private String authorAccountGuid;
    private String schoolId;

    public static Intent newItent(Context context, String authorAccountGuid, String schoolId) {
        Intent intent = new Intent(context, AddSecurityCheckActivity.class);
        intent.putExtra("authorAccountGuid", authorAccountGuid);
        intent.putExtra("schoolId", schoolId);
        return intent;
    }

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_add_security_check;
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(getString(R.string.title_add_security_check));
        edTitle = (EditText) getViewById(R.id.ed_title);
        edExplain = (EditText) getViewById(R.id.ed_explain);
        mBGASortableNinePhotoLayout = (BGASortableNinePhotoLayout) getViewById(R.id.sortable_nine_phone_layout);
    }

    @Override
    protected void setListener() {
        getViewById(R.id.ff_dispose).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mUser = ((App) AppManager.getApp()).cacheUser;
        schoolId = getIntent().getStringExtra("schoolId");
        authorAccountGuid = getIntent().getStringExtra("authorAccountGuid");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ff_dispose:

                break;
        }
    }


    public void attemptPost() {

        RestDataSoure.newInstance().addSecurityTask(authorAccountGuid, mUser.accountGuid, schoolId,
                edTitle.getText().toString().trim(), edExplain.getText().toString().trim(), "")
                .compose(RxUtil.applySchedulersJobUI());

    }
}
