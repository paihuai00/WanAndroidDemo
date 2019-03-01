package com.csx.wanandroiddemo.ui.presneters;

import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.base.mvp.IBaseView;
import com.csx.wanandroiddemo.beans.ProjectBean;
import com.csx.wanandroiddemo.beans.ProjectListBean;
import com.csx.wanandroiddemo.https.SchedulersHelper;

import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description: 项目列表
 */
public class ProjectListPresenter extends BasePresenter<ProjectListPresenter.Inter> {

    public ProjectListPresenter(Inter view) {
        super(view);
    }

    public void getProjectTitleList(int page, int cid) {
        addSubscribe(model.getProjectListBean(page, cid)
                .compose(SchedulersHelper.<ProjectListBean>changeSchedulerObservable())
                .subscribe(new Consumer<ProjectListBean>() {
                    @Override
                    public void accept(ProjectListBean projectBean) throws Exception {
                        mView.onProjectListSuccess(projectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onProjectListFailed(throwable.getMessage());
                    }
                }));
    }

    public interface Inter extends IBaseView {
        void onProjectListSuccess(ProjectListBean projectBean);

        void onProjectListFailed(String error);
    }
}

