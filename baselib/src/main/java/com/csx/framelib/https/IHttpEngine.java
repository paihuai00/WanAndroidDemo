package com.csx.framelib.https;

import java.util.Map;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: 网络引擎
 */
public interface IHttpEngine {
    void get(String url, Map<String, Object> params, HttpCallBack callBack);

    void get(String url, Map<String, Object> params, long cacheForLong, HttpCallBack callBack);

    void post(String url, Map<String, Object> params, HttpCallBack callBack);

    void post(String url, Map<String, Object> params, long cacheForLong, HttpCallBack callBack);

    void cancelAll();
}
