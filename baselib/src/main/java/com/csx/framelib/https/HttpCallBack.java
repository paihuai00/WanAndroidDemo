package com.csx.framelib.https;

import java.util.List;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: 网络请求，回调
 */
public abstract class HttpCallBack<Result> {
    public void onSuccess(Result result) {

    }

    public void onSuccess(List<Result> result) {

    }

    public abstract void onFailed(String error);

    public void onFailed(String error, int code) {

    }
}
