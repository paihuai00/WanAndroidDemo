package com.csx.wanandroiddemo.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.csx.framelib.utils.XDateUtils;
import com.csx.framelib.utils.glide_util.XImage;
import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_adapters.XViewHolder;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.beans.ArticlesBean;
import com.csx.wanandroiddemo.beans.MainArticleBean;

import java.util.ArrayList;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/21
 * @description: 知识体系adapter
 */
public class KnowledgeArticleAdapter extends XRecyclerViewAdapter<ArticlesBean> {

    public KnowledgeArticleAdapter(@NonNull RecyclerView mRecyclerView) {
        super(mRecyclerView, new ArrayList<ArticlesBean>(), R.layout.item_main_article);
    }

    @Override
    protected void bindData(XViewHolder holder, ArticlesBean data, int position) {
        holder.setText(R.id.tv_author, data.getAuthor());//作者

        holder.setText(R.id.tv_title, data.getTitle());//标题

        holder.setText(R.id.tv_time, XDateUtils.formatDateTime(data.getPublishTime()));//时间

        holder.setText(R.id.tv_style, data.getSuperChapterName());

        holder.bindChildClick(R.id.iv_star);//收藏

        XImage.load(getContext(),
                (ImageView) holder.getView(R.id.iv_star),
                data.isCollect() ? R.drawable.ic_heart_red : R.drawable.ic_heart_gray);

    }
}
