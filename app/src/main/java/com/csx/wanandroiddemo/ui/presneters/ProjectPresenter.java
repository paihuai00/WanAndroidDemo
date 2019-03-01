package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.ProjectBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description:
 */
public class ProjectPresenter extends BasePresenter<ProjectPresenter.Inter> {

    public ProjectPresenter(Inter view) {
        super(view);
    }

    public void getProjectTitleList() {
        addSubscribe(model.getProjectTitleBean()
                .compose(SchedulersHelper.<ProjectBean>changeSchedulerObservable())
                .subscribe(new Consumer<ProjectBean>() {
                    @Override
                    public void accept(ProjectBean projectBean) throws Exception {
                        mView.onProjectTitleSuccess(projectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onProjectTitleFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {
        void onProjectTitleSuccess(ProjectBean projectBean);

        void onProjectTitleFailed(String error);
    }
}

