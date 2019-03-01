package com.csx.wanandroiddemo.ui.fragments;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.XDividerUtils;
import com.csx.framelib.widgets.XActionBar;

import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;

import com.csx.wanandroiddemo.beans.ArticlesBean;
import com.csx.wanandroiddemo.beans.NavBeans;
import com.csx.wanandroiddemo.ui.adapters.NavListAdapter;
import com.csx.wanandroiddemo.ui.presneters.NavPresenter;
import com.csx.wanandroiddemo.utils.WebActivity;
import com.zhy.view.flowlayout.FlowLayout;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/19
 * @description: 导航
 */
public class NavFragment extends BaseLazyMvpFragment<NavPresenter> implements NavPresenter.Inter {


    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout mNavigationTabLayout;
    @BindView(R.id.rv_navigation)
    RecyclerView mRv;

    private NavListAdapter mAdapter;
    private boolean needScroll;
    private int index;
    private boolean isClickTab;
    private LinearLayoutManager mManager;

    public static NavFragment getInstance() {
        return new NavFragment();
    }

    @Override
    public void initViewLazy(View view) {
        showLoadingDialog();
        presenter.getNavDatas();//请求数据
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_nav;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mAdapter = new NavListAdapter(mRv);
        mManager = new LinearLayoutManager(getContext());
        mRv.setLayoutManager(mManager);
        mRv.addItemDecoration(XDividerUtils.getCommonDivider(getContext(), 0, 0));
        mRv.setAdapter(mAdapter);

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView();
                }
                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (needScroll) {
                    scrollRecyclerView();
                }
            }
        });

        mAdapter.setOnTabItemClick(new NavListAdapter.OnTabItemClick() {
            @Override
            public void onTabItemClick(View view, int itemPosition, int childPosition, FlowLayout parent) {
                ArticlesBean articlesBean = mAdapter.getItem(itemPosition).getArticles().get(childPosition);
                WebActivity.open(getActivity(), articlesBean.getLink(), articlesBean.getTitle(), articlesBean.getId());
            }
        });


        mNavigationTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tabView, int i) {
                isClickTab = true;
                selectTag(i);
            }

            @Override
            public void onTabReselected(TabView tabView, int i) {
            }
        });
    }

    @Override
    public NavPresenter getPresenter() {
        return new NavPresenter(this);
    }

    @Override
    public void onNavSuccess(final NavBeans navBeans) {
        hindLoadingDialog();
        mAdapter.addAll(navBeans.getData());

        mNavigationTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return navBeans.getData() == null ? 0 : navBeans.getData().size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }


            @Override
            public QTabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public QTabView.TabTitle getTitle(int position) {
                return new TabView.TabTitle.Builder()
                        .setContent(navBeans.getData().get(position).getName())
                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.color_1B8AD1),
                                ContextCompat.getColor(getActivity(), R.color.color_666))
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return -1;
            }


        });

    }

    @Override
    public void onNavFailed(String error) {
        hindLoadingDialog();
        ToastUtils.show(error);
    }

    private void scrollRecyclerView() {
        needScroll = false;
        int indexDistance = index - mManager.findFirstVisibleItemPosition();
        if (indexDistance >= 0 && indexDistance < mRv.getChildCount()) {
            int top = mRv.getChildAt(indexDistance).getTop();
            mRv.smoothScrollBy(0, top);
        }
    }

    /**
     * Right recyclerView linkage left tabLayout
     * SCROLL_STATE_IDLE just call once
     *
     * @param newState RecyclerView new scroll state
     */
    private void rightLinkageLeft(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isClickTab) {
                isClickTab = false;
                return;
            }
            int firstPosition = mManager.findFirstVisibleItemPosition();
            if (index != firstPosition) {
                index = firstPosition;
                setChecked(index);
            }
        }
    }

    /**
     * Smooth right to select the position of the left tab
     *
     * @param position checked position
     */
    private void setChecked(int position) {
        if (isClickTab) {
            isClickTab = false;
        } else {
            if (mNavigationTabLayout == null) {
                return;
            }
            mNavigationTabLayout.setTabSelected(index);
        }
        index = position;
    }

    private void selectTag(int i) {
        index = i;
        mRv.stopScroll();
        smoothScrollToPosition(i);
    }

    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = mManager.findFirstVisibleItemPosition();
        int lastPosition = mManager.findLastVisibleItemPosition();
        if (currentPosition <= firstPosition) {
            mRv.smoothScrollToPosition(currentPosition);
        } else if (currentPosition <= lastPosition) {
            int top = mRv.getChildAt(currentPosition - firstPosition).getTop();
            mRv.smoothScrollBy(0, top);
        } else {
            mRv.smoothScrollToPosition(currentPosition);
            needScroll = true;
        }
    }
}
