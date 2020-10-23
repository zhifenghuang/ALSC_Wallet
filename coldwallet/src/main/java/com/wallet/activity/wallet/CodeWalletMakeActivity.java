package com.wallet.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityCodeWalletMakeBinding;
import com.wallet.event.LoginEvent;
import com.wallet.utils.ToastUtil;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.ColdWallet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CodeWalletMakeActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCodeWalletMakeBinding binding;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, com.wallet.activity.wallet.CodeWalletMakeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_make);
        binding.setClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next) {
            next();
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void next() {
        String userName = binding.etUserName.getText().toString();
        String pass = binding.etPassword.getText().toString();
        String passRetry = binding.etPasswordRetry.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.toast(getString(R.string.wc_code_input_account));
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtil.toast(getString(R.string.wc_code_input_wallet_pwd));
            return;
        }
        if (!pass.equals(passRetry)) {
            ToastUtil.toast(getString(R.string.create_wallet_pwd_confirm_input_tips));
            return;
        }
        if (pass.length() < 8) {
            ToastUtil.toast(getString(R.string.saving_wallet_password_length));
            return;
        }
        try {
            binding.btnNext.setEnabled(false);
            showWithStatus();
            UserBean userBean = new UserBean();
            userBean.setAccount(userName);
            userBean.setPassword(pass);
            userBean.setWalletType(1);
            userBean.setUserId(-1);
            ColdWallet wallet = ColdWalletUtil.createColdWallet(userName, pass);
            ColdWalletBackupActivity.startActivity(mContext, wallet, userBean);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.toast(getString(R.string.saving_wallet_failure));
        }finally {
            dismissDialog();
            binding.btnNext.setEnabled(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
