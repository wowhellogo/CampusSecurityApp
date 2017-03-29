package com.campussecurity.app.rfidjni;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;

import com.campussecurity.app.R;
import com.campussecurity.app.utils.FNCUtils;
import com.hao.common.base.BaseActivity;
import com.hao.common.base.TopBarType;
import com.hao.common.rx.RxBus;
import com.hao.common.utils.ToastUtil;


/**
 * @Package com.campussecurity.app.rfidjni
 * @作 用:刷卡界面
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/30 0030
 */

public class MainFncCardActivity extends BaseActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isFirst = true;
    private IntentFilter ndef;
    private int position;

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, MainFncCardActivity.class);
        intent.putExtra("position",position);
        return intent;
    }

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_main_fnc_card;
    }

    @Override
    protected TopBarType getTopBarType() {
        return TopBarType.TitleBar;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle(getString(R.string.scan_card_title));
        //NFC适配器，所有的关于NFC的操作从该适配器进行
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!ifNFCUse()) {
            return;
        }
        //将被调用的Intent，用于重复被Intent触发后将要执行的跳转
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        //设定要过滤的标签动作，这里只接收ACTION_NDEF_DISCOVERED类型
        ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[]{ndef};// 过滤器
        mTechLists = new String[][]{new String[]{NfcA.class.getName()}, new String[]{NfcF.class.getName()}, new String[]{NfcB.class.getName()}, new String[]{NfcV.class.getName()}};// 允许扫描的标签类型
        position=getIntent().getIntExtra("position",-1);
        if (isFirst) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
                RxBus.send(new TagIdEvent(position,FNCUtils.getTagId(getIntent())));
                mSwipeBackHelper.backward();
            }
            isFirst = false;

        }
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    /*
     * 重写onResume回调函数的意义在于处理多次读取NFC标签时的情况
     * (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // 前台分发系统,这里的作用在于第二次检测NFC标签时该应用有最高的捕获优先权.
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters, mTechLists);

    }

    /*
     * 有必要要了解onNewIntent回调函数的调用时机,请自行上网查询
     *  (non-Javadoc)
     * @see android.app.Activity#onNewIntent(android.content.Intent)
     */
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            RxBus.send(new TagIdEvent(position,FNCUtils.getTagId(intent)));
            mSwipeBackHelper.backward();
        }

    }


    /**
     * 检测工作,判断设备的NFC支持情况
     *
     * @return
     */
    private Boolean ifNFCUse() {
        // TODO Auto-generated method stub
        if (nfcAdapter == null) {
            ToastUtil.show(R.string.tip_device_not_support);
            finish();
            return false;
        }
        if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
            ToastUtil.show(R.string.please_first_enable_nfc_function_in_the_system_settings);
            finish();
            return false;
        }
        return true;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
