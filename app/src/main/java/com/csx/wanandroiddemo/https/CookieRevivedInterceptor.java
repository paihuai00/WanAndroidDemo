package com.csx.wanandroiddemo.https;

import com.csx.framelib.utils.SPUtils;
import com.csx.wanandroiddemo.app.Constant;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 存储cookie的拦截器
 */
public class CookieRevivedInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            Set<String> cookiesSet = new HashSet<>();
            for (String cookie : originalResponse.headers("Set-Cookie")) {
                cookiesSet.add(cookie);
            }

            //存储在 sp 中
            SPUtils.getSP().edit().putStringSet(Constant.Cookie_SP, cookiesSet).apply();

        }
        return originalResponse;
    }
}
