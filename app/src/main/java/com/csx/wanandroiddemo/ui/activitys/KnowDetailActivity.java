package com.csx.wanandroiddemo.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.widgets.XActionBar;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseMvpActivity;
import com.csx.wanandroiddemo.base.mvp.BasePresenter;
import com.csx.wanandroiddemo.beans.KnowledgeBean;
import com.csx.wanandroiddemo.ui.fragments.KnowledgeDetailFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/28
 * @description: 知识体系，详情
 */
public class KnowDetailActivity extends BaseMvpActivity {
    private static final String TAG = "KnowDetailActivity";

    KnowledgeBean.DataBean detailBean;//传入的数据
    @BindView(R.id.xab_detail)
    XActionBar mXab;
    @BindView(R.id.stl)
    SlidingTabLayout mStl;
    @BindView(R.id.vp_detail)
    ViewPager mVp;

    private TabAdapter mVpAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public static void open(Activity activity, KnowledgeBean.DataBean detailBean) {
        Intent intent = new Intent(activity, KnowDetailActivity.class);
        intent.putExtra("detailBean", detailBean);
        activity.startActivity(intent);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onPre() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void initView() {
        detailBean = (KnowledgeBean.DataBean) getIntent().getSerializableExtra("detailBean");
        if (Judge.isEmpty(detailBean)) {
            ToastUtils.show("未知错误，请退出重试！");
            return;
        }

        mXab.setTitle(detailBean.getName());
        mXab.hasCloseBtn(this);

        for (int i = 0; i < detailBean.getChildren().size(); i++) {
            KnowledgeDetailFragment detailFragment = KnowledgeDetailFragment.getInstance(detailBean.getChildren().get(i).getId());
            fragmentList.add(detailFragment);
            titleList.add(detailBean.getChildren().get(i).getName());
        }

        mVpAdapter = new TabAdapter(getSupportFragmentManager());

        mVp.setAdapter(mVpAdapter);

        mStl.setViewPager(mVp);

        mStl.setCurrentTab(0);
        if (fragmentList.size() > 3)
            mVp.setOffscreenPageLimit(3);
    }

    @Override
    protected void initData() {

    }


    private class TabAdapter extends FragmentPagerAdapter {


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

