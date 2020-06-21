package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class PublishPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public PublishPicAdapter(Context context) {
        super(R.layout.item_publish_pic);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        if (TextUtils.isEmpty(s)) {
            helper.setGone(R.id.ivPic, true)
                    .setGone(R.id.ivDelete, true)
                    .setGone(R.id.sl, false);
        } else {
            helper.setImageResource(R.id.ivPic, R.mipmap.ic_launcher)
                    .setGone(R.id.ivPic, false)
                    .setGone(R.id.ivDelete, false)
                    .setGone(R.id.sl, true);
        }

    }
}
