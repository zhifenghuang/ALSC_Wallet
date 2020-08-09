package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class ChatUserAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public ChatUserAdapter(Context context) {
        super(R.layout.item_chat_user);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setGone(R.id.line, helper.getAdapterPosition() == getItemCount() - 1);
    }


}
