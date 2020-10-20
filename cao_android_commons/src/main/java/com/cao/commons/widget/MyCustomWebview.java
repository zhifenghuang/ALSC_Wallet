package com.cao.commons.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;

import com.wangjing.androidwebview.CustomWebview;

public class MyCustomWebview extends CustomWebview {
    public MyCustomWebview(Context context) {
        super(getFixedContext(context));
    }

    public MyCustomWebview(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
    }

    public MyCustomWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
    }

    private static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.createConfigurationContext(new Configuration());
        } else {
            return context;
        }
    }
}
