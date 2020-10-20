package com.alsc.chat.adapter;


import android.content.Context;

import com.alsc.chat.R;
import com.alsc.chat.utils.Utils;
import com.cao.commons.bean.chat.QuestionBean;
import com.cao.commons.bean.chat.UserBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class SelectServiceAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    private Context mContext;

    public SelectServiceAdapter(Context context) {
        super(R.layout.item_service);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.tvServiceName, item.getNickName());
        Utils.displayAvatar(mContext, R.drawable.chat_default_group_avatar, item.getAvatarUrl(), helper.getView(R.id.ivServiceAvatar));
    }
}
