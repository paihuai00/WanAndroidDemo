package com.csx.wanandroiddemo.ui.presneters;

import com.csx.framelib.utils.Judge;
import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.KnowledgeBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/21
 * @description: 知识体系
 */
public class KnowledgePresenter extends BasePresenter<KnowledgePresenter.Inter> {

    public KnowledgePresenter(Inter view) {
        super(view);
    }

    //获取知识体系 data
    public void getKnowledge() {
        addSubscribe(model.getKnowledge().compose(SchedulersHelper.<KnowledgeBean>changeSchedulerObservable())
                .subscribe(new Consumer<KnowledgeBean>() {
                    @Override
                    public void accept(KnowledgeBean knowledgeBean) throws Exception {
                        if (!Judge.isEmpty(knowledgeBean))
                            mView.onKnowledgeSuccess(knowledgeBean);
                        else
                            mView.onKnowledgeFailed("未知错误!");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onKnowledgeFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {
        void onKnowledgeSuccess(KnowledgeBean knowledgeBean);

        void onKnowledgeFailed(String errror);
    }
}

