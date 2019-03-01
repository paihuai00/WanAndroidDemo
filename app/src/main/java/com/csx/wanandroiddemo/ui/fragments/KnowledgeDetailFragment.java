package com.csx.wanandroiddemo.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_adapters.XViewHolder;
import com.csx.framelib.widgets.rv_tools.OnRefreshListener;
import com.csx.framelib.widgets.rv_tools.RefreshView;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;
import com.csx.wanandroiddemo.base.BaseMvpFragment;
import com.csx.wanandroiddemo.beans.KnowledgeBean;
import com.csx.wanandroiddemo.beans.KnowledgeDeatilBean;
import com.csx.wanandroiddemo.ui.adapters.KnowledgeArticleAdapter;
import com.csx.wanandroiddemo.ui.presneters.KnowledgeDetailPresenter;
import com.csx.wanandroiddemo.ui.presneters.KnowledgePresenter;
import com.csx.wanandroiddemo.utils.WebActivity;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/28
 * @description: 知识体系详情
 */
public class KnowledgeDetailFragment extends BaseLazyMvpFragment<KnowledgeDetailPresenter> implements KnowledgeDetailPresenter.Inter {

    @BindView(R.id.rv_knowledge_detail)
    RecyclerView mRv;
    @BindView(R.id.refresh_view)
    RefreshView mRefreshView;

    private int page = 0;
    private int cid;

    private KnowledgeArticleAdapter mAdapter;

    private boolean isCollection = false;
    private int collectionPosition = 0;

    public static KnowledgeDetailFragment getInstance(int cid) {
        Bundle bundle = new Bundle();
        bundle.putInt("cid", cid);
        KnowledgeDetailFragment knowledgeDetailFragment = new KnowledgeDetailFragment();
        knowledgeDetailFragment.setArguments(bundle);

        return knowledgeDetailFragment;
    }


    @Override
    public KnowledgeDetailPresenter getPresenter() {
        return new KnowledgeDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_knowledge_detail;
    }

    @Override
    public void initData() {
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.isLoadMore(false);
                page = 0;

                presenter.getKnowledgeData(page, cid);
            }
        });

        mAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {
                presenter.getKnowledgeData(page, cid);
            }

            @Override
            public void onLoadMore() {
                page++;
                presenter.getKnowledgeData(page, cid);
            }
        });
    }

    @Override
    public void initView() {
        cid = getArguments().getInt("cid", 0);
        mAdapter = new KnowledgeArticleAdapter(mRv);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                WebActivity.open(
                        getActivity(),
                        mAdapter.getItem(position).getLink(),
                        mAdapter.getItem(position).getTitle(),
                        mAdapter.getItem(position).getId(),
                        mAdapter.getItem(position).getCourseId(),
                        mAdapter.getItem(position).isCollect());
            }
        });

        //子点击事件
        mAdapter.setOnItemChildClickListener(new XRecyclerViewAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(XViewHolder holder, View view, int position) {
                if (view.getId() == R.id.iv_star) {
                    collectionPosition = position;

                    if (mAdapter.getItem(position).isCollect()) {
                        presenter.unCollection(mAdapter.getItem(position).getId());
                        isCollection = false;
                    } else {
                        presenter.doCollection("" + mAdapter.getItem(position).getId());
                        isCollection = true;
                    }
                }
            }
        });
    }

    @Override
    public void initViewLazy(View view) {
        mRefreshView.setAutoRefresh(true);
    }


    @Override
    public void onKnowledgeDataSuccess(KnowledgeDeatilBean detailBean) {
        mRefreshView.stopRefresh(true);
        mAdapter.addAll(detailBean.getData().getDatas());

        if (detailBean.getData().isOver()) {
            mAdapter.isLoadMore(false);
            if (mAdapter.getDataCount() == 0)
                mAdapter.showEmpty();
            else
                mAdapter.showLoadComplete();
        } else {
            mAdapter.isLoadMore(true);
            if (mAdapter.getDataCount() == 0)
                mAdapter.showEmpty();
            else
                mAdapter.showContent();
        }

    }

    @Override
    public void onKnowledgeDataFailed(String error) {
        mRefreshView.stopRefresh(true);
        ToastUtils.show(error);
        if (mAdapter.getDataCount() == 0) {
            mAdapter.showError();
        } else {
            mAdapter.showContent();
        }
    }

    @Override
    public void doCollectionSuccess() {
        if (isCollection) {
            ToastUtils.show("收藏成功！");
            mAdapter.getItem(collectionPosition).setCollect(true);
        } else {
            ToastUtils.show("取消收藏成功！");
            mAdapter.getItem(collectionPosition).setCollect(false);
        }
        mAdapter.notifyItemChanged(collectionPosition);
    }

    @Override
    public void doCollectionFailed(String error) {
        ToastUtils.show(error);
    }
}
