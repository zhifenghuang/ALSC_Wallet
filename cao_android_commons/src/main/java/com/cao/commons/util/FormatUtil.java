package com.cao.commons.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy.MM.dd");

    /**
     * 格式化手机号
     *
     * @param phone
     * @return
     */
    public static String formatPhone(String phone) {
        if (phone == null) {
            return "";
        }
        if (!TextUtils.isEmpty(phone) && phone.length() > 7) {
            return phone.substring(0, 3) + "****" + phone.substring(7);
        }
        return phone;
    }

    /**
     * 格式化时间
     *
     * @param formatTime
     * @return
     */
    public static String getFormatTime(String formatTime) {
        if (formatTime == null) {
            formatTime = "";
        }
        if (!TextUtils.isEmpty(formatTime)) {
            try {
                Date time = sdf.parse(formatTime);
                if (time != null) {
                    return sdfYMD.format(time);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return formatTime;
    }

    /**
     * 格式化高度
     *
     * @param height
     * @return
     */
    public static String getFormatHeight(String height) {
        if (height == null) {
            height = "";
        }
        if (!TextUtils.isEmpty(height) && height.length() > 3) {
            StringBuilder builder = new StringBuilder();
            int times = height.length() / 3;
            int rest = height.length() % 3;
            if (rest > 0) {
                builder.append(height.substring(0,rest));
                builder.append(",");
            }
            for (int i = 0; i < times; i++) {
                builder.append(height.substring(i * 3+rest, i * 3 + 3+rest));
                if (i+1<times){
                    builder.append(",");
                }
            }

            return builder.toString();
        }
        return height;
    }
}
