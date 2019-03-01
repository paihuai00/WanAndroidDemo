package com.csx.wanandroiddemo.ui.activitys;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.widgets.XActionBar;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseActivity;
import com.csx.wanandroiddemo.base.BaseLazyMvpFragment;
import com.csx.wanandroiddemo.dbs.UserInfoDb;
import com.csx.wanandroiddemo.ui.fragments.KnowledgeFragment;
import com.csx.wanandroiddemo.ui.fragments.MainFragment;
import com.csx.wanandroiddemo.ui.fragments.NavFragment;
import com.csx.wanandroiddemo.ui.fragments.ProjectFragment;
import com.csx.wanandroiddemo.ui.fragments.WxFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 主Activity
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.fragment_group)
    FrameLayout mFlMain;
    @BindView(R.id.nav_bottom)
    BottomNavigationView mNavBottom;
    @BindView(R.id.xab_main)
    XActionBar mXab;

    //fragments
    private ArrayList<BaseLazyMvpFragment> mFragments;
    private KnowledgeFragment knowledgeFragment;
    private MainFragment mainFragment;
    private WxFragment wxFragment;
    private NavFragment navFragment;
    private ProjectFragment projectFragment;
    private int mLastFgIndex;

    @Override
    protected void onPre() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mXab.setTitle("主页");
        mXab.setLeftOne(R.drawable.ic_user, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoDb userInfoDb = SQLite.select().from(UserInfoDb.class).querySingle();
                if (userInfoDb == null || userInfoDb.getId() == 0) {
                    //未登录
                    ToastUtils.show("请先登录！");
                    LoginActivity.open(MainActivity.this);
                } else {
                    UserActivity.open(MainActivity.this);
                }
            }
        });
        mXab.setRightOne(R.drawable.ic_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("搜索");
            }
        });

        initFragments();

        initBottomNav();

    }

    //初始化 fragment
    private void initFragments() {
        if (mFragments == null) mFragments = new ArrayList<>();

        if (Judge.isEmpty(mainFragment)) {
            mainFragment = MainFragment.getInstance();
            mFragments.add(mainFragment);
        }

        if (Judge.isEmpty(knowledgeFragment)) {
            knowledgeFragment = KnowledgeFragment.getInstance();
            mFragments.add(knowledgeFragment);
        }

        if (Judge.isEmpty(wxFragment)) {
            wxFragment = WxFragment.getInstance();
            mFragments.add(wxFragment);
        }

        if (Judge.isEmpty(navFragment)) {
            navFragment = NavFragment.getInstance();
            mFragments.add(navFragment);
        }

        if (Judge.isEmpty(projectFragment)) {
            projectFragment = ProjectFragment.getInstance();
            mFragments.add(projectFragment);
        }
    }

    private void initBottomNav() {
        mNavBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG, "onNavigationItemSelected: " + item.getItemId());
                switch (item.getItemId()) {
                    case R.id.tab_main_pager:
                        mXab.setTitle("主页");
                        switchFragment(0);
                        break;
                    case R.id.tab_knowledge_hierarchy:
                        mXab.setTitle("知识体系");
                        switchFragment(1);
                        break;
                    case R.id.tab_wx_article:
                        mXab.setTitle("公众号");
                        switchFragment(2);
                        break;
                    case R.id.tab_navigation:
                        mXab.setTitle("导航");
                        switchFragment(3);
                        break;
                    case R.id.tab_project:
                        mXab.setTitle("项目");
                        switchFragment(4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mNavBottom.setSelectedItemId(R.id.tab_main_pager);
    }

    @Override
    protected void initData() {

    }

    private void switchFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(targetFg).commitAllowingStateLoss();
            ft.add(R.id.fragment_group, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }
}
