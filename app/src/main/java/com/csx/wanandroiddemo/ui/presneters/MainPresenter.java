package com.csx.wanandroiddemo.ui.presneters;

import com.csx.framelib.utils.Judge;
import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.CollectionBean;
import com.csx.wanandroiddemo.beans.MainArticleBean;
import com.csx.wanandroiddemo.beans.MainBannerBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/20
 * @description:
 */
public class MainPresenter extends BasePresenter<MainPresenter.Inter> {
    private static final String TAG = "MainPresenter";

    public MainPresenter(Inter view) {
        super(view);
    }

    //banner数据
    public void getBannerData() {
        addSubscribe(model.getMainBanner()
                .compose(SchedulersHelper.<MainBannerBean>changeSchedulerObservable())
                .subscribe(new Consumer<MainBannerBean>() {
                    @Override
                    public void accept(MainBannerBean bannerBean) throws Exception {
                        if (!Judge.isEmpty(bannerBean))
                            mView.bannerDataSuccess(bannerBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.bannerDataFailed(throwable.getMessage());
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
                            mView.doCollectionSuccess();
                        else
                            mView.doCollectionFailed(collectionBean.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.doCollectionFailed(throwable.getMessage());
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
                            mView.doCollectionSuccess();
                        else
                            mView.doCollectionFailed(collectionBean.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.doCollectionFailed(throwable.getMessage());
                    }
                }));
    }

    //主页数据
    public void getMainArticle(int page) {
        addSubscribe(model.getMainArticle(page)
                .compose(SchedulersHelper.<MainArticleBean>changeSchedulerObservable())
                .subscribe(new Consumer<MainArticleBean>() {
                    @Override
                    public void accept(MainArticleBean mainArticleBean) throws Exception {
                        if (!Judge.isEmpty(mainArticleBean))
                            mView.mainArticleSuccess(mainArticleBean);
                        else
                            mView.mainArticleFailed("未知错误！");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.mainArticleFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {
        void bannerDataSuccess(MainBannerBean mainBannerBean);

        void bannerDataFailed(String error);

        void mainArticleSuccess(MainArticleBean articleBean);

        void mainArticleFailed(String error);


        void doCollectionSuccess();

        void doCollectionFailed(String error);
    }
}

