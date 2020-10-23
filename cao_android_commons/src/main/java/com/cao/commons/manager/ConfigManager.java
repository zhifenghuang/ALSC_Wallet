package com.cao.commons.manager;

import android.content.Context;

public class ConfigManager {

    private static final String TAG = "ConfigManager";

    private Context mContext;

    private static ConfigManager INSTANCE;

    private ConfigManager() {

    }

    public static ConfigManager getInstance() {
        if (INSTANCE == null) {
            synchronized (TAG) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigManager();
                }
            }
        }
        return INSTANCE;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
