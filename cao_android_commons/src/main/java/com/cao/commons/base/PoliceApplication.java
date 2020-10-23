package com.cao.commons.base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.cao.commons.bean.cold.ColdHqBean;
import com.cao.commons.util.ResolutionUtil;

/**
 * Application,根据内存大小生命周期，后面会把登陆user相关方法写在此类
 *
 * @author Y
 * @version v1.0
 * @Time 2018-11-10
 */

public class PoliceApplication extends Application {
    public static PoliceApplication instance = null;

    public static PoliceApplication newInstance() {
        return instance;
    }
    public static PoliceApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        ResolutionUtil.getInstance().init(this);
    }

    /**
     * 三方sdk初始化尽量放在sdk管理里面，避免进app卡顿很久
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private static ColdHqBean coldHqBean;

    public static ColdHqBean getColdHqBean() {
        return coldHqBean;
    }

    public static void setColdHqBean(ColdHqBean coldHqBean) {
        PoliceApplication.coldHqBean = coldHqBean;
    }
}
