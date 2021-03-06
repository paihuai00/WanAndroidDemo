package com.csx.framelib.base;

import android.app.Dialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class BaseDialogFragment extends DialogFragment {

    private BaseDialog mDialog;

    private static String sShowTag;
    private static long sLastTime;

    /**
     * 父类同名方法简化
     */
    public void show(Fragment fragment) {
        show(fragment.getFragmentManager(), fragment.getClass().getName());
    }

    /**
     * 父类同名方法简化
     */
    public void show(FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), activity.getClass().getName());
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!isRepeatedShow(tag)) {
            super.show(manager, tag);
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (!isRepeatedShow(tag)) {
            return super.show(transaction, tag);
        }
        return -1;
    }

    /**
     * 根据 tag 判断这个 Dialog 是否重复显示了
     *
     * @param tag           Tag标记
     */
    protected boolean isRepeatedShow(String tag) {
        boolean result = tag.equals(sShowTag) && SystemClock.uptimeMillis() - sLastTime < 500;
        sShowTag = tag;
        sLastTime = SystemClock.uptimeMillis();
        return result;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (mDialog != null) {
            return mDialog;
        }else {
            // 不使用 Dialog，替换成 BaseDialog 对象
            return mDialog = new BaseDialog(getActivity());
        }
    }

    public void setDialog(BaseDialog dialog) {
        mDialog = dialog;
    }

    @Override
    public Dialog getDialog() {
        if (mDialog != null) {
            return mDialog;
        }
        return super.getDialog();
    }

    public static class Builder<B extends BaseDialog.Builder> extends BaseDialog.Builder<B> {

        private FragmentActivity mActivity;
        private BaseDialogFragment mDialogFragment;

        public Builder(FragmentActivity activity) {
            super(activity);
            mActivity = activity;
        }

        public Builder(FragmentActivity activity, int themeResId) {
            super(activity, themeResId);
            mActivity = activity;
        }

        /**
         * 获取当前 Activity 对象（仅供子类调用）
         */
        protected FragmentActivity getActivity() {
            return mActivity;
        }

        /**
         * 获取当前 DialogFragment 对象（仅供子类调用）
         */
        protected BaseDialogFragment getDialogFragment() {
            return mDialogFragment;
        }

        /**
         * 获取 Fragment 的标记
         */
        protected String getFragmentTag() {
            return getClass().getName();
        }

        // 重写父类的方法（仅供子类调用）
        @Override
        protected void dismiss() {
            mDialogFragment.dismiss();
        }

        @Override
        public BaseDialog show() {
            BaseDialog dialog = create();
            mDialogFragment = new BaseDialogFragment();
            mDialogFragment.setDialog(dialog);
            mDialogFragment.show(mActivity.getSupportFragmentManager(), getFragmentTag());
            // 解决 Dialog 设置了而 DialogFragment 没有生效的问题
            mDialogFragment.setCancelable(isCancelable());
            return dialog;
        }
    }
}