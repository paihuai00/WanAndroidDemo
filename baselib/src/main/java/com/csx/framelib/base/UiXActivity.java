package com.csx.framelib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.csx.framelib.dialogs.LoadingDialog;
import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.widgets.ActivityStackManager;
import com.csx.framelib.widgets.DoubleClickHelper;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/17
 * @description: 继承自 XActivity ， 添加一些UI操作
 * 1，添加 toast 封装
 * 2，添加 加载 dialog
 * 3，点击两次，退出activity
 */
public abstract class UiXActivity extends XActivity {

    private BaseDialog loadingDialog;

    //显示加载中 dialog
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog.Builder(this)
                    .setMessage("加载中...")
                    .create();
        }
        if (!loadingDialog.isShowing()) loadingDialog.show();
    }

    //隐藏加载中 dialog
    public void hindLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    /**
     * 显示toast
     * {@link ToastUtils}
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (ToastUtils.isInit) {
            ToastUtils.show(msg);
        } else {
            Log.e("UiXActivity", "showToast : ToastUtils has not been initialized");
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();//如果没有初始化，就显示原来的
        }
    }

    //双击退出
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (ActivityStackManager.getInstance().isFinalActivity() && DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                }
            }, 300);
        } else {
            if (ActivityStackManager.getInstance().isFinalActivity()) {
                showToast("再按一次退出");
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
