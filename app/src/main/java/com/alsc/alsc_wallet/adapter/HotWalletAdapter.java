package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.cao.commons.bean.AssetsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class HotWalletAdapter extends BaseQuickAdapter<AssetsBean.ItemBean, BaseViewHolder> {

    private Context mContext;

    public HotWalletAdapter(Context context) {
        super(R.layout.item_wallet);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, AssetsBean.ItemBean item) {

        String name = item.getName().toLowerCase();
        if (name.contains("-")) {
            name = name.split("-")[0];
        }
        try {
            int drawableId = mContext.getResources().getIdentifier("wallet_" + name, "drawable", mContext.getPackageName());
            helper.setImageResource(R.id.ivCoinIcon, drawableId);
        } catch (Exception e) {

        }
        helper.setText(R.id.tvCoinName, item.getName())
                .setText(R.id.tvAddress, item.getAddress_wallet())
                .setText(R.id.tvTotal, "≈ $ " + item.getTotal());
    }
}
