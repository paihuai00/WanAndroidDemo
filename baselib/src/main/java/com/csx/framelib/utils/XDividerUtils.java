package com.csx.framelib.utils;

import android.content.Context;
import android.graphics.Color;

import com.csx.framelib.widgets.rv_adapters.DividerDecoration;


public class XDividerUtils {

    public static DividerDecoration getCommonDivider(Context context, int marginLeftSp, int marginRightSp) {
        return new DividerDecoration(Color.parseColor("#f3f3f3"), XDensityUtils.dp2px(context, 0.5f), XDensityUtils.dp2px(context, marginLeftSp), XDensityUtils.dp2px(context, marginRightSp));
    }

    public static DividerDecoration getCommonHeight10Divider(Context context, int color, int marginLeftSp, int marginRightSp) {
        return new DividerDecoration(color, XDensityUtils.dp2px(context, 10), XDensityUtils.dp2px(context, marginLeftSp), XDensityUtils.dp2px(context, marginRightSp));
    }
}
