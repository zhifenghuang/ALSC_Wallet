package com.cao.commons.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 打电话工具类
 *
 * @author CJZ
 * @Time 2018/11/21
 */
public class CallPhoneUtils {

    /**
     * 使用手机系统直接拨打电话
     *
     * @param phone
     */
    public static void CallPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                context.startActivity(intent);
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        } else {
            //当前系统小于6.0，直接调用拍照
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
