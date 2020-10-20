package com.cao.commons.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 跳转类，只需配置重要页面
 */

public class PoliceRouter {
    /**
     * 跳转登录页面
     */
    public static void startLogin(Context context) {
        try {
            context.startActivity(new Intent("police_LoginActivity", Uri.parse("shop://news003/jumpParams")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转新闻详情页
     */
    public static void startNewsDetails(Context context, String id) {
        try {
            context.startActivity(new Intent("police_NewsDetailActivity", Uri.parse("police://news001/jumpParams?id=" + id)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转受理单页面
     */
    public static void startFromDetails(Context context, String id,int position) {
        try {
            context.startActivity(new Intent("acceptance_AcceptanceFromActivity", Uri.parse("police://news002/jumpParams?id=" + id+"&position="+position)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 跳转受理单页面
     */
//    public static void startFromDetails(Context context, String id) {
//        try {
//            context.startActivity(new_message2 Intent("acceptance_AcceptanceFromActivity", Uri.parse("police://news002/jumpParams?id=" + id)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 跳转执法规范详情页面
     */
    public static void startLawDetails(Context context, String catalogId, String publishTimeString, String publishBy, String keyword,String articleInfoTitle,String showParagraphIds) {
        try {
            context.startActivity(new Intent("law_LawEnforcementNormsDetailActivity", Uri.parse("police://news004/jumpParams?id=" + catalogId +
                    "&publishTimeString=" + publishTimeString +
                    "&publishBy=" + publishBy +
                    "&keyword=" + keyword+
                    "&articleInfoTitle=" + articleInfoTitle+
                    "&showParagraphIds=" + showParagraphIds
            )));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转处警单页面
     */
    public static void startPoliceList(Context context, String id) {
        try {
            context.startActivity(new Intent("police_PoliceListActivity", Uri.parse("police://news005/jumpParams?id=" + id)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转处警单页面
     */
    public static void startMessageDetail(Context context, String id) {
        try {
            context.startActivity(new Intent("message_MessageActivity", Uri.parse("police://news006/jumpParams?id=" + id)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
