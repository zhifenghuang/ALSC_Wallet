package io.netflow.walletpro.activity;

import android.os.Bundle;
import android.text.TextUtils;

import io.netflow.walletpro.R;
import io.netflow.walletpro.fragment.LoginFragment;
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