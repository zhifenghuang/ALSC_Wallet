package com.cao.commons.retrofit;

/**
 * Created by LiYang on 2018/8/7.
 */

public class HttpConstants {
    /*** 缓存参数 */
    public static final String PRAGMA = "Pragma";
    public static final String USER_AGENT = "User-Agent";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String PUBLIC_MAX_AGE = "public, max-age=";
    public static final String NO_CACHE = "no-cache";
    public static final String CACHE_CONTROL_NOCACHE = "Cache-Control: no-cache";
    public static final String PUBLIC_ONLY_IF_CACHED_MAX_STALE = "public, only-if-cached, max-stale=";

    /*** 缓存有效期 */
    public static final int CACHE_STALE_SHORT = 60;
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;
    public static final String INTERNET = PUBLIC_MAX_AGE + CACHE_STALE_SHORT;
    public static final String INTERNET_NO = PUBLIC_ONLY_IF_CACHED_MAX_STALE + CACHE_STALE_LONG;

    /*** 自定义错误码 */
    //网络异常
    public static final int NETWORK_EXCPTION = 1;
    //未知异常
    public static final int UNKNOWN_EXCPTION = 2;
    //数据空
    public static final int NETWORK_NODATA_EXCPTION = 3;

    public static final int FACE_EXCPTION = 6;
    /**次错误码要根据后台定义判断*/
    /*** FACE HTTP STATUS */
    public static final int FACE_ERROR_200 = 0;
    //客户端token失效，可直接退出登录
    public static final int FACE_ERROR_401 = 401;
    public static final int FACE_ERROR_3001 = 3001;
    // 用户没有该访问权限
    public static final int FACE_ERROR_403 = 403;
    //资源不存在
    public static final int FACE_ERROR_404 = 404;
    //求方法出错，检查GET，POST，PUT是否对应
    public static final int FACE_ERROR_405 = 405;
}
