package com.csx.wanandroiddemo.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_adapters.XViewHolder;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.beans.KnowledgeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/21
 * @description: 知识结构 adapter
 */
public class KnowledgeAdapter extends XRecyclerViewAdapter<KnowledgeBean.DataBean> {

    public KnowledgeAdapter(@NonNull RecyclerView mRecyclerView) {
        super(mRecyclerView, new ArrayList<KnowledgeBean.DataBean>(), R.layout.item_knowledge);
    }

    @Override
    protected void bindData(XViewHolder holder, KnowledgeBean.DataBean data, int position) {
        holder.setText(R.id.tv_title, data.getName());

        StringBuilder stringBuilder = new StringBuilder();
        for (KnowledgeBean.DataBean.ChildrenBean c : data.getChildren()) {
            stringBuilder.append(c.getName() + "  ");
        }
        holder.setText(R.id.tv_detail, stringBuilder.toString());
    }
}
