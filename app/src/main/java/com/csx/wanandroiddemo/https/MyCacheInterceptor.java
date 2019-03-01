package com.csx.wanandroiddemo.https;

import com.csx.framelib.utils.XNetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/26
 * @description: 缓存拦截器
 */
public class MyCacheInterceptor implements Interceptor {
    private int maxCacheTime = 0;

    public MyCacheInterceptor() {
        this.maxCacheTime = 0;
    }

    public MyCacheInterceptor(int maxCacheTime) {
        this.maxCacheTime = maxCacheTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        //1,判断网络是否可以用
        if (!XNetworkUtils.isAvailable()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        } else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }

        Response response = chain.proceed(request);

        if (XNetworkUtils.isConnected()) {
            int maxAge = maxCacheTime;
            //有网络的时候，默认不缓存
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Cache-Control")
                    .removeHeader("Pragma")
                    .build();
        } else {
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }

        return response;
    }

}
