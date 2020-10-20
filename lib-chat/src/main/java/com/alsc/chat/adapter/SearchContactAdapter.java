package com.alsc.chat.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.alsc.chat.R;
import com.cao.commons.bean.chat.ContactItem;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.MessageType;
import com.cao.commons.bean.chat.UserBean;
import com.alsc.chat.utils.Utils;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchContactAdapter extends BaseMultiItemQuickAdapter<ContactItem, BaseViewHolder> {

    private Context mContext;

    private ArrayList<UserBean> mFriendList;
    private ArrayList<GroupBean> mGroups;

    public SearchContactAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(ContactItem.VIEW_TYPE_0, R.layout.item_friend_2);
        addItemType(ContactItem.VIEW_TYPE_1, R.layout.item_search_contact);
        addItemType(ContactItem.VIEW_TYPE_2, R.layout.item_search_contact);
        mContext = context;
    }

    public void setFriends(ArrayList<UserBean> friendList, ArrayList<GroupBean> groups) {
        mFriendList = friendList;
        mGroups = groups;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, @Nullable ContactItem item) {
        switch (helper.getItemViewType()) {
            case ContactItem.VIEW_TYPE_0:
                helper.setText(R.id.tvTitle, item.getName());
                break;
            case ContactItem.VIEW_TYPE_1:
                if (item.getFriend() != null) {
                    helper.setText(R.id.tvName, item.getFriend().getNickName())
                            .setGone(R.id.tvText, true);
                    Utils.displayAvatar(mContext, R.drawable.chat_default_group_avatar, item.getFriend().getAvatarUrl(), helper.getView(R.id.ivAvatar));
                } else {
                    helper.setText(R.id.tvName, item.getGroup().getName())
                            .setGone(R.id.tvText, true);
                    Utils.displayAvatar(mContext, R.drawable.chat_default_group_avatar, item.getGroup().getIcon(), helper.getView(R.id.ivAvatar));
                }
                break;
            case ContactItem.VIEW_TYPE_2:
                if (item.getFriend() != null) {
                    helper.setText(R.id.tvName, item.getFriend().getNickName())
                            .setGone(R.id.tvText, false)
                            .setText(R.id.tvText, mContext.getString(R.string.chat_xxx_chat_record, String.valueOf(item.getMsgNum())));
                    Utils.displayAvatar(mContext, R.drawable.chat_default_group_avatar, item.getFriend().getAvatarUrl(), helper.getView(R.id.ivAvatar));
                } else {
                    helper.setText(R.id.tvName, item.getGroup().getName())
                            .setGone(R.id.tvText, false)
                            .setText(R.id.tvText, mContext.getString(R.string.chat_xxx_chat_record, String.valueOf(item.getMsgNum())));
                    Utils.displayAvatar(mContext, R.drawable.chat_default_group_avatar, item.getGroup().getIcon(), helper.getView(R.id.ivAvatar));
                }
                break;
        }
    }


    public void searchFriend(String text) {
        List<ContactItem> list = getData();
        list.clear();
        if (!TextUtils.isEmpty(text)) {
            ContactItem contactItem;
            boolean isHad;
            if (mFriendList != null && !mFriendList.isEmpty()) {
                isHad = false;
                for (UserBean user : mFriendList) {
                    if (user.getNickName().toLowerCase().contains(text)
                            || user.getLoginAccount().toLowerCase().contains(text)
                            || user.getPinyinName().contains(text)) {
                        contactItem = new ContactItem();
                        contactItem.setItemType(ContactItem.VIEW_TYPE_1);
                        contactItem.setFriend(user);
                        list.add(contactItem);
                        isHad = true;
                    }
                }
                if (isHad) {
                    contactItem = new ContactItem();
                    contactItem.setItemType(ContactItem.VIEW_TYPE_0);
                    contactItem.setName(mContext.getString(R.string.chat_contact));
                    list.add(0, contactItem);
                }
            }
            int size = list.size();
            if (mGroups != null && !mGroups.isEmpty()) {
                isHad = false;
                for (GroupBean groupBean : mGroups) {
                    if (groupBean.getName().toLowerCase().contains(text)
                            || groupBean.getPinyinName().contains(text)) {
                        contactItem = new ContactItem();
                        contactItem.setItemType(ContactItem.VIEW_TYPE_1);
                        contactItem.setGroup(groupBean);
                        list.add(contactItem);
                        isHad = true;
                    }
                }
                if (isHad) {
                    contactItem = new ContactItem();
                    contactItem.setItemType(ContactItem.VIEW_TYPE_0);
                    contactItem.setName(mContext.getString(R.string.chat_group_chat));
                    list.add(size, contactItem);
                }
            }
            size = list.size();
            int num;
            UserBean myInfo = DataManager.getInstance().getUser();
            isHad = false;
            if (mFriendList != null && !mFriendList.isEmpty()) {
                for (UserBean user : mFriendList) {
                    num = DatabaseOperate.getInstance().searchChatRecordNum(myInfo.getUserId(),
                            user.getContactId(), text, MessageType.TYPE_TEXT.ordinal());
                    if (num > 0) {
                        contactItem = new ContactItem();
                        contactItem.setItemType(ContactItem.VIEW_TYPE_2);
                        contactItem.setFriend(user);
                        contactItem.setMsgNum(num);
                        list.add(contactItem);
                        isHad = true;
                    }
                }
            }
            if (mGroups != null && !mGroups.isEmpty()) {
                for (GroupBean groupBean : mGroups) {
                    num = DatabaseOperate.getInstance().searchGroupChatRecordNum(myInfo.getUserId(),
                            groupBean.getGroupId(), text, MessageType.TYPE_TEXT.ordinal());
                    if (num > 0) {
                        contactItem = new ContactItem();
                        contactItem.setItemType(ContactItem.VIEW_TYPE_2);
                        contactItem.setGroup(groupBean);
                        contactItem.setMsgNum(num);
                        list.add(contactItem);
                        isHad = true;
                    }
                }
                if (isHad) {
                    contactItem = new ContactItem();
                    contactItem.setItemType(ContactItem.VIEW_TYPE_0);
                    contactItem.setName(mContext.getString(R.string.chat_chat_record));
                    list.add(size, contactItem);
                }
            }
        }
        notifyDataSetChanged();
    }


}
