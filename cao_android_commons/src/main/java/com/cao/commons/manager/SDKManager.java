package com.cao.commons.manager;


import android.content.Context;


public class SDKManager {

    public static void init(Context context) {
        initBugly(context);
        initNiMc(context);
        initUM(context);
    }

    private static void initUM(Context context) {
        /** 设置LOG开关，默认为false */
//        UMConfigure.setLogEnabled(true);
//        /** 初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口 */
//        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    private static void initBugly(Context context) {
       // CrashReport.initCrashReport(context);
    }

    /**
     * 初始化云信sdk
     */
    private static void initNiMc(Context context) {

    }

    /**
     * 本地数据库初始化
     */
    private static void initDaoData(Context context) {

    }
}
