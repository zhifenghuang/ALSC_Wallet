package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.View;

import com.alsc.alsc_wallet.R;
import com.common.activity.BaseActivity;
import com.common.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.trade.MoneyAccountFragment;
import com.alsc.alsc_wallet.fragment.trade.PaySettingFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class BuyCoinAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public BuyCoinAdapter(Context context) {
        super(R.layout.item_buy_coin);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setImageResource(R.id.ivAvatar, R.mipmap.ic_launcher_round);
        helper.getView(R.id.tvBuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSellCoinDialog();
            }
        });
        helper.getView(R.id.ivAvatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) mContext).gotoPager(MoneyAccountFragment.class);
            }
        });
    }

    private void showSellCoinDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_buy_coin_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView, R.id.tvCancel, R.id.tvOk);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvOk:
                        showSelectBuyTypeDialog();
                        break;
                }
            }
        });
        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
    }

    private void showSelectBuyTypeDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_select_pay_type_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView, R.id.tvCancel,
                        R.id.llAliPay, R.id.llWePay, R.id.llBank, R.id.llPayPal);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.llAliPay:
                    case R.id.llWePay:
                    case R.id.llBank:
                    case R.id.llPayPal:
                        ((BaseActivity) mContext).gotoPager(PaySettingFragment.class);
                        break;
                }
            }
        });
        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
    }
}
