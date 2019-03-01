package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.LoginBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 用户信息
 */
public class UserPresenter extends BasePresenter<UserPresenter.Inter> {

    public UserPresenter(Inter view) {
        super(view);
    }

    //登录
    public void doLogin(String username, String password) {
        addSubscribe(model.getLoginBean(username, password).compose(SchedulersHelper.<LoginBean>changeSchedulerObservable())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        mView.onLoginSuccess(loginBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onLoginFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {
        void onLoginSuccess(LoginBean loginBean);

        void onLoginFailed(String error);

    }
}

