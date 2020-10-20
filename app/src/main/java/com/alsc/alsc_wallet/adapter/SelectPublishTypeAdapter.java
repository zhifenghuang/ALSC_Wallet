package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.alsc.alsc_wallet.R;
import com.common.bean.KeyValueBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SelectPublishTypeAdapter extends BaseQuickAdapter<KeyValueBean, BaseViewHolder> {

    private Context mContext;

    public SelectPublishTypeAdapter(Context context) {
        super(R.layout.item_select_publish_type);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, KeyValueBean item) {
        helper.setText(R.id.tvTitle, item.getKey());
        if (TextUtils.isEmpty(item.getValue())) {
            helper.setGone(R.id.tvContent, true);
        } else {
            helper.setText(R.id.tvContent, item.getValue())
                    .setGone(R.id.tvContent, false);
        }
        helper.setGone(R.id.line, helper.getAdapterPosition() == getItemCount() - 1);
    }
}
