package com.csx.wanandroiddemo.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_tools.OnRefreshListener;
import com.csx.framelib.widgets.rv_tools.RefreshView;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;
import com.csx.wanandroiddemo.beans.ProjectListBean;
import com.csx.wanandroiddemo.ui.adapters.ProjectListAdapter;
import com.csx.wanandroiddemo.ui.presneters.ProjectListPresenter;
import com.csx.wanandroiddemo.utils.WebActivity;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description: 项目列表
 */
public class ProjectListFragment extends BaseLazyMvpFragment<ProjectListPresenter> implements ProjectListPresenter.Inter {

    @BindView(R.id.rv_project)
    RecyclerView mRv;
    @BindView(R.id.refresh_view)
    RefreshView mRefreshView;

    private int cid;//传入
    private int page;
    private ProjectListAdapter mAdapter;

    public static ProjectListFragment getInstance(int cid) {
        Bundle bundle = new Bundle();
        bundle.putInt("cid", cid);
        ProjectListFragment projectListFragment = new ProjectListFragment();
        projectListFragment.setArguments(bundle);
        return projectListFragment;
    }


    @Override
    public void initViewLazy(View view) {
        mRefreshView.setAutoRefresh(true);
    }

    @Override
    public ProjectListPresenter getPresenter() {
        return new ProjectListPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_project_list;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        cid = getArguments().getInt("cid");

        mAdapter = new ProjectListAdapter(mRv);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter);

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mAdapter.isLoadMore(false);
                mAdapter.clear();
                presenter.getProjectTitleList(page, cid);
            }
        });

        mAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {
                presenter.getProjectTitleList(page, cid);
            }

            @Override
            public void onLoadMore() {
                page++;
                presenter.getProjectTitleList(page, cid);
            }
        });

        //跳转
        mAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                WebActivity.open(getActivity(), mAdapter.getItem(position).getLink(), mAdapter.getItem(position).getTitle(), mAdapter.getItem(position).getId());
            }
        });
    }

    @Override
    public void onProjectListSuccess(ProjectListBean projectBean) {
        mRefreshView.stopRefresh(true);
        mAdapter.addAll(projectBean.getData().getDatas());

        if (projectBean.getData().isOver()) {
            mAdapter.isLoadMore(false);
            mAdapter.showLoadComplete();
        } else {
            mAdapter.isLoadMore(true);

            if (mAdapter.getDataCount() == 0) {
                mAdapter.showEmpty();
            } else {
                mAdapter.showContent();
            }
        }

    }

    @Override
    public void onProjectListFailed(String error) {
        mRefreshView.stopRefresh(true);
    }
}
