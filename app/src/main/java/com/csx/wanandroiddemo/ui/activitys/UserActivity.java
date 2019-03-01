package com.csx.wanandroiddemo.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.utils.SPUtils;
import com.csx.framelib.utils.glide_util.GlideCircleTransform;
import com.csx.framelib.utils.glide_util.XImage;
import com.csx.framelib.widgets.XActionBar;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.app.Constant;
import com.csx.wanandroiddemo.base.BaseMvpActivity;
import com.csx.wanandroiddemo.beans.LoginBean;
import com.csx.wanandroiddemo.dbs.UserInfoDb;
import com.csx.wanandroiddemo.ui.presneters.UserPresenter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 用户信息
 */
public class UserActivity extends BaseMvpActivity<UserPresenter> implements UserPresenter.Inter {
    private static final String TAG = "UserActivity";
    @BindView(R.id.iv_user)
    ImageView mIvUser;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.ll_collection)
    LinearLayout mLlCollection;
    @BindView(R.id.ll_set)
    LinearLayout mLlSet;
    @BindView(R.id.ll_about)
    LinearLayout mLlAbout;
    @BindView(R.id.ll_login_out)
    LinearLayout mLlLoginOut;
    @BindView(R.id.xab_user)
    XActionBar mXab;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, UserActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    protected void onPre() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        mXab.hasCloseBtn(this);
        mXab.setTitle("个人中心");

        UserInfoDb userInfoDb = SQLite.select().from(UserInfoDb.class).querySingle();

        if (!Judge.isEmpty(userInfoDb)) {
            //用户头像
            XImage.load(this, mIvUser, userInfoDb.getIcon(), new GlideCircleTransform(), R.drawable.ic_android64);

            mTvName.setText(userInfoDb.getUsername());
        }


    }

    @Override
    protected void initData() {

    }


    @Override
    public void onLoginSuccess(LoginBean loginBean) {

    }

    @Override
    public void onLoginFailed(String error) {

    }

    @OnClick({R.id.ll_collection, R.id.ll_set, R.id.ll_about, R.id.ll_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_collection:
                CollectionArticleActivity.open(UserActivity.this);
                break;
            case R.id.ll_set:
                break;
            case R.id.ll_about:
                break;
            case R.id.ll_login_out:
                clearInfo();
                ToastUtils.show("退出成功！");
                LoginActivity.open(UserActivity.this);
                finish();
                break;
        }
    }

    private void clearInfo() {
        SQLite.delete(UserInfoDb.class).execute();
        SPUtils.getSP().getStringSet(Constant.Cookie_SP, new HashSet<String>()).clear();
    }
}
