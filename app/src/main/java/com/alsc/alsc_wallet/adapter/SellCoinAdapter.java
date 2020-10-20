package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.View;
import com.alsc.alsc_wallet.R;
import com.common.activity.BaseActivity;
import com.common.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.trade.MoneyAccountFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SellCoinAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public SellCoinAdapter(Context context) {
        super(R.layout.item_sell_coin);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setImageResource(R.id.ivAvatar, R.mipmap.ic_launcher_round);
        helper.getView(R.id.tvSell).setOnClickListener(new View.OnClickListener() {
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
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_sell_coin_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView, R.id.tvCancel, R.id.tvOk);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId){
                    case R.id.tvOk:
                        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_sell_tip_dialog);
                        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
                            @Override
                            public void initView(View view) {
                                dialogFragment.setDialogViewsOnClickListener(view, R.id.tv1, R.id.tv2);
                            }

                            @Override
                            public void onViewClick(int viewId) {

                            }
                        });
                        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
                        break;
                }
            }
        });
        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
    }
}
