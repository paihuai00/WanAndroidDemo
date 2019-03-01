package com.csx.framelib.base;

import android.util.Log;
import android.widget.Toast;

import com.csx.framelib.dialogs.LoadingDialog;
import com.csx.framelib.toast.ToastUtils;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: 继承自XFragment ，用于添加一些UI 操作
 */
public abstract class UiFragment extends XFragment {
    private BaseDialog loadingDialog;


    //显示加载中 dialog
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog.Builder(getActivity())
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
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();//如果没有初始化，就显示原来的
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
