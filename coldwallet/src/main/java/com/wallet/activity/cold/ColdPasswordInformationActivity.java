package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdPasswordInformationBinding;

/**
 * 密码提示信息
 */
public class ColdPasswordInformationActivity extends BaseActivity implements View.OnClickListener {

    private ActivityColdPasswordInformationBinding binding;
    private String mSymbol;
    private boolean isOpen;

    public static void startActivity(Context context, String symbol) {
        Intent intent = new Intent(context, ColdPasswordInformationActivity.class);
        intent.putExtra("symbol", symbol);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_password_information);
        binding.setClickListener(this);
        mSymbol = getIntent().getStringExtra("symbol");
        binding.tvMessage.setText(mSymbol);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_eye) {
            isOpen = !isOpen;
            if (isOpen) {
                binding.tvMessage.setTransformationMethod(HideReturnsTransformationMethod.getInstance());  //密码以明文显示
            } else {
                binding.tvMessage.setTransformationMethod(PasswordTransformationMethod.getInstance());  //密码以明文显示
            }
        } else if (id == R.id.fl_back) {
            finish();
        }
    }
}