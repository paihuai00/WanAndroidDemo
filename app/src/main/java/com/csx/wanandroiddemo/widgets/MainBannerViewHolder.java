package com.csx.wanandroiddemo.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.csx.framelib.utils.glide_util.XImage;
import com.csx.framelib.widgets.banners.holder.MZViewHolder;
import com.csx.wanandroiddemo.R;
import com.csx.wanandroiddemo.beans.MainBannerBean;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/21
 * @description: banner  ViewHolder
 */
public class MainBannerViewHolder implements MZViewHolder<MainBannerBean.DataBean> {
    ImageView iv_single_banner;

    @Override
    public View createView(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.banner_single_image, null);
        iv_single_banner = mView.findViewById(R.id.iv_banner);
        return mView;
    }

    @Override
    public void onBind(Context context, int position, MainBannerBean.DataBean data) {
        XImage.load(context, iv_single_banner, data.getImagePath(), R.drawable.ic_default);
    }
}
