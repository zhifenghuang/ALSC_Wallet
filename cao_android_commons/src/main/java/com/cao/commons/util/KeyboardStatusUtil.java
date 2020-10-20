package com.cao.commons.util;


import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.fragment.app.Fragment;

public class KeyboardStatusUtil {

    private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 100;
    private KeyboardVisibilityListener mVisibilityListener;

    boolean keyboardVisible = false;

    public void registerFragment(Fragment f) {
        registerView(f.getView());
    }

    public void registerActivity(Activity a) {
        registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public KeyboardStatusUtil registerView(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);

                int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) { // if more than recharge_select pixels, its probably a keyboard...
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(true);
                        }
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(false);
                        }
                    }
                }
            }
        });
        return this;
    }

    public KeyboardStatusUtil setmVisibilityListener(KeyboardVisibilityListener listener) {
        mVisibilityListener = listener;
        return this;
    }

    public interface KeyboardVisibilityListener {
        void onVisibilityChanged(boolean keyboardVisible);
    }

}
