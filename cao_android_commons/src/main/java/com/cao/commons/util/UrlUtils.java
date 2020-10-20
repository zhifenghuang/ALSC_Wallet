package com.cao.commons.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 2018/11/30.
 */

public class UrlUtils {
    private static final String acceptableSchemes[] = {
            "http:",
            "https:",
            "file:"
    };

    public static boolean urlHasAcceptableScheme(String url) {
        if (url == null) {
            return false;
        }

        for (int i = 0; i < acceptableSchemes.length; i++) {
            if (url.startsWith(acceptableSchemes[i])) {
                return true;
            }
        }
        return false;
    }
    public static String isImagesTrue(String posturl) throws IOException {
        URL url = new URL(posturl);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        urlcon.setRequestMethod("POST");
        urlcon.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded");
        if (urlcon.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println(HttpURLConnection.HTTP_OK + posturl
                    + ":posted ok!");
            return "200";
        } else {
            System.out.println(urlcon.getResponseCode() + posturl
                    + ":Bad post...");
            return "404";
        }
    }

    /**
     * 去除url的第一个字符
     * @param url
     * @return
     */
    public static String substring(String url){
        return url.substring(1, url.length());
    }

}
