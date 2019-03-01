package com.csx.wanandroiddemo.app;


import android.view.Gravity;

import com.csx.framelib.base.BaseApplication;
import com.csx.framelib.toast.IToastStyle;
import com.csx.framelib.toast.ToastUtils;
import com.csx.wanandroiddemo.utils.LogUtil;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/17
 * @description:
 */
public class MyApplication extends BaseApplication {
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化Log日志
        LogUtil.init(true, AppConfig.isPrintLog);

        //初始化DbFlow数据库
        FlowManager.init(this);

    }


    public static MyApplication getInstance() {
        return instance;
    }
}
