package com.wallet.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.cao.commons.base.PoliceApplication;


public class ToastUtil {

    private static Toast toast;// 修复工具类 当多次调用时，消息不会叠加在一起，体验很差 2016-12-20。

    public static void toast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void toast(Context context, int ResId) {
        if (toast == null) {
            toast = Toast.makeText(context, ResId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(ResId);
        }
        toast.show();
    }

    public static void toast(String msg) {
        if (PoliceApplication.getInstance() != null && !TextUtils.isEmpty(msg)) {
            Toast.makeText(PoliceApplication.getInstance(), msg, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static void toastLong(String msg) {
        if (PoliceApplication.getInstance() != null && !TextUtils.isEmpty(msg)) {
            Toast.makeText(PoliceApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
