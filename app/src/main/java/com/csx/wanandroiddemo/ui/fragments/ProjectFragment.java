package com.csx.wanandroiddemo.ui.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.widgets.XActionBar;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;
import com.csx.wanandroiddemo.beans.ProjectBean;
import com.csx.wanandroiddemo.ui.presneters.ProjectPresenter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/20
 * @description: 项目
 */
public class ProjectFragment extends BaseLazyMvpFragment<ProjectPresenter> implements ProjectPresenter.Inter {

    @BindView(R.id.stl)
    SlidingTabLayout mStl;
    @BindView(R.id.vp_project)
    ViewPager mVp;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    private TabAdapter mTabAdapter;

    public static ProjectFragment getInstance() {
        return new ProjectFragment();
    }


    @Override
    public void initViewLazy(View view) {
        presenter.getProjectTitleList();
    }

    @Override
    public ProjectPresenter getPresenter() {
        return new ProjectPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_project;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();

        mTabAdapter = new TabAdapter(getChildFragmentManager());


    }

    @Override
    public void onProjectTitleSuccess(ProjectBean projectBean) {
        fragmentList.clear();
        titleList.clear();
        for (int i = 0; i < projectBean.getData().size(); i++) {
            ProjectListFragment projectListFragment = ProjectListFragment.getInstance(projectBean.getData().get(i).getId());
            fragmentList.add(projectListFragment);
            titleList.add(projectBean.getData().get(i).getName());
        }

        if (fragmentList.size() >= 5) mVp.setOffscreenPageLimit(5);

        mVp.setAdapter(mTabAdapter);
        mStl.setViewPager(mVp);
        mStl.setCurrentTab(0);
    }

    @Override
    public void onProjectTitleFailed(String error) {
        ToastUtils.show(error);
    }


    class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}

