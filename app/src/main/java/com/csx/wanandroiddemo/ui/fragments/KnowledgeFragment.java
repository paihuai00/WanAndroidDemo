package com.csx.wanandroiddemo.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.widgets.XActionBar;
import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_tools.OnRefreshListener;
import com.csx.framelib.widgets.rv_tools.RefreshView;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;
import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.beans.KnowledgeBean;
import com.csx.wanandroiddemo.ui.activitys.KnowDetailActivity;
import com.csx.wanandroiddemo.ui.adapters.KnowledgeAdapter;
import com.csx.wanandroiddemo.ui.presneters.KnowledgePresenter;
import com.csx.wanandroiddemo.utils.WebActivity;

import butterknife.BindView;
import io.reactivex.internal.operators.maybe.MaybeConcatArrayDelayError;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/19
 * @description: 知识体系
 */
public class KnowledgeFragment extends BaseLazyMvpFragment<KnowledgePresenter> implements KnowledgePresenter.Inter {

    @BindView(R.id.rv_knowledge)
    RecyclerView mRv;
    @BindView(R.id.refresh_view)
    RefreshView mRefreshView;

    private KnowledgeAdapter mAdapter;


    public static KnowledgeFragment getInstance() {
        return new KnowledgeFragment();
    }

    @Override
    public void initViewLazy(View view) {
        mRefreshView.setAutoRefresh(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_knowledge;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new KnowledgeAdapter(mRv);
        mRv.setAdapter(mAdapter);

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                presenter.getKnowledge();
            }
        });

        mAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                KnowDetailActivity.open(getActivity(), mAdapter.getItem(position));
            }
        });
    }

    @Override
    public KnowledgePresenter getPresenter() {
        return new KnowledgePresenter(this);
    }

    @Override
    public void onKnowledgeSuccess(KnowledgeBean knowledgeBean) {
        mRefreshView.stopRefresh(true);
        mAdapter.addAll(knowledgeBean.getData());
        if (mAdapter.getDataCount() == 0)
            mAdapter.showEmpty();
        else
            mAdapter.showContent();
    }

    @Override
    public void onKnowledgeFailed(String errror) {
        mRefreshView.stopRefresh(true);
        if (mAdapter.getDataCount() == 0) {
            mAdapter.showError();
        }
        ToastUtils.show(errror);
    }
}
