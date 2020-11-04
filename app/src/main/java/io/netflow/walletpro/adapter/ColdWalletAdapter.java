package io.netflow.walletpro.adapter;

import android.content.Context;

import io.netflow.walletpro.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.common.bean.CoinSymbolBean;

import org.jetbrains.annotations.NotNull;

public class ColdWalletAdapter extends BaseQuickAdapter<CoinSymbolBean, BaseViewHolder> {

    private Context mContext;

    public ColdWalletAdapter(Context context) {
        super(R.layout.item_wallet);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, CoinSymbolBean item) {
        helper.setImageResource(R.id.ivCoinIcon, item.getResId())
                .setText(R.id.tvCoinName, item.getName())
                .setText(R.id.tvAddress, item.getAddress());
    }
}
