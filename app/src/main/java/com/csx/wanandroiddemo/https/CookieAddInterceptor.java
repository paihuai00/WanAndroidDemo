package com.csx.wanandroiddemo.https;

import com.csx.framelib.utils.SPUtils;
import com.csx.wanandroiddemo.app.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 将存储的cookie，添加到url中
 */
public class CookieAddInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> cookiesSet = (HashSet) SPUtils.getSP().getStringSet(Constant.Cookie_SP, new HashSet<String>());

        for (String cooike : cookiesSet) {
            builder.addHeader("Cookie", cooike);
        }

        return chain.proceed(builder.build());
    }
}
