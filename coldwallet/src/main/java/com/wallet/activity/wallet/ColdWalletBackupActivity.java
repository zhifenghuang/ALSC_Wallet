package com.wallet.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdWalletBackupBinding;
import com.wallet.widget.dialog.ColdWalletTextDialog;
import com.wallet.wallet.bean.ColdWallet;

/**
 * 备份
 */
public class ColdWalletBackupActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdWalletBackupBinding binding;
    private ColdWalletTextDialog mDialog;
    private ColdWallet mColdWallet;
    private UserBean mUserBean;

    public static void startActivity(Context context, ColdWallet wallet, UserBean userBean ) {
        Intent intent = new Intent(context, com.wallet.activity.wallet.ColdWalletBackupActivity.class);
        intent.putExtra("wallet", wallet);
        intent.putExtra("userBean", userBean);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_wallet_backup);
        binding.setClickListener(this);
        mColdWallet = (ColdWallet) getIntent().getSerializableExtra("wallet");
        mUserBean = (UserBean) getIntent().getSerializableExtra("userBean");
        if (mColdWallet != null) {
            StringBuilder builder = new StringBuilder();
            for (String str : mColdWallet.getMnemonicCode()) {
                builder.append(str);
                builder.append(" ");
            }
            binding.tvContent.setText(builder.toString());
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next) {
            next();
        } else if (id == R.id.iv_cover) {
            binding.ivCover.setVisibility(View.GONE);
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void next() {
        if (binding.ivCover.getVisibility()==View.VISIBLE){
            return;
        }
        if (mDialog == null) {
            mDialog = new ColdWalletTextDialog(mContext);
        }
        mDialog.show();
        mDialog.getmOkButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColdWalletVerifyActivity.startActivity(mContext,mColdWallet,mUserBean);
                mDialog.dismiss();
                finish();
            }
        });
        mDialog.getmCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

}
