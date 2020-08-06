package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.bean.CoinSymbolBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class CoinSymbolZichanAdapter extends BaseQuickAdapter<CoinSymbolBean, BaseViewHolder> {

    private Context mContext;

    public CoinSymbolZichanAdapter(Context context) {
        super(R.layout.item_coin_zichan);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, CoinSymbolBean item) {
        helper.setImageResource(R.id.ivCoinIcon, item.getResId())
                .setText(R.id.tvCoinName, item.getName())
                .setText(R.id.tvAddress, item.getAddress());
    }
}
