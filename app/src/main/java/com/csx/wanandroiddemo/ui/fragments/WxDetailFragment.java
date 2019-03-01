package com.csx.wanandroiddemo.ui.fragments;

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
import com.csx.wanandroiddemo.beans.WxArticleDetailBean;
import com.csx.wanandroiddemo.ui.adapters.WxArticleDetailAdapter;
import com.csx.wanandroiddemo.ui.presneters.WxArticleDetailListPresenter;
import com.csx.wanandroiddemo.utils.WebActivity;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description: 具体的公众号列表
 */
public class WxDetailFragment extends BaseLazyMvpFragment<WxArticleDetailListPresenter> implements WxArticleDetailListPresenter.Inter {

    @BindView(R.id.rv_detail)
    RecyclerView mRvDetail;
    @BindView(R.id.refresh_view)
    RefreshView mRefreshView;

    private WxArticleDetailAdapter mAdapter;

    private int id;//请求需要使用该id

    private boolean isCollection = false;//true：收藏；false：取消收藏
    private int collectionPosition = 0;

    public static WxDetailFragment getInstance(int id) {
        WxDetailFragment detailFragment = new WxDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }


    @Override
    public void initViewLazy(View view) {
        mRefreshView.setAutoRefresh(true);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fg_wx_detail;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        //获取到传入的id
        id = getArguments().getInt("id");
        mRvDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WxArticleDetailAdapter(mRvDetail);
        mRvDetail.setAdapter(mAdapter);

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                presenter.getWxArticleDetailList(id);
            }
        });

        mAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                WebActivity.open(getActivity(), mAdapter.getItem(position).getLink(), mAdapter.getItem(position).getTitle(), mAdapter.getItem(position).getId());
            }
        });

        mAdapter.setOnItemChildClickListener(new XRecyclerViewAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(XViewHolder holder, View view, int position) {
                if (view.getId() == R.id.iv_star) {
                    collectionPosition = position;
                    if (mAdapter.getItem(position).isCollect()) {
                        isCollection = false;
                        presenter.unCollection(mAdapter.getItem(position).getId());
                    } else {
                        isCollection = true;
                        presenter.doCollection(mAdapter.getItem(position).getId() + "");
                    }
                }
            }
        });
    }

    @Override
    public WxArticleDetailListPresenter getPresenter() {
        return new WxArticleDetailListPresenter(this);
    }

    @Override
    public void onWxDetailListSuccess(WxArticleDetailBean detailListBean) {
        mRefreshView.stopRefresh(true);

        mAdapter.addAll(detailListBean.getData().getDatas());

        if (detailListBean.getData().isOver()) {
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
    public void onWxDetailListFailed(String error) {
        mRefreshView.stopRefresh(true);
        ToastUtils.show(error);
    }

    @Override
    public void showCollectionSuccess() {
        if (isCollection) {
            mAdapter.getItem(collectionPosition).setCollect(true);
            ToastUtils.show("收藏成功！");
        } else {
            mAdapter.getItem(collectionPosition).setCollect(false);
            ToastUtils.show("取消收藏成功！");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCollectionFailed(String error) {
        ToastUtils.show("");
    }


}
