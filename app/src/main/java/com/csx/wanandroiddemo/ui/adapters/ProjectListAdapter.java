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

import java.util.ArrayList;
import java.util.List;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description:
 */
public class ProjectListAdapter extends XRecyclerViewAdapter<ArticlesBean> {
    public ProjectListAdapter(@NonNull RecyclerView mRecyclerView) {
        super(mRecyclerView, new ArrayList<ArticlesBean>(), R.layout.item_project_list);
    }

    @Override
    protected void bindData(XViewHolder holder, ArticlesBean data, int position) {
        XImage.load(getContext(),
                (ImageView) holder.getView(R.id.iv_thum),
                data.getEnvelopePic(),
                R.drawable.ic_default);

        holder.setText(R.id.tv_title, data.getTitle());
        holder.setText(R.id.tv_content, data.getDesc());
        holder.setText(R.id.tv_author_time, XDateUtils.formatDateTime(data.getPublishTime()) + "  " + data.getAuthor());
    }
}
