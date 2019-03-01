package com.csx.framelib.widgets.rv_tools;


import android.content.Context;
import android.util.AttributeSet;

public class RefreshView extends RefreshLayout {

    public RefreshView(Context context) {
        super(context);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        HeaderView header = new HeaderView(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}
