package com.cao.commons.util;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * VIEW的显示隐藏动画
 *
 * @author CJZ
 * @Time 2018/11/19
 */
public class AnimationUtils {
    public static void showAnim(View view) {

        TranslateAnimation showAnim = new TranslateAnimation(android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 1.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(500);
        view.startAnimation(showAnim);
        view.setVisibility(View.VISIBLE);
    }

    public static void hideAnim(View view) {
        TranslateAnimation hideAnim = new TranslateAnimation(android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 1.0f);
        hideAnim.setDuration(500);
        view.startAnimation(hideAnim);
        view.setVisibility(View.GONE);
    }
}
