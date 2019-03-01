package com.csx.wanandroiddemo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.csx.wanandroiddemo.base.mvp.BasePresenter;

import butterknife.ButterKnife;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: 懒加载fg
 */
public abstract class BaseLazyMvpFragment<P extends BasePresenter> extends BaseMvpFragment<P> {
    /**
     * 是否可见状态 为了避免和{@link Fragment#isVisible()}冲突 换个名字
     */
    private boolean isFragmentVisible;

    protected boolean isPrepared;

    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

    private boolean forceLoad = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
        isFirstLoad = true;

        view = super.onCreateView(inflater, container, savedInstanceState);

        isPrepared = true;
        lazyLoad();
        return view;
    }


    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (isPrepared && isFragmentVisible) {
            if (forceLoad || isFirstLoad) {
                forceLoad = false;
                isFirstLoad = false;
                initViewLazy(view);
            }
        }
    }

    public abstract void initViewLazy(View view);

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
//     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 和上面的 onHiddenChanged 都是为了不同情况下的懒加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onVisible() {
        isFragmentVisible = true;
        lazyLoad();
        onResumeLazy();
    }

    protected void onInvisible() {
        isFragmentVisible = false;
        onPauseLazy();
    }

    public void onPauseLazy() {

    }

    public void onResumeLazy() {

    }
}
