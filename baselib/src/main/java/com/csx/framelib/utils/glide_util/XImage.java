package com.csx.framelib.utils.glide_util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.csx.framelib.R;
import com.csx.framelib.utils.Judge;


public class XImage {

    /**
     * 加载图片，使用默认图片 R.drawable.zhanwei_full ( )
     *
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public static void load(Context context, ImageView imageView, Object imageUrl) {
        if (!Judge.isEmpty(imageUrl) && !Judge.isEmpty(imageView)) {
            GlideApp.with(context)
                    .load(!Judge.isEmpty(imageUrl) ? imageUrl : R.drawable.zhanwei_full)
                    .error(R.drawable.zhanwei_full)
                    .into(imageView);
        }
    }

    public static void load(Context context, ImageView imageView, Object imageUrl, int defaultImage) {
        if (!Judge.isEmpty(imageUrl) && !Judge.isEmpty(imageView)) {
            GlideApp.with(context)
                    .load(!Judge.isEmpty(imageUrl) ? imageUrl : defaultImage)
                    .placeholder(defaultImage)
                    .error(defaultImage)
                    .into(imageView);
        }
    }

    public static void load(Context context, ImageView imageView, Object imageUrl, Object transformation) {
        if (!Judge.isEmpty(imageUrl) && !Judge.isEmpty(imageView) && !Judge.isEmpty(transformation)) {
            GlideApp.with(context)
                    .load(!Judge.isEmpty(imageUrl) ? imageUrl : R.drawable.zhanwei_full)
                    .error(R.drawable.zhanwei_full)
                    .transform((BitmapTransformation) transformation)
                    .into(imageView);
        }
    }

    public static void load(Context context, ImageView imageView, Object imageUrl, Object transformation, int defaultImage) {
        if (!Judge.isEmpty(imageUrl) && !Judge.isEmpty(imageView) && !Judge.isEmpty(transformation)) {
            GlideApp.with(context)
                    .load(!Judge.isEmpty(imageUrl) ? imageUrl : defaultImage)
                    .error(defaultImage)
                    .transform((BitmapTransformation) transformation)
                    .into(imageView);
        }
    }

}
