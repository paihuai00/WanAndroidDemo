package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.CollectionBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/26
 * @description: web presenter
 */
public class WebPresenter extends BasePresenter<WebPresenter.Inter> {

    public WebPresenter(Inter view) {
        super(view);
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
    public void unCollection(int id, int originId) {
        addSubscribe(model.unCollectionMine(id, originId)
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
        void showCollectionSuccess();

        void showCollectionFailed(String error);
    }
}

