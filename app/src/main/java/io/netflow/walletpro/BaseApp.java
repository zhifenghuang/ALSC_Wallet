package io.netflow.walletpro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alsc.chat.manager.ChatManager;
import com.cao.commons.base.PoliceApplication;
import com.cao.commons.manager.ConfigManager;

import java.util.List;

/**
 * @author rainking
 */
@SuppressLint("Registered")
public class BaseApp extends PoliceApplication {

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


        ConfigManager.getInstance().setContext(this);
        ChatManager.getInstance().setContext(this);

        mActivityRecord = 0;
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                ++mActivityRecord;
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
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
            }
        });

    }

    public void initWebSocket(String token) {
        ChatManager.getInstance().initWebSocket(token);
    }

}