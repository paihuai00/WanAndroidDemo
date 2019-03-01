package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.CollectionBean;
import com.csx.wanandroiddemo.beans.KnowledgeDeatilBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/28
 * @description: 知识体系，详情presenter
 */
public class KnowledgeDetailPresenter extends BasePresenter<KnowledgeDetailPresenter.Inter> {

    public KnowledgeDetailPresenter(Inter view) {
        super(view);
    }

    public void getKnowledgeData(int page, int cid) {
        addSubscribe(model.getKnowledgeDetailData(page, cid)
                .compose(SchedulersHelper.<KnowledgeDeatilBean>changeSchedulerObservable())
                .subscribe(new Consumer<KnowledgeDeatilBean>() {
                    @Override
                    public void accept(KnowledgeDeatilBean knowledgeDeatilBean) throws Exception {
                        if (knowledgeDeatilBean.getErrorCode() == 0)
                            mView.onKnowledgeDataSuccess(knowledgeDeatilBean);
                        else
                            mView.onKnowledgeDataFailed(knowledgeDeatilBean.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onKnowledgeDataFailed(throwable.getMessage());
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

    public interface Inter extends IBaseView {
        void onKnowledgeDataSuccess(KnowledgeDeatilBean detailBean);

        void onKnowledgeDataFailed(String error);

        void doCollectionSuccess();

        void doCollectionFailed(String error);
    }
}

