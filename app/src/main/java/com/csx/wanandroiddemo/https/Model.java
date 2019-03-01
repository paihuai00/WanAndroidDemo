package com.csx.wanandroiddemo.https;

import com.csx.wanandroiddemo.base.mvp.BaseModel;
import com.csx.wanandroiddemo.beans.CollectionArticleListBean;
import com.csx.wanandroiddemo.beans.CollectionBean;
import com.csx.wanandroiddemo.beans.KnowledgeBean;
import com.csx.wanandroiddemo.beans.KnowledgeDeatilBean;
import com.csx.wanandroiddemo.beans.LoginBean;
import com.csx.wanandroiddemo.beans.MainArticleBean;
import com.csx.wanandroiddemo.beans.MainBannerBean;
import com.csx.wanandroiddemo.beans.NavBeans;
import com.csx.wanandroiddemo.beans.ProjectBean;
import com.csx.wanandroiddemo.beans.ProjectListBean;
import com.csx.wanandroiddemo.beans.WxArticleDetailBean;
import com.csx.wanandroiddemo.beans.WxArticleListBean;

import io.reactivex.Observable;

/**
 * create by cuishuxiang
 *
 * @date : 2019/1/18
 * @description: M 层，处理数据
 * <p>
 * 所有的数据 m层 处理都在这里
 */
public class Model extends BaseModel {

    //首页banner
    public Observable<MainBannerBean> getMainBanner() {
        return apiServices.getMainBannerData();
    }

    //首页列表数据
    public Observable<MainArticleBean> getMainArticle(int page) {
        return apiServices.getMainArticleData(page + "");
    }

    //知识体系列表
    public Observable<KnowledgeBean> getKnowledge() {
        return apiServices.getKnowledge();
    }

    //公众号列表
    public Observable<WxArticleListBean> getWxList() {
        return apiServices.getWxList();
    }

    //公众号详情列表
    public Observable<WxArticleDetailBean> getWxDetailList(int id) {
        return apiServices.getWxDetailList(id);
    }

    //导航
    public Observable<NavBeans> getNavData() {
        return apiServices.getNavDatas();
    }

    //项目
    public Observable<ProjectBean> getProjectTitleBean() {
        return apiServices.getProjectTitleBean();
    }

    //项目列表
    public Observable<ProjectListBean> getProjectListBean(int page, int cid) {
        return apiServices.getProjectListBean(page, cid);
    }

    //收藏
    public Observable<CollectionBean> doCollection(String id) {
        return apiServices.doCollection(id);
    }

    //取消收藏（文章列表中，点击取消收藏）
    public Observable<CollectionBean> unCollection(int id) {
        return apiServices.unCollection(id);
    }

    //取消收藏（我的收藏列表中，点击取消收藏）
    public Observable<CollectionBean> unCollectionMine(int id,int originId) {
        return apiServices.unCollectionMine(id,originId);
    }

    //收藏文章列表
    public Observable<CollectionArticleListBean> getCollectionBean(int page) {
        return apiServices.getCollectionBeans(page);
    }

    //登录
    public Observable<LoginBean> getLoginBean(String username, String password) {
        return apiServices.getLoginBean(username, password);
    }


    //知识体系详情
    public Observable<KnowledgeDeatilBean> getKnowledgeDetailData(int page, int cid) {
        return apiServices.getKnowledgeDetailData(page, cid);
    }
}
