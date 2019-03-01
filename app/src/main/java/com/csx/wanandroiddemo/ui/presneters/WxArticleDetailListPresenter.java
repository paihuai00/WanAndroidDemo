package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.CollectionBean;
import com.csx.wanandroiddemo.beans.WxArticleDetailBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description:
 */
public class WxArticleDetailListPresenter extends BasePresenter<WxArticleDetailListPresenter.Inter> {

    public WxArticleDetailListPresenter(Inter view) {
        super(view);
    }

    //公众号，详情列表
    public void getWxArticleDetailList(int id) {
        addSubscribe(model.getWxDetailList(id)
                .compose(SchedulersHelper.<WxArticleDetailBean>changeSchedulerObservable())
                .subscribe(new Consumer<WxArticleDetailBean>() {
                    @Override
                    public void accept(WxArticleDetailBean wxArticleDetailBean) throws Exception {
                        mView.onWxDetailListSuccess(wxArticleDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onWxDetailListFailed(throwable.getMessage());
                    }
                }));
    }

    //收藏
    public void doCollection(String id) {
        addSubscribe(model.doCollection(id)
                .compose(SchedulersHelper.<CollectionBean>changeSchedulerObservable())
                .subscribe(new Consumer<CollectionBean>() {
                    @Override
                    public void accept(CollectionBean collectionBean) throws Exception {
                        if (collectionBean.getErrorCode() == 0)
                            mView.showCollectionSuccess();
                        else
                            mView.showCollectionFailed(collectionBean.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showCollectionFailed(throwable.getMessage());
                    }
                }));
    }

    //取消收藏
    public void unCollection(int id) {
        addSubscribe(model.unCollection(id)
                .compose(SchedulersHelper.<CollectionBean>changeSchedulerObservable())
                .subscribe(new Consumer<CollectionBean>() {
                    @Override
                    public void accept(CollectionBean collectionBean) throws Exception {
                        if (collectionBean.getErrorCode() == 0)
                            mView.showCollectionSuccess();
                        else
                            mView.showCollectionFailed(collectionBean.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showCollectionFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {
        void onWxDetailListSuccess(WxArticleDetailBean detailListBean);

        void onWxDetailListFailed(String error);

        void showCollectionSuccess();

        void showCollectionFailed(String error);
    }
}

