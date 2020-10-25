package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdWalletCreateBinding;
import com.wallet.event.WalletAddEvent;
import com.wallet.utils.ToastUtil;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.JnWallet;
import com.wallet.wallet.bean.WalletType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 创建ALSC钱包
 */
public class ColdWalletCreateActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdWalletCreateBinding binding;
    private String mSymbol;

    public static void startActivity(Context context, String symbol) {
        Intent intent = new Intent(context, ColdWalletCreateActivity.class);
        intent.putExtra("symbol", symbol);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_wallet_create);
        binding.setClickListener(this);
        EventBus.getDefault().register(this);
        mSymbol = getIntent().getStringExtra("symbol");
        binding.tvToolbarTitle.setText(String.format(getString(R.string.c_create_wallet), mSymbol));
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
        String name = binding.etWalletName.getText().toString().trim();
        String pass = binding.etPassword.getText().toString().trim();
        String passRetry = binding.etPasswordRetry.getText().toString().trim();
        String remark = binding.etRemark.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            ToastUtil.toast(getString(R.string.wc_code_input_wallet));
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
            JnWallet coldWallet = null;
            if ("BTC".equals(mSymbol)) {
                coldWallet = ColdWalletUtil.getBtcColdWallet().createWallet(name, WalletType.BTC);
            } else if ("USDT-OMNI".equals(mSymbol)) {
                coldWallet = ColdWalletUtil.getBtcColdWallet().createWallet(name, WalletType.USDT_OMNI);
            } else if ("ETH".equals(mSymbol)) {
                coldWallet = ColdWalletUtil.getEthColdWallet().createWallet(name, pass, WalletType.ETH);
            } else if ("USDT-ERC20".equals(mSymbol)) {
                coldWallet = ColdWalletUtil.getEthColdWallet().createWallet(name, pass, WalletType.USDT_ERC20);
            }
//            else if ("A13".equals(mSymbol)) {
//                coldWallet = ColdWalletUtil.getEthColdWallet().createWallet(name, pass, WalletType.A13);
//            }
            ColdBackupMnemonicActivity.startActivity(mContext, mSymbol, coldWallet, pass, remark);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.toast(e.toString());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAddEvent event) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
