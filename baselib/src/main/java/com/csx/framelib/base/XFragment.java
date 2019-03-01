package com.csx.framelib.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description:
 */
public abstract class XFragment extends Fragment {
    public View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPre();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() > 0) {
            view = inflater.inflate(getLayoutId(), container, false);
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();//初始化View

        initData();//处理数据
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initView();

    //该方法在  onCreate();用于初始化一些初始的东西，子类可根据需要复写
    public void onPre() {

    }
}
