package com.csx.wanandroiddemo.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.widgets.XActionBar;
import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_adapters.XViewHolder;
import com.csx.framelib.widgets.rv_tools.OnRefreshListener;
import com.csx.framelib.widgets.rv_tools.RefreshView;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseMvpActivity;
import com.csx.wanandroiddemo.beans.CollectionArticleListBean;
import com.csx.wanandroiddemo.events.WebCollectionArticleEvent;
import com.csx.wanandroiddemo.ui.adapters.CollectionArticleAdapter;
import com.csx.wanandroiddemo.ui.presneters.CollectionArticlePresenter;
import com.csx.wanandroiddemo.utils.RxBus;
import com.csx.wanandroiddemo.utils.WebActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 收藏文章
 */
public class CollectionArticleActivity extends BaseMvpActivity<CollectionArticlePresenter> implements CollectionArticlePresenter.Inter {
    private static final String TAG = "CollectionArticleActivity";
    @BindView(R.id.xab_collection)
    XActionBar mXab;
    @BindView(R.id.rv_collection)
    RecyclerView mRv;
    @BindView(R.id.refresh_view)
    RefreshView mRefreshView;

    private CollectionArticleAdapter mAdapter;

    private int page = 0;
    private int unCollectionPosition = 0;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, CollectionArticleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected CollectionArticlePresenter createPresenter() {
        return new CollectionArticlePresenter(this);
    }

    @Override
    protected void onPre() {
        isRegisterEventBus = true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        mXab.setTitle("收藏");
        mXab.hasCloseBtn(this);

        mAdapter = new CollectionArticleAdapter(mRv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //收藏页面，都是 已收藏的！
                WebActivity.open(CollectionArticleActivity.this,
                        mAdapter.getItem(position).getLink(),
                        mAdapter.getItem(position).getTitle(),
                        mAdapter.getItem(position).getId(),
                        mAdapter.getItem(position).getOriginId(),
                        true);
            }
        });

        mAdapter.setOnItemChildClickListener(new XRecyclerViewAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(XViewHolder holder, View view, int position) {
                if (view.getId() == R.id.iv_star) {
                    unCollectionPosition = position;
                    presenter.unCollection(mAdapter.getItem(position).getId(), mAdapter.getItem(position).getOriginId());
                }
            }
        });
    }

    @Override
    protected void initData() {
        mRefreshView.setAutoRefresh(true);
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.isLoadMore(false);
                mAdapter.clear();
                page = 0;
                presenter.getCollectionBean(page);
            }
        });

        //上拉加载
        mAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {
                presenter.getCollectionBean(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                presenter.getCollectionBean(page);
            }
        });

    }

    @Override
    public void onCollectionArticleSuccess(CollectionArticleListBean collectionArticleListBean) {
        mRefreshView.stopRefresh(true);

        mAdapter.addAll(collectionArticleListBean.getData().getDatas());

        if (collectionArticleListBean.getData().isOver()) {
            mAdapter.isLoadMore(false);
            if (mAdapter.getDataCount() == 0)
                mAdapter.showEmpty();
            else
                mAdapter.showLoadComplete();
        } else {
            mAdapter.isLoadMore(false);
            if (mAdapter.getDataCount() == 0)
                mAdapter.showEmpty();
            else
                mAdapter.showContent();
        }
    }

    @Override
    public void onCollectionArticleFailed(String error) {
        mRefreshView.stopRefresh(true);

        if (mAdapter.getDataCount() == 0) {
            mAdapter.showEmpty();
        } else {
            mAdapter.showError();
        }

        ToastUtils.show(error);
    }

    @Override
    public void unCollectionSuccess() {
        ToastUtils.show("取消收藏成功！");
        mAdapter.remove(unCollectionPosition);
        mAdapter.notifyItemRemoved(unCollectionPosition);

        if (mAdapter.getDataCount() == 0)
            mAdapter.showEmpty();

    }

    @Override
    public void unCollectionFailed(String error) {
        ToastUtils.show(error);
    }


    //============================接到取消的Event=============================================
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnCollectEvent(WebCollectionArticleEvent event) {
        if (!mRefreshView.isRefreshing)
            mRefreshView.setAutoRefresh(true);
    }


}
