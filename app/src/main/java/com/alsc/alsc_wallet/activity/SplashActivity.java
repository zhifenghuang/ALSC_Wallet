package com.alsc.alsc_wallet.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.LoginFragment;
import com.cao.commons.manager.DataManager;
import com.common.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (TextUtils.isEmpty(DataManager.getInstance().getToken())) {
            gotoPager(LoginFragment.class);
        } else {
            gotoPager(MainActivity.class);
        }
        finish();
    }
}