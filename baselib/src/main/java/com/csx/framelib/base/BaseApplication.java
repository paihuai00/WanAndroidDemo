package com.csx.framelib.base;

import android.app.Application;
import android.content.Context;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.SPUtils;
import com.csx.framelib.widgets.ActivityStackManager;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/17
 * @description:
 */
public class BaseApplication extends Application {
    private String SpName = "AppSp";

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        //初始化toast
        ToastUtils.init(this);

        //初始化栈管理
        ActivityStackManager.init(this);

        //初始化 Sp
        SPUtils.initialize(this, SpName);
    }
}
