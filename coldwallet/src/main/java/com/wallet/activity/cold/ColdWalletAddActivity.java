package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdWalletAddBinding;
import com.wallet.widget.dialog.WalletAddDialog;
import com.wallet.event.WalletAddEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 添加钱包
 */
public class ColdWalletAddActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdWalletAddBinding binding;
    private WalletAddDialog addDialog;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ColdWalletAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_wallet_add);
        binding.setClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_alsc) {
            showDialog("A13");
        } else if (id == R.id.ll_btc) {
            showDialog("BTC");
        } else if (id == R.id.ll_eth) {
            showDialog("ETH");
        } else if (id == R.id.ll_usdto) {
            showDialog("USDT-OMNI");
        } else if (id == R.id.ll_usdte) {
            showDialog("USDT-ERC20");
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void showDialog(String symbol) {
        if (addDialog == null) {
            addDialog = new WalletAddDialog(mContext);
        }
        addDialog.showDialog(symbol);
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
