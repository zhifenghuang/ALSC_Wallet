package com.cao.commons.base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.cao.commons.util.ResolutionUtil;

//import com.baidu.mapapi.CoordType;
//import com.baidu.mapapi.SDKInitializer;

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

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        Snake.init(this);
        //百度地图初始化
//        SDKInitializer.initialize(PoliceApplication.newInstance());
//        SDKInitializer.setCoordType(CoordType.BD09LL);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        ResolutionUtil.getInstance().init(this);
    }

    /**
     * 视频缓存
     */
//    private HttpProxyCacheServer proxy;
//
//    public static HttpProxyCacheServer getProxy(Context context) {
//        PoliceApplication app = (PoliceApplication) context.getApplicationContext();
//        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
//    }
//
//    private HttpProxyCacheServer newProxy() {
//        return new HttpProxyCacheServer(this);
//    }

    /**
     * 三方sdk初始化尽量放在sdk管理里面，避免进app卡顿很久
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
