package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class BuyCoinAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public BuyCoinAdapter(Context context) {
        super(R.layout.item_buy_sell);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setImageResource(R.id.ivAvatar, R.mipmap.ic_launcher_round);
    }
}
