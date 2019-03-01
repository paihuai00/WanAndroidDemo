package com.csx.wanandroiddemo.base;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.csx.framelib.base.UiXActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/17
 * @description: app 页面的baseActivity，可以添加一些通用的操作
 */
public abstract class BaseActivity extends UiXActivity {
    private Unbinder mUnbinder;//绑定ButterKnife

    /**
     * 如需初始化EventBus
     * 子类需要复写{@link BaseActivity#onPre()}
     * 在里面设置 isRegisterEventBus =true
     */
    public boolean isRegisterEventBus = false;//是否注册EventBus

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initOtherData() {
        if (isRegisterEventBus) EventBus.getDefault().register(this);

        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null) mUnbinder.unbind();//解绑ButterKnife
        super.onDestroy();

        if (isRegisterEventBus && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent() {

    }

}
