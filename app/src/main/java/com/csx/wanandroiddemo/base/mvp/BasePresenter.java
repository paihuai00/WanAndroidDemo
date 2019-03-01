package com.csx.wanandroiddemo.base.mvp;


import com.csx.wanandroiddemo.https.Model;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: P 层，持有M V层引用
 */
public class BasePresenter<V extends IBaseView> {
    public V mView;
    public Model model = new Model();

    //统一处理订阅(防止内存泄漏)
    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BasePresenter(V view) {
        this.mView = view;
    }

    //在activity生命周期结束的时候调用
    public void detach() {
        mView = null;
        if (compositeDisposable != null) compositeDisposable.clear();
    }

    /**
     * 子 presenter，在订阅的时候，记得调用该方法
     *
     * @param disposable
     */
    public void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(disposable);
    }

}
