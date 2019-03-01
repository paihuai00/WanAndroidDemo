package com.csx.wanandroiddemo.utils;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/28
 * @description:  有异常处理的 Rxbus (无背压)
 */
public class RxBus {

    private static volatile RxBus instance;
    private final Relay<Object> mBus;


    public RxBus() {
        this.mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getInstance(){
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance=Holder.BUS;
                }
            }
        }

        return instance;
    }

    public void post(Object object) {
        mBus.accept(object);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable(){
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

}
