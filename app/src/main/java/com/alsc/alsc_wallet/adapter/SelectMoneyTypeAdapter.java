package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.TradeFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SelectMoneyTypeAdapter extends BaseQuickAdapter<TradeFragment.TypeBean, BaseViewHolder> {

    private Context mContext;

    public SelectMoneyTypeAdapter(Context context) {
        super(R.layout.item_money_type);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, TradeFragment.TypeBean item) {
        helper.setImageResource(R.id.iv, item.icon)
                .setText(R.id.tvType, "(" + item.name + ")");

    }


}
