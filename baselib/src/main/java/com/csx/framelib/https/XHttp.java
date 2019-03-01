package com.csx.framelib.https;

import java.util.Map;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: 网络请求，交给具体的网络请求类处理
 */
public class XHttp implements IHttpEngine {
    private static XHttp xHttp;
    private static IHttpEngine httpEngine;

    //需要在Application中初始化，网络引擎
    public static void initEngine(IHttpEngine engine) {
        httpEngine = engine;
    }

    //单例
    public static XHttp getInstance() {
        if (httpEngine == null)
            throw new NullPointerException(" httpEngine==null ; Please call XHttp.initEngine(); in your Application!");

        if (xHttp == null) {
            synchronized (XHttp.class) {
                if (xHttp == null) xHttp = new XHttp();
            }
        }

        return xHttp;
    }


    @Override
    public void get(String url, Map<String, Object> params, HttpCallBack callBack) {
        httpEngine.get(url, params, callBack);
    }

    @Override
    public void get(String url, Map<String, Object> params, long cacheForLong, HttpCallBack callBack) {
        httpEngine.get(url, params, cacheForLong, callBack);
    }

    @Override
    public void post(String url, Map<String, Object> params, HttpCallBack callBack) {
        httpEngine.post(url, params, callBack);
    }

    @Override
    public void post(String url, Map<String, Object> params, long cacheForLong, HttpCallBack callBack) {
        httpEngine.post(url, params, cacheForLong, callBack);
    }

    @Override
    public void cancelAll() {
        httpEngine.cancelAll();
    }
}
