package com.alsc.alsc_wallet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alsc.alsc_wallet.http.ChatHttpMethods;
import com.alsc.alsc_wallet.http.HttpMethods;
import com.alsc.alsc_wallet.manager.ConfigManager;

import java.util.List;

/**
 * @author rainking
 */
@SuppressLint("Registered")
public class BaseApp extends Application {

    private static BaseApp mInstance;

    private int mActivityRecord;  //记录app是否处于app页面，0表示不在，1表示在


    public static synchronized BaseApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        int count = 0;
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
            if (runningAppProcessInfo.pid == pid) {
                if (runningAppProcessInfo.processName.equals("com.jnsh.alsc:pushcore")) {
                    return;
                }
                ++count;
            }
        }
        if (count > 1) {
            return;
        }
        mInstance = this;
        HttpMethods.getInstance().setContent(this);
        ChatHttpMethods.getInstance().setContent(this);
 //       Utils.init(this);
        ConfigManager.getInstance().setContext(this);

 //       CrashReport.initCrashReport(getApplicationContext(), "02cfa38619", false);
        mActivityRecord = 0;
        ConfigManager.getInstance().setAppRunning(false);
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                ++mActivityRecord;
                ConfigManager.getInstance().setAppRunning(true);
                ConfigManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                --mActivityRecord;
                ConfigManager.getInstance().setAppRunning(mActivityRecord != 0);
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                ConfigManager.getInstance().setCurrentActivityNull(activity);
            }
        });

    }

    public void initWebSocket(String token) {
        ConfigManager.getInstance().initWebSocket(token);
    }

}