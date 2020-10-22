package com.wallet.activity.cold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.user.TransferListDetailBean;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityColdAssetsDetailBinding;
import com.wallet.retrofit.ColdInterface;
import com.wallet.retrofit.HttpInfoRequest;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;

/**
 * activity_cold_assets_detail
 */
public class ColdAssetsDetailActivity extends BaseActivity implements View.OnClickListener {
    private ActivityColdAssetsDetailBinding binding;
    private String mId;
    private String mSymbol;

    public static void startActivity(Context context, String id, String type) {
        Intent intent = new Intent(context, ColdAssetsDetailActivity.class);
        intent.putExtra("mId", id);
        intent.putExtra("symbol", type);
        context.startActivity(intent);
    }


    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_assets_detail);
        binding.setClickListener(this);
        mId = getIntent().getStringExtra("mId");
        mSymbol = getIntent().getStringExtra("symbol");

        getData();
    }

    private void getData() {
        ColdInterface.getInfo(mId, mSymbol, mContext, Tag, new HttpInfoRequest<TransferListDetailBean>() {
            @Override
            public void onSuccess(TransferListDetailBean model) {
                if (model != null) {
                    if (model.getType() == 1) {//类型1转账  2充值
                        binding.tvType.setText(getString(R.string.detail_discharge_money));
                        binding.tvMoney.setText("-" + model.getAmount());
                    } else {
                        binding.tvType.setText(getString(R.string.detail_charge_money));
                        binding.tvMoney.setText("+" + model.getAmount());
                    }
                    if (model.getStatus() == 0) {//状态 0 等待确认 1确认中  2已完成  5交易失败
                        binding.tvStatus.setText(getString(R.string.status_cold0));
                    } else if (model.getStatus() == 1) {//状态 0 等待确认 1确认中  2已完成  5交易失败
                        binding.tvStatus.setText(getString(R.string.status_cold1));
                    } else if (model.getStatus() == 2) {//状态 0 等待确认 1确认中  2已完成  5交易失败
                        binding.tvStatus.setText(getString(R.string.status_cold2));
                    } else if (model.getStatus() == 5) {//状态 0 等待确认 1确认中  2已完成  5交易失败
                        binding.tvStatus.setText(getString(R.string.status_cold3));
                    } else {
                        binding.tvStatus.setText("");
                    }
                    binding.tvTime.setText("" + model.getAdd_time());
                    String umc = "";
                    if ("ETH".equals(model.getSymbol()) || "ETHUSDT".equals(model.getSymbol()) || "alsc".equals(model.getSymbol())) {
                        umc = "ether";
                    } else {
                        umc = "btc";
                    }
                    binding.tvFee.setText("" + model.getFee() + " " + umc);
                    binding.tvTxid.setText("" + model.getTxid());
                    binding.tvReceive.setText("" + model.getTo());
                }
            }

            @Override
            public void onError(int eCode) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fl_receive) {
            Utils.copyData(mContext, binding.tvReceive.getText().toString());
            ToastUtil.toast(getString(R.string.user_copy_success));
        } else if (id == R.id.rl_hash) {
            Utils.copyData(mContext, binding.tvTxid.getText().toString());
            ToastUtil.toast(getString(R.string.user_copy_success));
        } else if (id == R.id.fl_back) {
            finish();
        }
    }
}