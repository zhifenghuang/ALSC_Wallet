package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class MsgCommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public MsgCommentAdapter(Context context) {
        super(R.layout.item_msg_comment);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        ((TextView)helper.getView(R.id.tvText)).setText(Html.fromHtml("<font color=\"#000000\">席幕枫:</font>以太思路已更新"));
    }
}
