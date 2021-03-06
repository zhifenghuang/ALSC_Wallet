package com.alsc.chat.adapter;

import android.content.Context;

import com.alsc.chat.R;
import com.cao.commons.bean.chat.UserBean;
import com.alsc.chat.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LabelFriendAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    private Context mContext;


    public LabelFriendAdapter(Context context) {
        super(R.layout.item_label_friend);
        mContext = context;

    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, @Nullable UserBean item) {
        helper.setText(R.id.tvName, item.getNickName())
                .setGone(R.id.line, helper.getAdapterPosition() == getItemCount() - 1);
        Utils.displayAvatar(mContext, R.drawable.chat_default_group_avatar, item.getAvatarUrl(), helper.getView(R.id.ivAvatar));

    }
}
