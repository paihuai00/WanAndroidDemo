package com.csx.framelib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Random;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/17
 * @description: activity 基类
 * 1，设置 layoutId  ， 初始化view/data 抽象方法
 * 2，activity 跳转，以及防止多次点击跳转处理
 * 3，软键盘判断，防止内存泄漏
 */
public abstract class XActivity extends AppCompatActivity {

    public Handler mHandler = new Handler(Looper.getMainLooper());//创建一个公共的handler（主线程）

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null && getSupportActionBar().isShowing())
            getSupportActionBar().hide();
        setContentView(getLayoutId());

        onPre();

        initOtherData();

        initView();

        initData();
    }

    //=========================子类必须要实现的abstract方法========================================

    protected abstract void onPre();

    //设置布局id
    protected abstract int getLayoutId();

    protected abstract void initOtherData();

    //初始化View
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //===============页面跳转==================
    public void startActivity(Class<? extends AppCompatActivity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivityFinish(Class<? extends AppCompatActivity> cls) {
        startActivity(new Intent(this, cls));
        finish();//开启后，关闭本身
    }

    private ActivityCallback mActivityCallback;
    private int mActivityRequestCode;

    public void startActivityForResult(Intent intent, ActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, @Nullable Bundle options, ActivityCallback callback) {
        if (mActivityCallback == null) {
            mActivityCallback = callback;
            // 随机生成请求码，这个请求码在 0 - 255 之间
            mActivityRequestCode = new Random().nextInt(255);
            startActivityForResult(intent, mActivityRequestCode, options);
        } else {
            // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
            throw new IllegalArgumentException("Error, The callback is not over yet");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback.onActivityResult(resultCode, data);
            mActivityCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    // 隐藏软键盘，避免软键盘引发的内存泄露
    @Override
    public void finish() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.finish();
    }

    /**
     * 防 Activity 多重跳转：https://www.jianshu.com/p/579f1f118161
     */

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (startActivitySelfCheck(intent)) {
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private String mStartActivityTag;
    private long mStartActivityTime;

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    protected boolean startActivitySelfCheck(Intent intent) {
        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        } else { // 其他方式
            return result;
        }

        if (tag.equals(mStartActivityTag) && mStartActivityTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false;
        }

        mStartActivityTag = tag;
        mStartActivityTime = SystemClock.uptimeMillis();
        return result;
    }

    /**
     * Activity 回调接口
     */
    public interface ActivityCallback {
        /**
         * 结果回调
         *
         * @param resultCode 结果码
         * @param data       数据
         */
        void onActivityResult(int resultCode, @Nullable Intent data);
    }



}
