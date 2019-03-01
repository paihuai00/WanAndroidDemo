package com.csx.framelib.widgets.rv_tools;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.csx.framelib.R;


/**
 * 默认的下拉刷新头部   可以按照这个例子 自定义头部
 */

public class HeaderView extends FrameLayout implements OnHeaderListener {

    public ImageView iv;

    //    private MediaPlayer mMediaPlayer;
    public HeaderView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.refresh_header, this, true);
        iv = findViewById(R.id.header);
//        mMediaPlayer=MediaPlayer.create(context, R.raw.audio);
    }

    /**
     * 下拉刷新
     *
     * @param scrollY
     */
    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        iv.setImageResource(R.drawable.ic_loading_1);
    }

    /**
     * 松开刷新
     *
     * @param scrollY
     */
    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {
    }

    /**
     * 准备刷新
     *
     * @param scrollY
     */
    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    /**
     * 正在刷新
     *
     * @param scrollY
     */
    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        iv.setImageResource(R.drawable.loading_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        if (!animationDrawable.isRunning())
            animationDrawable.start();
    }

    /**
     * 刷新成功
     *
     * @param scrollY
     * @param isRefreshSuccess 刷新的状态  是成功了 还是失败了
     */
    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        iv.setImageResource(R.drawable.loading_animation);//刷新完成显示图片
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        if (animationDrawable.isRunning())
            animationDrawable.stop();
//        if(!Judge.isEmpty(rmMediaPlayer) && !mMediaPlayer.isPlaying()) {
//                mMediaPlayer.stat();
//        }
    }

    /**
     * 取消刷新
     *
     * @param scrollY
     */
    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        iv.setImageResource(R.drawable.ic_loading_1);
    }
}
