package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class ImportWalletAddressAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public ImportWalletAddressAdapter(Context context) {
        super(R.layout.item_impot_wallet_address);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String item) {

    }
}