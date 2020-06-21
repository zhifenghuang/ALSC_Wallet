package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.message.FastMsgDetailFragment;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FastMsgDetailAdapter extends BaseMultiItemQuickAdapter<FastMsgDetailFragment.FastMsgDetailItem, BaseViewHolder> {

    private Context mContext;

    public FastMsgDetailAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_fast_msg_0);
        addItemType(1, R.layout.item_fast_msg_1);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, FastMsgDetailFragment.FastMsgDetailItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                break;
            case 1:
                break;
        }
    }

}
