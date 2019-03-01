package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.CollectionArticleListBean;
import com.csx.wanandroiddemo.beans.CollectionBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 收藏
 */
public class CollectionArticlePresenter extends BasePresenter<CollectionArticlePresenter.Inter> {

    public CollectionArticlePresenter(Inter view) {
        super(view);
    }

    public void getCollectionBean(int page) {
        addSubscribe(model.getCollectionBean(page)
                .compose(SchedulersHelper.<CollectionArticleListBean>changeSchedulerObservable())
                .subscribe(new Consumer<CollectionArticleListBean>() {
                    @Override
                    public void accept(CollectionArticleListBean collectionArticleListBean) throws Exception {
                        mView.onCollectionArticleSuccess(collectionArticleListBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onCollectionArticleFailed(throwable.getMessage());
                    }
                }));
    }

    //取消收藏
    public void unCollection(int id, int origineId) {
        addSubscribe(model.unCollectionMine(id, origineId)
                .compose(SchedulersHelper.<CollectionBean>changeSchedulerObservable())
                .subscribe(new Consumer<CollectionBean>() {
                    @Override
                    public void accept(CollectionBean collectionBean) throws Exception {
                        if (collectionBean.getErrorCode() == 0)
                            mView.unCollectionSuccess();
                        else
                            mView.unCollectionFailed(collectionBean.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.unCollectionFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {
        void onCollectionArticleSuccess(CollectionArticleListBean collectionArticleListBean);

        void onCollectionArticleFailed(String error);


        void unCollectionSuccess();

        void unCollectionFailed(String error);
    }
}

