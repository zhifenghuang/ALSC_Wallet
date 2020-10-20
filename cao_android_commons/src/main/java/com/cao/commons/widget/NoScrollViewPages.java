package com.cao.commons.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class NoScrollViewPages extends ViewPager {

    public boolean isCanScroll = false;

    public NoScrollViewPages(Context context) {
        this(context, null);
    }

    public NoScrollViewPages(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    //第一种
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isCanScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isCanScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }
}
