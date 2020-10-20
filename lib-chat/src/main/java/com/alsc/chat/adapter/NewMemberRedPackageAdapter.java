package com.alsc.chat.adapter;

import android.content.Context;

import com.alsc.chat.R;
import com.cao.commons.bean.chat.EnvelopeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;

public class NewMemberRedPackageAdapter extends BaseQuickAdapter<EnvelopeBean, BaseViewHolder> {

    private Context mContext;

    public NewMemberRedPackageAdapter(Context context) {
        super(R.layout.item_new_member_red_package);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final EnvelopeBean item) {
        helper.setText(R.id.tvAmount, mContext.getString(R.string.chat_xxx_alsc, String.format("%.2f", item.getAmount())))
                .setText(R.id.tvOneAmount, mContext.getString(R.string.chat_xxx_alsc, String.format("%.2f", (float) (item.getAmount() / item.getTotalCount() + 0.00001))))
                .setGone(R.id.tvHadUnLock, item.getStatus() != 3)
                .setText(R.id.tvRestNum, item.getSessionCount() + "/" + item.getTotalCount());
    }
}
