package com.cao.commons.retrofit;


import com.cao.commons.SPConstants;

public class PoliceBaseUrl {
    /**
     * 外网测试
     */
//    public static final String V1_TEXT = "http://www.alsc.space";
    private static final String V1_TEXT = SPConstants.DEBUG_MODE ? "https://youxiceshi.uwyoo.com/" : "https://main3.uwyoo.com/";


    public static String getBaseUrl() {
        return V1_TEXT;
    }


    /**
     * 外网测试
     */
    public static final String V1_TEXT_COLD = "http://cold1.cmzzz.top/";
    /**
     * 外网测试
     */
    public static final String V1_IMAGE = "";
    /**内网部署*/
    //public static final String V1_TEXT = "http://www.artxm.com.cn/";

}