package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.MainBannerBean;
import com.csx.wanandroiddemo.beans.NavBeans;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description: 导航
 */
public class NavPresenter extends BasePresenter<NavPresenter.Inter> {


    public NavPresenter(Inter view) {
        super(view);
    }

    public void getNavDatas() {
        addSubscribe(model.getNavData()
                .compose(SchedulersHelper.<NavBeans>changeSchedulerObservable())
                .subscribe(new Consumer<NavBeans>() {
                    @Override
                    public void accept(NavBeans navBeans) throws Exception {
                        mView.onNavSuccess(navBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onNavFailed(throwable.getMessage());
                    }
                }));
    }


    public interface Inter extends IBaseView {
        void onNavSuccess(NavBeans navBeans);

        void onNavFailed(String error);

    }
}

