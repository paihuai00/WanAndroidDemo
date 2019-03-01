package com.csx.wanandroiddemo.https;

import android.text.TextUtils;

import com.csx.wanandroiddemo.app.AppConfig;
import com.csx.wanandroiddemo.app.MyApplication;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/21
 * @description: Retrofit 管理
 * <p>
 * 注意：项目域名有2个的时候，可以调用 RetrofitManager.getApiService(Class<?> cls, String baseUrl); 传入相应的api.class，和 baseUrl
 */
public class RetrofitManager {
    private static OkHttpClient okHttpClient;
    private static long retryTimeOut = 60;
    private static long timeOut = 60;
    private static long readTimeOut = 60;
    private static long cacheSize = 20 * 1024 * 1024;//缓存大小为：20m
    private static File fileCache = new File(MyApplication.instance.getCacheDir(), "net_cache");//缓存路径

    //默认Retrofit实例
    private static Retrofit normalRetrofit;
    private static String baseUrl = AppConfig.BaseUrl;//需要设置baseUrl

    private static Retrofit otherRetrofit;

    private static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    //如果api比较多的时候，可以考虑通过map、存储创建的apis
//    private Map<Class<?>, Object> apis = new HashMap<>();

    /**
     * 默认的
     *
     * @return
     */
    public static ApiServices getInstance() {
        if (TextUtils.isEmpty(baseUrl))
            throw new IllegalArgumentException("RetrofitManager ： Please set BaseUrl！");

        initOkhttpClient();

        normalRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return normalRetrofit.create(ApiServices.class);
    }


    /**
     * 得到 不同 baseUrl，
     *
     * @param cls
     * @param baseUrl
     * @return
     */
    public static Class<?> getApiService(Class<?> cls, String baseUrl) {
        if (TextUtils.isEmpty(baseUrl))
            throw new IllegalArgumentException("RetrofitManager ： Please set BaseUrl！");

        initOkhttpClient();

        otherRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return (Class<?>) otherRetrofit.create(cls);
    }


    /**
     * 初始化OkHttpClient
     */
    private static void initOkhttpClient() {
        if (okHttpClient == null) {
            //打印http请求
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogInterceptor());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);//打印请求体

            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(retryTimeOut, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .cache(new Cache(fileCache, cacheSize))//缓存
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    .readTimeout(readTimeOut, TimeUnit.SECONDS)
//                    .cookieJar(new CookieJar() {//存储 cookie
//                        @Override
//                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                            /**
//                             * key：url的host地址
//                             * value：具体的cookies
//                             */
//                            cookieStore.put(url.host(), cookies);
//                        }
//
//                        @Override
//                        public List<Cookie> loadForRequest(HttpUrl url) {
//                            List<Cookie> cookies = cookieStore.get(url.host());
//                            //判断cookies，是否为null
//                            return cookies == null ? new ArrayList<Cookie>() : cookies;
//                        }
//                    })
                    .addNetworkInterceptor(logInterceptor)//用于打印网络请求
                    .addInterceptor(new CookieAddInterceptor())//添加拦截器 存储/添加 cookie
                    .addInterceptor(new CookieRevivedInterceptor())//可以在这里添加拦截器
//                    .addNetworkInterceptor(new MyCacheInterceptor())
                    .build();
        }
    }

}
