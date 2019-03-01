package com.csx.framelib.widgets;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csx.framelib.R;
import com.csx.framelib.utils.Judge;
import com.csx.framelib.utils.StringUtils;
import com.csx.framelib.utils.XDensityUtils;
import com.csx.framelib.utils.glide_util.XImage;


/**
 * 顶部ActionBar
 * Created by lixu on 2016/12/22.
 */

public class XActionBar extends RelativeLayout {

    private TextView mTvTitle;
    private TextView mLeftOne;
    private TextView mLeftTwo;
    private TextView mRightOne;
    private TextView mRightTwo;
    private ImageView ivDown, mRightTwoIv;
    private LinearLayout.LayoutParams params;
    private RelativeLayout mRlBg;
    private TextView mTvLine1;
    private TextView mTvLine2;

    public XActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_action_bar, this);
        mTvLine1 = (TextView) findViewById(R.id.tv_line1);
        mTvLine2 = (TextView) findViewById(R.id.tv_line2);
        mTvTitle = (TextView) findViewById(R.id.xaction_bar_title);
        mLeftOne = (TextView) findViewById(R.id.xaction_bar_left_one);
        mLeftTwo = (TextView) findViewById(R.id.xaction_bar_left_two);
        mRightOne = (TextView) findViewById(R.id.xaction_bar_right_one);
        mRightTwo = (TextView) findViewById(R.id.xaction_bar_right_two);
        mRightTwoIv = (ImageView) findViewById(R.id.xaction_bar_right_two_iv);
        mRlBg = (RelativeLayout) findViewById(R.id.rl_bg);
        ivDown = (ImageView) findViewById(R.id.iv_down);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public TextView getRightOne() {
        return mRightOne;
    }

    public TextView getRightTwo() {
        return mRightTwo;
    }

    public void setMultiTitle(String up, String down) {
        if (!Judge.isEmpty(up)) {
            mTvLine1.setText(up);
        }
        if (!Judge.isEmpty(down)) {
            mTvLine2.setText(down);
        }
    }

    public ImageView getIvDown() {
        return ivDown;
    }

    /**
     * 是否需要返回按钮
     */
    public void hasFinishBtn(final Activity activity) {
        setLeftOne(R.drawable.bt_title_back, new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        adjustTitleLength();
    }

    public void hasCloseBtn(final Activity activity) {
        setLeftOne(R.drawable.btn_close_dl, new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        adjustTitleLength();
    }

    /**
     * 设置左边第一个占位的图或者文字，并且设置点击事件
     */
    public void setLeftOne(int resId, OnClickListener listener) {
        addText(mLeftOne, "", null);
        if (resId == 0) {
            clear(mLeftOne);
        } else {
            addImage(mLeftOne, resId, listener);
        }
        adjustTitleLength();
    }

    public void setLeftOne(String text, OnClickListener listener) {
        addImage(mLeftOne, 0, null);
        if (StringUtils.isNull(text)) {
            clear(mLeftOne);
        } else {
            addText(mLeftOne, text, listener);
        }
        adjustTitleLength();
    }

    /**
     * 设置左边第二个占位的图或者文字，并且设置点击事件
     */
    public void setLeftTwo(int resId, OnClickListener listener) {
        addText(mLeftTwo, "", null);
        if (resId == 0) {
            clear(mLeftTwo);
        } else {
            addImage(mLeftTwo, resId, listener);
        }
        adjustTitleLength();
    }

    public void setLeftTwo(String text, OnClickListener listener) {
        addImage(mLeftTwo, 0, null);
        if (StringUtils.isNull(text)) {
            clear(mLeftTwo);
        } else {
            addText(mLeftTwo, text, listener);
        }
        adjustTitleLength();
    }

    public TextView getTitle() {
        return mTvTitle;
    }

    /**
     * 设置中间标题
     */
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        mTvTitle.setText(title);
        adjustTitleLength();
    }

    /**
     * 设置右边第一个占位的图或者文字，并且设置点击事件
     */
    public void setRightOne(int resId, OnClickListener listener) {
        setParams(mRightOne, 10);
        addText(mRightOne, "", null);
        if (resId == 0) {
            clear(mRightOne);
        } else {
            addImage(mRightOne, resId, listener);
        }
        adjustTitleLength();
    }

    public void setRightOne(int resId, OnClickListener listener, int w, int h) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRightOne.getLayoutParams();
        lp.width = XDensityUtils.dp2px(getContext(), w);
        lp.height = XDensityUtils.dp2px(getContext(), h);
        mRightOne.setLayoutParams(lp);
        addText(mRightOne, "", null);
        if (resId == 0) {
            clear(mRightOne);
        } else {
            addImage(mRightOne, resId, listener);
        }
        adjustTitleLength();
    }

    public void setRightTwo(int resId, OnClickListener listener, int w, int h) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRightTwo.getLayoutParams();
        lp.width = XDensityUtils.dp2px(getContext(), w);
        lp.height = XDensityUtils.dp2px(getContext(), h);
        mRightTwo.setLayoutParams(lp);
        addText(mRightTwo, "", null);
        if (resId == 0) {
            clear(mRightTwo);
        } else {
            addImage(mRightTwo, resId, listener);
        }
        adjustTitleLength();
    }

    public void setRightOne(String text, OnClickListener listener) {
        setParams(mRightOne, 15);
        addImage(mRightOne, 0, null);
        if (StringUtils.isNull(text)) {
            clear(mRightOne);
        } else {
            addText(mRightOne, text, listener);
        }
        adjustTitleLength();
    }

    /**
     * 设置右边第二个占位的图或者文字，并且设置点击事件
     */
    public void setRightTwo(int resId, OnClickListener listener) {
        setParams(mRightTwo, 15);
        addText(mRightTwo, "", null);
        if (resId == 0) {
            clear(mRightTwo);
        } else {
            addImage(mRightTwo, resId, listener);
        }
        adjustTitleLength();
    }

    public void setRightTwo(String text, OnClickListener listener) {
        setParams(mRightTwo, 15);
        addImage(mRightTwo, 0, null);
        if (StringUtils.isNull(text)) {
            clear(mRightTwo);
            clear(mRightTwoIv);
        } else {
            if (text.startsWith("http://")) {
                mRightTwoIv.setVisibility(VISIBLE);
                mRightTwo.setVisibility(GONE);
                addImage(mRightTwoIv, text, listener);
            } else {
                mRightTwoIv.setVisibility(GONE);
                mRightTwo.setVisibility(VISIBLE);
                addText(mRightTwo, text, listener);
            }
        }
        adjustTitleLength();
    }

    // 私有方法
    public void clear(TextView tv) {
        tv.clearAnimation();
        tv.setVisibility(GONE);
        tv.setBackgroundResource(0);
        tv.setText("");
        adjustTitleLength();
    }

    // 私有方法
    public void clear(ImageView iv) {
        iv.clearAnimation();
        iv.setVisibility(GONE);
        iv.setBackgroundResource(0);
        adjustTitleLength();
    }

    private void addImage(TextView tv, int resId, OnClickListener listener) {
        tv.clearAnimation();
        tv.setVisibility(VISIBLE);
        tv.setBackgroundResource(resId);
        tv.setOnClickListener(listener);
        adjustTitleLength();
    }

    private void addImage(ImageView iv, String url, OnClickListener listener) {
        iv.clearAnimation();
        iv.setVisibility(VISIBLE);
        XImage.load(getContext(), iv, url);
        iv.setOnClickListener(listener);
        adjustTitleLength();
    }

    private void addText(TextView tv, String text, OnClickListener listener) {
        tv.clearAnimation();
        tv.setVisibility(VISIBLE);
        tv.setText(text);
        tv.setOnClickListener(listener);
        adjustTitleLength();
    }

    private void setParams(TextView tv, int margin) {
        params.setMargins(0, 0, XDensityUtils.dp2px(getContext(), margin), 0);
        tv.setLayoutParams(params);
        adjustTitleLength();
    }

    public void setBg(int resId) {
        mRlBg.setBackgroundColor(resId);
    }

    public TextView getLeftOne() {
        return mLeftOne;
    }

    public void setVisible(boolean isLeft, boolean isOne, boolean isVisible) {
        if (isLeft) {
            if (isOne) {
                mLeftOne.clearAnimation();
                mLeftOne.setVisibility(isVisible ? VISIBLE : GONE);
            } else {
                mLeftTwo.clearAnimation();
                mLeftTwo.setVisibility(isVisible ? VISIBLE : GONE);
            }
        } else {
            if (isOne) {
                mRightOne.clearAnimation();
                mRightOne.setVisibility(isVisible ? VISIBLE : GONE);
            } else {
                mRightTwo.clearAnimation();
                mRightTwo.setVisibility(isVisible ? VISIBLE : GONE);
                mRightTwoIv.clearAnimation();
                mRightTwoIv.setVisibility(isVisible ? VISIBLE : GONE);
            }
        }

        adjustTitleLength();
    }

    private void adjustTitleLength() {
        int total = 0;
        if (mRightOne.getVisibility() == VISIBLE) {
            total += 1;
        }
        if (mRightTwo.getVisibility() == VISIBLE) {
            total += 1;
        }
        if (mLeftOne.getVisibility() == VISIBLE) {
            total += 1;
        }
        if (mLeftTwo.getVisibility() == VISIBLE) {
            total += 1;
        }
        switch (total) {
            case 0:
                mTvTitle.setMaxEms(15);
                break;
            case 1:
                mTvTitle.setMaxEms(12);
                break;
            case 2:
                mTvTitle.setMaxEms(8);
                break;
            case 3:
                mTvTitle.setMaxEms(5);
                break;
            case 4:
                mTvTitle.setMaxEms(3);
                break;
        }

    }

    public void setVisible(boolean isLeft, boolean isVisible) {
        if (isLeft) {
            mLeftOne.clearAnimation();
            mLeftOne.setVisibility(isVisible ? VISIBLE : GONE);
            mLeftTwo.clearAnimation();
            mLeftTwo.setVisibility(isVisible ? VISIBLE : GONE);
        } else {
            mRightOne.clearAnimation();
            mRightOne.setVisibility(isVisible ? VISIBLE : GONE);
            mRightTwo.clearAnimation();
            mRightTwo.setVisibility(isVisible ? VISIBLE : GONE);
            mRightTwoIv.clearAnimation();
            mRightTwoIv.setVisibility(isVisible ? VISIBLE : GONE);
        }
        adjustTitleLength();
    }

    public TextView getmRightOne() {
        return mRightOne;
    }

    public TextView getmRightTwo() {
        return mRightTwo;
    }

    public void setTilteClickListener(OnClickListener listener) {
        if (listener != null) {
            mTvTitle.setOnClickListener(listener);
        }
    }
}
