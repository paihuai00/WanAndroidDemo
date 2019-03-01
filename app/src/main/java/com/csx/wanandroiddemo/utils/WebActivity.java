package com.csx.wanandroiddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.widgets.XActionBar;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseMvpActivity;
import com.csx.wanandroiddemo.events.WebCollectionArticleEvent;
import com.csx.wanandroiddemo.ui.presneters.WebPresenter;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description:
 */
public class WebActivity extends BaseMvpActivity<WebPresenter> implements WebPresenter.Inter {
    private static final String TAG = "WebActivity";

    @BindView(R.id.ll_web)
    LinearLayout mLlWeb;
    @BindView(R.id.xab_web)
    XActionBar mXab;

    private String url;
    private String title;
    private int id;
    private int originId;
    private boolean isCollection;

    private AgentWeb agentWeb;

    private TextView shareTv;
    private TextView starTv;
    private boolean isShowStar = false;

    public static void open(Activity activity, String url, String title, int id, int originId, boolean isCollection) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        intent.putExtra("originId", originId);
        intent.putExtra("isCollection", isCollection);
        activity.startActivity(intent);
    }

    public static void open(Context context, String url, String title, int id) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void onPre() {
        isRegisterEventBus = true;
    }

    @Override
    protected void initView() {

        initReceived();

        initActionBar();

        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLlWeb, new LinearLayout.LayoutParams(-1, -1))// 设置 AgentWeb 的父控件 ， 这里的view 是 LinearLayout ， 那么需要传入 LinearLayout.LayoutParams
                .useDefaultIndicator()
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private void initActionBar() {
        mXab.setTitle(title);
        mXab.hasCloseBtn(this);

        mXab.setRightOne(R.drawable.ic_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("分享");
            }
        });

        mXab.setRightTwo(R.drawable.ic_heart_gray, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollection) {
                    presenter.unCollection(id, originId);
                } else {
                    presenter.doCollection("" + id);
                }
            }
        });

        shareTv = mXab.getRightOne();
        starTv = mXab.getRightTwo();

        if (isCollection) {
            starTv.setBackground(getResources().getDrawable(R.drawable.ic_heart_red));
        } else {
            starTv.setBackground(getResources().getDrawable(R.drawable.ic_heart_gray));
        }

    }

    private void initReceived() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        originId = getIntent().getIntExtra("originId", 0);
        isCollection = getIntent().getBooleanExtra("isCollection", false);
        if (Judge.isEmpty(url))
            throw new IllegalArgumentException("Please enter url !");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter(this);
    }

    @Override
    public void showCollectionSuccess() {
        if (isCollection) {
            ToastUtils.show("取消收藏成功！");
            WebCollectionArticleEvent.post();//发送取消收藏，event
            starTv.setBackground(getResources().getDrawable(R.drawable.ic_heart_gray));
        } else {
            ToastUtils.show("收藏成功！");
            starTv.setBackground(getResources().getDrawable(R.drawable.ic_heart_red));
        }
        isCollection = !isCollection;
    }

    @Override
    public void showCollectionFailed(String error) {
        ToastUtils.show(error);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unCollectionEvent(WebCollectionArticleEvent event) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }


    @Override
    public void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
