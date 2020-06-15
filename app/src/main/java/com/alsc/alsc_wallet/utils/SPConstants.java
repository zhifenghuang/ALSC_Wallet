package com.alsc.alsc_wallet.utils;

/**
 * SharedPreferences key
 *
 * @author Ycc
 * @version v1.0
 * @Time 2018-8-7
 */

public class SPConstants {

    // 是否测试模式
    public static final boolean DEBUG_MODE = false;
    public static final String CURRENT_VERSION_NAME = "2.0.9";
    public static final int CURRENT_VERSION_CODE = 10;

    public static final String J_SHOP = "caoJZDemo";
    /**
     * android key
     */
    public static final String ANDROID_PLATFORM = "android";
    public static final String ANDROID_REGION = "china";
    public static final String LIKE_APP_ID = "likeAppId";
    /**
     * weface key
     */
    public static final String WEFACE_TOKEN = "token";
    public static final String WEFACE_COOKIE = "Cookie";
    public static final String WEFACE_USER_ID = "userId";
    public static final String USERBASELOGINMODEL = "userInfo";
    public static final String WEFACEAPP_CACHE = "wefaceapp";


    //首页面判断是否登陆code
    public static final int MAIN_TO_LOGIN_REQUEST = 1;
    public static final int MAIN_TO_LOGIN_RESULT = 2;
    public static final int LOGIN_TO_REGISTER_REQUEST = 3;
    public static final int LOGIN_TO_REGISTER_RESULT = 4;

    //获取验证码的标签种类
    public static final String REGISTER_TAG = "reg";
    public static final String LOGIN_TAG = "login";
    public static final String FORGOT_TAG = "forgot";


    //接口参数
    public static final String ANDROID_LIST = "android_list";
    public static final String APP_BANNER = "app_banner";
    public static final String RECOMMEND = "recommend";
    public static final String ACCOUNT = "account";
    public static final String COVER = "cover";
    public static final String COMMENT_TAG = "comment_tag";

    //微信 支付宝 APPID
    public static final String WECHAT = "wx6334ffe17c658ddc";
    public static final String WECHAT_FLAG = "wechat";
    public static final String ALIPAY_FLAG = "alipay";
    public static final String BALANCE_FLAG = "balance";
    public static final int SDK_PAY_FLAG = 1;

    //发布活动
    public static final int RELEASE_ACTIVITY_RESULT = 1;
    public static final int SELECT_ADDRESS_REQUEST = 2;
    public static final int TICKET_SETTING_REQUEST = 3;
    public static final int TICKET_ADD_REQUEST = 7;
    public static final int TICKET_EDIT_REQUEST = 8;
    public static final int REGISTRATION_FORM_REQUEST = 4;
    public static final int SELECT_SET_ADDRESS_REQUEST = 5;
    public static final int SELECT_ACTIVITY_DETAIL_REQUEST = 6;


    //错误代码
    public static final int ERROR_CODE_INSUFFICIENT_BALANCE = 60021;//余额不足
    public static final int ERROR_USER_PASSWORD_WRONG = 3100001;//密码错误
    public static final int ERROR_USER_PAYMENT_PASSWORD_NUll = 3100002;//未设置密码
    public static final int ERROR_TOKEN_INVALID = 1104;//token失效


    //设置界面修改昵称
    public static final int CHANGE_NICKNAME_REQUEST = 1;
    public static final int CHANGE_NICKNAME_RESULT = 2;

    //评论
    public static final String TYPE_COMMENT = "5";
    public static final String TYPE_REPLY = "4";
    public static final String TYPE_VIDEO = "2";
    public static final String TYPE_IMAGE = "3";

    //票券编辑flag
    public static final String LOCAL = "local";
    public static final String ON_LINE = "on_line";
    public static final String LOCAL_ADD = "local_add";
    public static final String LOCAL_EDIT = "local_edit";
    public static final String ON_LINE_ADD = "on_line_add";
    public static final String ON_LINE_EDIT = "on_line_edit";

    //小区页面
    public static final int CENTER_SELECT_RESULT = 1;
    public static final int CENTER_SELECT_REQUEST = 2;
    public static final int CENTER_SELECT_FINISH_RESULT = 3;
    public static final String CENTER_COMMUNITY_ID = "communityid";
    public static final String CENTER_COMMUNITY_NAME = "communityName";


    //常量数字
    public static final int CONSTANT_ZEOR = 0;
    public static final int CONSTANT_ONE = 1;
    public static final int CONSTANT_TWO = 2;
    public static final int CONSTANT_THREE = 3;
    public static final int CONSTANT_FOUR = 4;


}
