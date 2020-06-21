package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class FollowedAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public FollowedAdapter(Context context) {
        super(R.layout.item_followed);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setText(R.id.tvName, s);
        if (helper.getAdapterPosition() % 2 == 1) {
            helper.setBackgroundResource(R.id.tvFollow, R.drawable.bg_wallet_follow)
                    .setText(R.id.tvFollow, mContext.getString(R.string.wallet_follow))
                    .setTextColor(R.id.tvFollow, ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        } else {
            helper.setBackgroundResource(R.id.tvFollow, R.drawable.bg_wallet_followed)
                    .setText(R.id.tvFollow, mContext.getString(R.string.wallet_followed))
                    .setTextColor(R.id.tvFollow, ContextCompat.getColor(mContext, R.color.color_66_66_66));
        }
    }
}
