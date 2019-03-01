package com.csx.wanandroiddemo.https;

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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/19
 * @description:
 */
public interface ApiServices {

    //首页banner数据
    @GET("banner/json")
    Observable<MainBannerBean> getMainBannerData();

    //首页列表数据
    @GET("article/list/{page}/json")
    Observable<MainArticleBean> getMainArticleData(@Path("page") String curPage);

    //知识体系
    @GET("tree/json")
    Observable<KnowledgeBean> getKnowledge();

    //公众号列表
    @GET("wxarticle/chapters/json ")
    Observable<WxArticleListBean> getWxList();

    //公众号，详情(id = 具体的公众号的id)
    @GET("wxarticle/list/{id}/1/json")
    Observable<WxArticleDetailBean> getWxDetailList(@Path("id") int id);

    //导航
    @GET("navi/json")
    Observable<NavBeans> getNavDatas();

    //项目
    @GET("project/tree/json")
    Observable<ProjectBean> getProjectTitleBean();

    /**
     * 项目列表数据
     *
     * @param cid  分类的id，上面项目分类接口
     * @param page 页码：拼接在链接中，从1开始。
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<ProjectListBean> getProjectListBean(@Path("page") int page, @Query("cid") int cid);

    /**
     * 收藏的文章列表
     *
     * @param page
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<CollectionArticleListBean> getCollectionBeans(@Path("page") int page);


    /**
     * 收藏
     *
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<CollectionBean> doCollection(@Path("id") String id);

    /**
     * 取消收藏（文章列表）
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<CollectionBean> unCollection(@Path("id") int id);


    /**
     * 取消收藏(我的收藏)
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect/{id}/json")
    Observable<CollectionBean> unCollectionMine(@Path("id") int id, @Query("originId") int originId);


    /**
     * 登陆 接口
     *
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    Observable<LoginBean> getLoginBean(@Query("username") String username, @Query("password") String password);

    /**
     * 知识体系，详情
     * @param page
     * @param id
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<KnowledgeDeatilBean> getKnowledgeDetailData(@Path("page") int page, @Query("cid") int id);
}
