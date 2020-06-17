package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.TradeFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SelectPayTypeAdapter extends BaseQuickAdapter<TradeFragment.TypeBean, BaseViewHolder> {

    private Context mContext;

    public SelectPayTypeAdapter(Context context) {
        super(R.layout.item_select_pay_type);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, TradeFragment.TypeBean item) {
        helper.setText(R.id.tvType, item.name);

    }


}
