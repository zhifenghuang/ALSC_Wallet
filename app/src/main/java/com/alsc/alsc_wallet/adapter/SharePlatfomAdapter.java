package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.View;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.BaseActivity;
import com.alsc.alsc_wallet.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.message.FollowedFragment;
import com.alsc.alsc_wallet.fragment.trade.MoneyAccountFragment;
import com.alsc.alsc_wallet.fragment.trade.PaySettingFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SharePlatfomAdapter extends BaseQuickAdapter<FollowedFragment.SharePlatformInfo, BaseViewHolder> {

    private Context mContext;

    public SharePlatfomAdapter(Context context) {
        super(R.layout.item_share_platform);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, FollowedFragment.SharePlatformInfo item) {
        helper.setImageResource(R.id.iv, item.srcId)
                .setText(R.id.tv, item.name);

    }
}
