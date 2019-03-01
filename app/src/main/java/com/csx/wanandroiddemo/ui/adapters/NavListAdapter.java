package com.csx.wanandroiddemo.ui.adapters;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.csx.framelib.toast.ToastUtils;
import com.csx.framelib.utils.XDensityUtils;
import com.csx.framelib.widgets.rv_adapters.XRecyclerViewAdapter;
import com.csx.framelib.widgets.rv_adapters.XViewHolder;

import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.beans.ArticlesBean;
import com.csx.wanandroiddemo.beans.NavBeans;
import com.csx.wanandroiddemo.utils.WebActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/22
 * @description: 导航栏，右侧
 */
public class NavListAdapter extends XRecyclerViewAdapter<NavBeans.DataBean> {

    private OnTabItemClick onTabItemClick;

    public NavListAdapter(@NonNull RecyclerView mRecyclerView) {
        super(mRecyclerView, new ArrayList<NavBeans.DataBean>(), R.layout.item_navigation);
    }

    public void setOnTabItemClick(OnTabItemClick onTabItemClick) {
        this.onTabItemClick = onTabItemClick;
    }

    @Override
    protected void bindData(XViewHolder helper, NavBeans.DataBean item, final int itemPosition) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.item_navigation_tv, item.getName());
        }
        final TagFlowLayout mTagFlowLayout = helper.getView(R.id.item_navigation_flow_layout);
        List<ArticlesBean> mArticles = item.getArticles();
        mTagFlowLayout.setAdapter(new TagAdapter<ArticlesBean>(mArticles) {
            @Override
            public View getView(FlowLayout parent, final int position, ArticlesBean feedArticleData) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                        mTagFlowLayout, false);
                if (feedArticleData == null) {
                    return null;
                }
                String name = feedArticleData.getTitle();
                tv.setPadding(XDensityUtils.dp2px(getContext(), 10), XDensityUtils.dp2px(getContext(), 10),
                        XDensityUtils.dp2px(getContext(), 10), XDensityUtils.dp2px(getContext(), 10));
                tv.setText(name);
                tv.setTextColor(randomColor());
                mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position1, FlowLayout parent) {

                        if (onTabItemClick != null)
                            onTabItemClick.onTabItemClick(view, itemPosition, position1, parent);
//                        startNavigationPager(view, position, parent, mArticles);
                        return true;
                    }
                });
                return tv;
            }
        });


    }

    public interface OnTabItemClick {
        void onTabItemClick(View view, int itemPosition, int childPosition, FlowLayout parent);
    }


    /**
     * 获取随机rgb颜色值
     */
    private int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red = random.nextInt(150);
        //0-190
        int green = random.nextInt(150);
        //0-190
        int blue = random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }
}
