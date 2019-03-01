package com.csx.wanandroiddemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;


/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: Mvp activity 基类
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    public P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        if (presenter != null){
            presenter.detach();
            presenter = null;
        }
        super.onDestroy();
    }
}
