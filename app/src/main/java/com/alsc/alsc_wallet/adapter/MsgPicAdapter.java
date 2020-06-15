package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class MsgPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public MsgPicAdapter(Context context) {
        super(R.layout.item_msg_pic);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setImageResource(R.id.ivPic, R.mipmap.ic_launcher);
    }
}
