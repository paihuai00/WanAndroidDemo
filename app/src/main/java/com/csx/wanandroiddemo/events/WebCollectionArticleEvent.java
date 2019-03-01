package com.csx.wanandroiddemo.events;

import org.greenrobot.eventbus.EventBus;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/28
 * @description: 网页，取消收藏
 */
public class WebCollectionArticleEvent {
    public static void post() {
        EventBus.getDefault().post(new WebCollectionArticleEvent());
    }
}
