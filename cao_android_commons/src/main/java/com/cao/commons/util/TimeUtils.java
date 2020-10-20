package com.cao.commons.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间工具类
 *
 * @author peidongxu
 */
public class TimeUtils {

    public static String format = "yyyy-MM-dd";
    public static String formatYMDHM = "yyyy-MM-dd HH:mm";
    public static String formatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    private static long lastClickTime;

    /**
     * 获取当前时间字符串
     *
     * @param format yyyy-MM-dd HH:mm:ss 获取的当前的时间的格式
     * @return
     */
    public static String getCurrertData(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String formatStr = sdf.format(new Date());
        return formatStr;
    }

    /**
     * 字符串转成时间戳
     *
     * @param fotmat yyyy-MM-dd HH:mm:ss
     * @param str
     */
    public static long getDataUnix(String fotmat, String str) {
        long date = 0;
        if (str != null && !TextUtils.isEmpty(str)) {
            try {
                date = new SimpleDateFormat(fotmat).parse(str).getTime() / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param fotmat yyyy-MM-dd HH:mm:ss
     * @param l
     */
    public static String getData(String fotmat, String l) {
        return new SimpleDateFormat(fotmat).format(new Date(Long.parseLong(l) * 1000));
    }

    /**
     * 获取当前星期
     *
     * @return
     */
    public static String getCurrertWeek() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(c.get(Calendar.DAY_OF_WEEK) - 1);
    }

    /**
     * 获取两个时间相差的分数
     *
     * @param beginTime 开始的时间
     * @param endTime   结束的时间
     * @return 时间天数
     */
    public static boolean getDateMillisecond(String beginTime, String endTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 输入日期的格式
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.getTimeInMillis() - cal2.getTimeInMillis() > 0;
    }

    /**
     * 获取两个时间相差的分数
     *
     * @param beginTime 开始的时间
     * @param endTime   结束的时间
     * @return 时间天数
     */
    public static long getDateMillisecondToLong(String beginTime, String endTime) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long second=0;//秒数差
        try {

            final Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

            one = df.parse(beginTime);
            c.setTime(one);
            two = df.parse(endTime);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            diff = time1 - time2;

            second = diff/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return second;
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param fotmat yyyy-MM-dd HH:mm:ss
     * @param l      时间戳
     */
    public static String timeStampToStr(String fotmat, String l) {
        return new SimpleDateFormat(fotmat).format(new Date(Long.parseLong(l) * 1000));
    }

    // 把日期转为字符串
    public static String converToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    // 把日期转为字符串
    public static String converToStringYMDHM(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(date);
    }

    // 把日期转为字符串
    public static String converToStringYMD(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    // 格式化字符串日期
    public static String getFotmatData(String format, String strDate) {
        return getData("yyyy-MM-dd HH:mm:ss", String.valueOf(getDataUnix(format, strDate)));
    }

    /**
     * 格式化字符串日期
     *
     * @param format_bef 格式化之前格式
     * @param format_end 格式化之后格式
     * @param strDate    格式的日期
     * @return
     */
    public static String getFotmatData(String format_bef, String format_end, String strDate) {
        return getData(format_end, String.valueOf(getDataUnix(format_bef, strDate)));
    }

    /**
     * Data转换为字符串
     */
    public static String getDateToString(String format, Date data) {
        return new SimpleDateFormat(format).format(data);
    }

    /**
     * 20 * 字符串转换成Date
     * 21 * @param str
     * 22 * @return date
     * 23
     */
    public static Date StrToDate(String str, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当前时间的月初时间
     *
     * @param format
     */
    public static String getDateEarlyMonth(String str, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(StrToDate(str, format));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDateToString(format, calendar.getTime());
    }

    /**
     * 获取当前时间
     */
    public static String getToData() {
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 获取当前时间
     */
    public static String getToNetData() {
        long l = System.currentTimeMillis();
        Date date = new Date(l + MILLIS_IN_DAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 获取明天的时间
     */
    public static String getDataTomorrow(String format) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        return getDateToString(format, calendar.getTime());
    }

    /**
     * 获取传入时间的第二天时间
     */
    public static String getDataTomorrow(String formatStr, String format) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(StrToDate(formatStr, format));
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        return getDateToString(format, calendar.getTime());
    }

    /*
     * 将秒数转为时分秒
     * */
    public static String MillisecondToHMS(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        return h + ":" + d + ":" + s + "";
    }


    /**
     * 防止多次点击按钮
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 700) {// 0.7秒以内点击只有最后一次有效
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
