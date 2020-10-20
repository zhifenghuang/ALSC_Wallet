package com.cao.commons.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.cao.commons.R;
import com.cao.commons.manager.ActivityManager;
import com.cao.commons.manager.DataManager;


public class Utils {

    public static void loginOutAndGoToLogin(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alsc://login"));
        context.startActivity(intent);
        loginOut();
    }

    public static void loginOutAndGoToType(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alsc://choosetype"));
        context.startActivity(intent);
        loginOut();
    }

    public static void loginOut(){
        DataManager.getInstance().loginOut();
        ActivityManager.getInstance().exit();
    }


    public static String getCodeMessage(Context context, int code) {
        int strId = context.getResources().getIdentifier("error_code_" + code, "string", context.getPackageName());
        if (strId != 0) {
            return context.getString(strId);
        } else {
            return context.getString(R.string.error_code_4290);
        }
    }
}
