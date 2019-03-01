package com.csx.wanandroiddemo.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.utils.XDividerUtils;
import com.csx.framelib.widgets.banners.MZBannerView;
import com.csx.framelib.widgets.banners.holder.MZHolderCreator;
import com.csx.framelib.widgets.banners.holder.MZViewHolder;
import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_adapters.XViewHolder;
import com.csx.framelib.widgets.rv_tools.OnRefreshListener;
import com.csx.framelib.widgets.rv_tools.RefreshView;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;
import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.beans.MainArticleBean;
import com.csx.wanandroiddemo.beans.MainBannerBean;
import com.csx.wanandroiddemo.ui.adapters.MainArticleAdapter;
import com.csx.wanandroiddemo.ui.presneters.MainPresenter;
import com.csx.wanandroiddemo.utils.WebActivity;
import com.csx.wanandroiddemo.widgets.MainBannerViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/19
 * @description: 首页
 */
public class MainFragment extends BaseLazyMvpFragment implements MainPresenter.Inter {
    private static final String TAG = "MainFragment";

    @BindView(R.id.rv_main)
    RecyclerView mRv;
    @BindView(R.id.refresh_view)
    RefreshView mRefreshView;

    private MainArticleAdapter mAdapter;

    private MainPresenter presnter;

    private View headView;
    MZBannerView mBannerView;
    private List<MainBannerBean.DataBean> bannerList;

    private int page = 0;//当前页码

    private int collectionPostion = 0;

    private boolean isCollection = false;//是否收藏


    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public void initViewLazy(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initRv();
        initHeadView();
    }

    private void initHeadView() {
        if (headView == null) {
            headView = LayoutInflater.from(getContext()).inflate(R.layout.view_main_head, null);
            mBannerView = headView.findViewById(R.id.banner_view);
            mBannerView.setIndicatorRes(R.drawable.img_carousel_2, R.drawable.img_carousel_1);//设置指示器
        }

        mBannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                if (!Judge.isEmpty(bannerList) && position < bannerList.size()) {
                    WebActivity.open(getContext(),
                            bannerList.get(position).getUrl(),
                            bannerList.get(position).getTitle(),
                            bannerList.get(position).getId());
                }
            }
        });

        if (mAdapter.getHeaderCount() == 0)
            mAdapter.addHeaderView(headView);
    }

    private void initRv() {
        //初次刷新
        mRefreshView.setAutoRefresh(true);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRv.addItemDecoration(XDividerUtils.getCommonDivider(getContext(), 5, 5));
        mAdapter = new MainArticleAdapter(mRv);
        mRv.setAdapter(mAdapter);

        //下拉刷新
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.isLoadMore(false);
                page = 0;
                mAdapter.clear();
                presnter.getBannerData();
                presnter.getMainArticle(page);
            }
        });

        //上拉加载
        mAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {
                presnter.getBannerData();
                presnter.getMainArticle(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                presnter.getMainArticle(page);
            }
        });

        //item点击
        mAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                WebActivity.open(getActivity(),
                        mAdapter.getItem(position).getLink(),
                        mAdapter.getItem(position).getTitle(),
                        mAdapter.getItem(position).getId());
            }
        });

        //子点击事件
        mAdapter.setOnItemChildClickListener(new XRecyclerViewAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(XViewHolder holder, View view, int position) {
                if (view.getId() == R.id.iv_star) {
                    collectionPostion = position;
                    if (!mAdapter.getItem(position).isCollect()) {
                        isCollection = true;
                        presnter.doCollection(mAdapter.getItem(position).getId() + "");
                    } else {
                        isCollection = false;
                        presnter.unCollection(mAdapter.getItem(position).getId());
                    }
                }
            }
        });

    }

    @Override
    public MainPresenter getPresenter() {
        if (presnter == null) presnter = new MainPresenter(this);
        return presnter;
    }

    /*首页banner 数据response*/
    @Override
    public void bannerDataSuccess(MainBannerBean mainBannerBean) {
        bannerList = mainBannerBean.getData();
        //设置轮播图，数据
        mBannerView.setPages(bannerList, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new MainBannerViewHolder();
            }
        });
        mBannerView.start();
    }

    @Override
    public void bannerDataFailed(String error) {
        ToastUtils.show(error);
    }

    //首页文章 数据
    @Override
    public void mainArticleSuccess(MainArticleBean articleBean) {
        mRefreshView.stopRefresh(true);

        if (!Judge.isEmpty(articleBean)) {
            mAdapter.addAll(articleBean.getData().getDatas());

            if (mAdapter.getDataCount() == 0) {
                mAdapter.isLoadMore(false);
                mAdapter.showEmpty();
            } else {
                if (articleBean.getData().isOver()) {
                    mAdapter.isLoadMore(false);
                    mAdapter.showLoadComplete();
                } else {
                    mAdapter.showContent();
                    mAdapter.isLoadMore(true);
                }
            }
        }
    }

    @Override
    public void mainArticleFailed(String error) {
        mRefreshView.stopRefresh(true);

        if (mAdapter.getDataCount() == 0) {
            mAdapter.showError();
        }
        ToastUtils.show(error);
    }

    //收藏
    @Override
    public void doCollectionSuccess() {
        if (isCollection) {
            mAdapter.getItem(collectionPostion).setCollect(true);
            ToastUtils.show("收藏成功！");
        } else {
            mAdapter.getItem(collectionPostion).setCollect(false);
            ToastUtils.show("取消收藏成功！");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void doCollectionFailed(String error) {
        ToastUtils.show(error);
    }
}
