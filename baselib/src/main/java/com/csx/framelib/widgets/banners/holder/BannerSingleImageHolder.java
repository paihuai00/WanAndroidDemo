package com.csx.framelib.widgets.banners.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.csx.framelib.R;
import com.csx.framelib.utils.glide_util.XImage;


/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/30
 * @description: 轮播图，单个图片 holder
 */
public class BannerSingleImageHolder implements MZViewHolder<String> {
    ImageView iv_single_banner;

    @Override
    public View createView(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.banner_single_image, null);
        iv_single_banner = mView.findViewById(R.id.iv_banner);
        return mView;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        XImage.load(context,
                iv_single_banner,
                data,
                R.drawable.zhanwei_full
        );

    }
}
