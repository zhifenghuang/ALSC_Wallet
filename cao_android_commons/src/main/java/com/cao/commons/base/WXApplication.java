package com.cao.commons.base;


import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by Administrator on 2015/4/6.
 */
public class WXApplication extends PoliceApplication {
    public static BaseResp resp;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static BaseResp getResp() {
        return resp;
    }

    public static void setResp(BaseResp resp) {
        WXApplication.resp = resp;
    }
}
