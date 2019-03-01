package com.csx.wanandroiddemo.base.mvp;


import com.csx.wanandroiddemo.https.ApiServices;
import com.csx.wanandroiddemo.https.RetrofitManager;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: M层，可以在这里加一些base处理
 */
public class BaseModel {
    public ApiServices apiServices = RetrofitManager.getInstance();
}
