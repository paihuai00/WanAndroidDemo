package com.csx.wanandroiddemo.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csx.framelib.base.UiFragment;
import com.csx.wanandroiddemo.base.mvp.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description:
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends UiFragment {
    public P presenter;
    public View view = null;
    private Unbinder mButterKnife;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        mButterKnife = ButterKnife.bind(this, view);

        presenter = getPresenter();

        return view;
    }

    public abstract P getPresenter();//设置Presenter

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
        if (presenter != null) presenter.detach();
    }
}
