package com.cao.commons.widget.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cao.commons.R;

public class ConfirmToast extends Toast {

    /**
     * Toast单例
     */
    private static ConfirmToast toast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ConfirmToast(Context context) {
        super(context);
    }

    /**
     * 初始化Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private static void initToast(Context context, CharSequence text, int status) {
        try {

            toast = new ConfirmToast(context);
            // 获取LayoutInflater对象
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.toast_confirm, null);

            // 吐司上的图片
            ImageView iv_image = layout.findViewById(R.id.iv_image);
            TextView tv_title = layout.findViewById(R.id.tv_title);
            tv_title.setText(text);
            if (status == 0) {
                iv_image.setImageResource(R.mipmap.icon_complete);
            } else {
                iv_image.setImageResource(R.mipmap.icon_attention_toast);
            }
            toast.setView(layout);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSuccess(Context context) {
        showToast(context, context.getString(R.string.confirm_toast_success), Toast.LENGTH_SHORT, 0);
    }

    public static void showFailure(Context context) {
        showToast(context,  context.getString(R.string.confirm_toast_failure),  Toast.LENGTH_SHORT, 1);
    }

    public static void showSuccess(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT, 0);
    }

    public static void showFailure(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT, 1);
    }

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param time    显示时长
     * @param imgType 图标状态
     */
    private static void showToast(Context context, CharSequence text, int time, int imgType) {
        // 初始化一个新的Toast对象
        initToast(context, text, imgType);

        // 设置显示时长
        if (time == Toast.LENGTH_LONG) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        // 显示Toast
        toast.show();
    }
}
