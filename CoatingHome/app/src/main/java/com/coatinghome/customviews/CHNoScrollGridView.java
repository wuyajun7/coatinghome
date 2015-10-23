package com.coatinghome.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by wuyajun on 15/6/19.
 *
 * 自定义无滚动GridView
 */
public class CHNoScrollGridView extends GridView {
    public CHNoScrollGridView(Context context) {
        super(context);

    }

    public CHNoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}