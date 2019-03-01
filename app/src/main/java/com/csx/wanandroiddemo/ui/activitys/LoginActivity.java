package com.csx.wanandroiddemo.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.Judge;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.base.BaseMvpActivity;
import com.csx.wanandroiddemo.beans.LoginBean;
import com.csx.wanandroiddemo.dbs.UserInfoDb;
import com.csx.wanandroiddemo.ui.presneters.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 登录页面
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginPresenter.Inter {


    @BindView(R.id.login_account_edit)
    EditText mLoginAccountEdit;
    @BindView(R.id.login_account_group)
    LinearLayout mLoginAccountGroup;
    @BindView(R.id.login_password_edit)
    EditText mLoginPasswordEdit;
    @BindView(R.id.login_password_group)
    LinearLayout mLoginPasswordGroup;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_or_tv)
    TextView mLoginOrTv;
    @BindView(R.id.login_register_btn)
    Button mLoginRegisterBtn;

    private String account;
    private String password;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onPre() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        account = "17610176618";
        password = 123456 + "";
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        hindLoadingDialog();
        UserInfoDb userInfoDb = new UserInfoDb();
        userInfoDb.setUsername(loginBean.getData().getUsername());
        userInfoDb.setChapterTops(loginBean.getData().getChapterTops());
        userInfoDb.setCollectIds(loginBean.getData().getCollectIds());
        userInfoDb.setEmail(loginBean.getData().getEmail());
        userInfoDb.setIcon(loginBean.getData().getIcon());
        userInfoDb.setId(loginBean.getData().getId());
        userInfoDb.setPassword(loginBean.getData().getPassword());
        userInfoDb.setType(loginBean.getData().getType());
        userInfoDb.save();
        ToastUtils.show("登录成功！");
        finish();
    }

    @Override
    public void onLoginFailed(String error) {
        hindLoadingDialog();
        ToastUtils.show(error);
    }

    @OnTextChanged({R.id.login_account_edit})
    public void onAccountEditTextChange(CharSequence s, int start, int before, int count) {
        account = s.toString();
    }

    @OnTextChanged({R.id.login_password_edit})
    public void onPwdEditTextChange(CharSequence s, int start, int before, int count) {
        password = s.toString();
    }

    @OnClick({R.id.login_btn, R.id.login_register_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (Judge.isEmpty(account)) {
                    ToastUtils.show("请输入账号！");
                    return;
                }

                if (Judge.isEmpty(password)) {
                    ToastUtils.show("请输入密码！");
                    return;
                }
                showLoadingDialog();
                presenter.doLogin(account, password);
                break;
            case R.id.login_register_btn:
                break;
        }
    }
}
