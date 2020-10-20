package com.alsc.chat.adapter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alsc.chat.R;
import com.alsc.chat.activity.ChatBaseActivity;
import com.alsc.chat.fragment.ApplyListFragment;
import com.alsc.chat.fragment.GroupListFragment;
import com.alsc.chat.fragment.LabelFragment;
import com.alsc.chat.fragment.StarFriendFragment;
import com.cao.commons.bean.chat.FriendItem;
import com.alsc.chat.utils.Utils;
import com.cao.commons.manager.DataManager;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;

public class FriendAdapter extends BaseMultiItemQuickAdapter<FriendItem, BaseViewHolder> {

    private Context mContext;

    private boolean mIsHadNewVerify;

    public FriendAdapter(Context context) {
        super(new ArrayList<FriendItem>());
        addItemType(FriendItem.VIEW_TYPE_0, R.layout.item_friend_0);
//        addItemType(FriendItem.VIEW_TYPE_1, R.layout.item_friend_1);
//        addItemType(FriendItem.VIEW_TYPE_2, R.layout.item_friend_2);
        addItemType(FriendItem.VIEW_TYPE_3, R.layout.item_friend_3);
        addItemType(FriendItem.VIEW_TYPE_4, R.layout.item_friend_4);
        mContext = context;
    }

    public void setNew(boolean isHadNewVerify) {
        mIsHadNewVerify = isHadNewVerify;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendItem item) {
        switch (helper.getItemViewType()) {
            case FriendItem.VIEW_TYPE_0:
                helper.getView(R.id.tvNewFreind).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataManager.getInstance().setNoNew();
                        setNew(false);
                        ((ChatBaseActivity) mContext).gotoPager(ApplyListFragment.class);
                    }
                });
                helper.getView(R.id.tvMyGroup).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatBaseActivity) mContext).gotoPager(GroupListFragment.class);
                    }
                });
                helper.getView(R.id.tvStarFriend).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatBaseActivity) mContext).gotoPager(StarFriendFragment.class);
                    }
                });
                helper.getView(R.id.tvLabel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatBaseActivity) mContext).gotoPager(LabelFragment.class);
                    }
                });
                break;
            case FriendItem.VIEW_TYPE_4:
                break;
//            case FriendItem.VIEW_TYPE_1:
//                helper.setImageResource(R.id.ivIcon, item.getIconRes())
//                        .setText(R.id.tvName, item.getName())
//                        .setGone(R.id.ivNew, helper.getAdapterPosition() != 1 || !mIsHadNewVerify)
//                        .setGone(R.id.line, helper.getAdapterPosition() == 4);
//
//                break;
//            case FriendItem.VIEW_TYPE_2:
//                helper.setText(R.id.tvTitle, item.getName());
//                break;
            case FriendItem.VIEW_TYPE_3:
                helper.setText(R.id.tvName, item.getFriend().getNickName());
                Utils.displayAvatar(mContext, R.drawable.chat_default_group_avatar, item.getFriend().getAvatarUrl(), (ImageView) helper.getView(R.id.ivAvatar));
                break;
        }
    }
}
