package com.csx.wanandroiddemo.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.csx.framelib.utils.XDateUtils;
import com.csx.framelib.utils.glide_util.XImage;
import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_adapters.XViewHolder;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.beans.CollectionArticleListBean;

import java.util.ArrayList;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/21
 * @description: 收藏
 */
public class CollectionArticleAdapter extends XRecyclerViewAdapter<CollectionArticleListBean.DataBean.DatasBean> {

    public CollectionArticleAdapter(@NonNull RecyclerView mRecyclerView) {
        super(mRecyclerView, new ArrayList<CollectionArticleListBean.DataBean.DatasBean>(), R.layout.item_collection_article);
    }

    @Override
    protected void bindData(XViewHolder holder, CollectionArticleListBean.DataBean.DatasBean data, int position) {
        holder.setText(R.id.tv_author, data.getAuthor());//作者

        holder.setText(R.id.tv_title, data.getTitle());//标题

        holder.setText(R.id.tv_time, XDateUtils.formatDateTime(data.getPublishTime()));//时间

        holder.setText(R.id.tv_style, data.getChapterName());

        holder.bindChildClick(R.id.iv_star);
    }
}
