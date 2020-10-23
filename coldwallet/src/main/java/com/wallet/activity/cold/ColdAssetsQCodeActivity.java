package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.util.QRCodeUtil;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdAssetsQcodeBinding;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;

/**
 * 转账二维码
 */
public class ColdAssetsQCodeActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdAssetsQcodeBinding binding;
    private String mSymbol;
    private int chooseErc = 3;
    private String mAddress;

    public static void startActivity(Context context, String symbol,String address) {
        Intent intent = new Intent(context, ColdAssetsQCodeActivity.class);
        intent.putExtra("symbol", symbol);
        intent.putExtra("address", address);
        context.startActivity(intent);
    }


    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_assets_qcode);
        binding.setClickListener(this);
        mSymbol = getIntent().getStringExtra("symbol");
        mAddress = getIntent().getStringExtra("address");

        binding.tvToolbarTitle.setText(mSymbol.toUpperCase() + getString(R.string.my_collect));
        binding.tvMessage.setText(String.format(getString(R.string.c_wallet_qcode_tips),mSymbol));
        binding.ivImage.setImageResource(Utils.getImageResource3(mSymbol));
        setQcode();
    }

    private void setQcode() {
        Bitmap bitmap = QRCodeUtil.createQRImage(mContext, mAddress, null);
        binding.ivQcode.setImageBitmap(bitmap);
        binding.tvUrl.setText(mAddress);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_copy) {
            if (!TextUtils.isEmpty(binding.tvUrl.getText().toString())) {
                Utils.copyData(mContext, binding.tvUrl.getText().toString());
                ToastUtil.toast(getString(R.string.user_copy_success));
            }
        } else if (id == R.id.btn_next || id == R.id.fl_back) {
            finish();
        }
    }
}
