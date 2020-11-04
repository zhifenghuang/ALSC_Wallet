package io.netflow.walletpro.adapter;

import android.content.Context;

import io.netflow.walletpro.R;
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
