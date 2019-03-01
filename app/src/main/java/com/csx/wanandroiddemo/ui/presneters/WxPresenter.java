package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.WxArticleDetailBean;
import com.csx.wanandroiddemo.beans.WxArticleListBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description: 公众号
 */
public class WxPresenter extends BasePresenter<WxPresenter.Inter> {


    public WxPresenter(Inter view) {
        super(view);
    }

    //获取公众号
    public void getWxList() {
        addSubscribe(model.getWxList().compose(SchedulersHelper.<WxArticleListBean>changeSchedulerObservable())
                .subscribe(new Consumer<WxArticleListBean>() {
                    @Override
                    public void accept(WxArticleListBean wxArticleListBean) throws Exception {
                        mView.onWxListSuccess(wxArticleListBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onWxListFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {

        void onWxListSuccess(WxArticleListBean wxArticleListBean);

        void onWxListFailed(String error);

    }
}

