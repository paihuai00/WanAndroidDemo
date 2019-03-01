package com.csx.wanandroiddemo.ui.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.widgets.XActionBar;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;
import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.beans.WxArticleListBean;
import com.csx.wanandroiddemo.ui.presneters.WxPresenter;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/19
 * @description: 公众号
 */
public class WxFragment extends BaseLazyMvpFragment<WxPresenter> implements WxPresenter.Inter {

    private static final String TAG = "WxFragment";

    @BindView(R.id.stb_stb)
    SlidingTabLayout mStb;
    @BindView(R.id.vp_wx)
    ViewPager mVp;

    private List<Fragment> fragmentList;
    private List<String> titlesList;
    private VpAdapter mTabAdapter;


    public static WxFragment getInstance() {
        return new WxFragment();
    }

    @Override
    public void initViewLazy(View view) {
        presenter.getWxList();//获取数据
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_wx;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        fragmentList = new ArrayList<>();
        titlesList = new ArrayList<>();

        mTabAdapter = new VpAdapter(getChildFragmentManager());

        mStb.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public WxPresenter getPresenter() {
        return new WxPresenter(this);
    }

    @Override
    public void onWxListSuccess(WxArticleListBean wxArticleListBean) {
        //公众号请求成功
        if (!Judge.isEmpty(wxArticleListBean.getData())) {
            fragmentList.clear();
            titlesList.clear();
            for (int i = 0; i < wxArticleListBean.getData().size(); i++) {
                WxDetailFragment wxDetailFragment = WxDetailFragment.getInstance(wxArticleListBean.getData().get(i).getId());
                fragmentList.add(wxDetailFragment);
                titlesList.add(wxArticleListBean.getData().get(i).getName());
            }
        }
        mVp.setAdapter(mTabAdapter);
        mVp.setOffscreenPageLimit(5);
        mStb.setViewPager(mVp);
        mStb.setCurrentTab(0);
    }

    @Override
    public void onWxListFailed(String error) {
        //请求失败
        ToastUtils.show(error);
    }


    class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titlesList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}

