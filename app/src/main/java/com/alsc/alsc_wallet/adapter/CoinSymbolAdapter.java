package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class CoinSymbolAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public CoinSymbolAdapter(Context context) {
        super(R.layout.item_coin_symbol);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setText(R.id.tvName, s);
    }
}
