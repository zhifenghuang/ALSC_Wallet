package com.alsc.chat.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.alsc.chat.activity.ChatBaseActivity;
import com.alsc.chat.adapter.ChatUserAdapter;
import com.cao.commons.bean.chat.CaptureEvent;
import com.cao.commons.bean.chat.ChatBean;
import com.cao.commons.bean.chat.ChatSubBean;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.GroupMessageBean;
import com.cao.commons.bean.chat.MessageBean;
import com.cao.commons.bean.chat.MessageType;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.http.HttpObserver;
import com.alsc.chat.http.SubscriberOnNextListener;
import com.cao.commons.manager.DataManager;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatListFragment extends BaseFragment {

    private ArrayList<ChatBean> mChatList;
    private ChatUserAdapter mAdapter;

    private ArrayList<UserBean> mFriendList;
    private ArrayList<GroupBean> mGroupList;
    private HashMap<String, ChatSubBean> mSettings;
    private Gson mGson;

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_list;
    }

    @Override
    protected void onViewCreated(View view) {
        setTopStatusBarStyle(view);
        EventBus.getDefault().register(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        mChatList = new ArrayList<>();
        ((ChatBaseActivity) getActivity()).getFriendFromServer();
        ((ChatBaseActivity) getActivity()).getGroupFromServer();
    }

    private ChatUserAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ChatUserAdapter(getActivity(), DataManager.getInstance().getUser());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Bundle bundle = new Bundle();
                    ChatBean bean = getAdapter().getItem(position);
                    if (bean.chatUser != null) {
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, bean.chatUser);
                        gotoPager(ChatFragment.class, bundle);
                    } else {
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, bean.group);
                        gotoPager(GroupChatFragment.class, bundle);
                    }
                }
            });
            mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                    showDeleteChatRecord(getAdapter().getItem(position));
                    return false;
                }
            });
            mAdapter.setNewData(mChatList);
        }
        return mAdapter;
    }

    private void initChatList() {
        DatabaseOperate.getInstance().deleteAllExprieMsg();
        mChatList.clear();
        UserBean myInfo = DataManager.getInstance().getUser();
        ArrayList<MessageBean> list = DatabaseOperate.getInstance().getUserChatList(myInfo.getUserId());
        if (list != null && !list.isEmpty()) {
            boolean isFriendMsg;
            ChatBean chatBean;
            for (MessageBean bean : list) {
                isFriendMsg = false;
                for (UserBean friend : mFriendList) {
                    if (friend.getBlock() == 1) {
                        continue;
                    }
                    if (friend.getContactId() == bean.getToId()
                            || friend.getContactId() == bean.getFromId()) {
                        chatBean = new ChatBean();
                        chatBean.chatUser = friend;
                        chatBean.lastMsg = bean;
                        chatBean.chatSubBean = mSettings.get("user_" + friend.getContactId());
                        chatBean.unReadNum = bean.getUnReadNum();//DatabaseOperate.getInstance().getUnReadNum(myInfo.getUserId(), friend.getContactId());
                        mChatList.add(chatBean);
                        isFriendMsg = true;
                        break;
                    }
                }
                if (!isFriendMsg && myInfo.isService() && !TextUtils.isEmpty(bean.getExtra())) {  //客服消息要特殊处理，需要把陌生人显示出来
                    ArrayList<UserBean> users = getGson().fromJson(bean.getExtra(), new TypeToken<ArrayList<UserBean>>() {
                    }.getType());
                    chatBean = new ChatBean();
                    if (users != null && !users.isEmpty()) {
                        for (UserBean userBean : users) {
                            if (userBean.getUserId() != myInfo.getUserId()) {
                                chatBean.chatUser = userBean;
                                break;
                            }
                        }
                    }
                    chatBean.lastMsg = bean;
                    chatBean.chatSubBean = mSettings.get("user_" + chatBean.chatUser.getContactId());
                    chatBean.unReadNum = bean.getUnReadNum();//DatabaseOperate.getInstance().getUnReadNum(myInfo.getUserId(), chatBean.chatUser.getContactId());
                    mChatList.add(chatBean);
                }
            }
        }
        initGroupChatList(myInfo.getUserId());
        getAdapter().setNewData(mChatList);
        getAdapter().resortList();
        getAdapter().notifyDataSetChanged();
        resetTotalUnReadNum();
    }

    private void initGroupChatList(long myId) {
        ArrayList<GroupMessageBean> list = DatabaseOperate.getInstance().getChatGroupList(myId);
        if (list != null && !list.isEmpty()) {
            for (GroupMessageBean bean : list) {
                for (GroupBean group : mGroupList) {
                    if (group.getGroupId() == bean.getGroupId()) {
                        ChatBean chatBean = new ChatBean();
                        chatBean.group = group;
                        chatBean.lastMsg = bean;
                        chatBean.chatSubBean = mSettings.get("group_" + group.getGroupId());
                        chatBean.unReadNum = bean.getUnReadNum();//DatabaseOperate.getInstance().getGroupUnReadNum(myId, group.getGroupId());
                        mChatList.add(chatBean);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void updateUIText() {
        mSettings = DataManager.getInstance().getChatSubSettings();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(MessageBean message) {
        if (getView() != null && message != null) {
            UserBean myInfo = DataManager.getInstance().getUser();
            for (ChatBean chatBean : mChatList) {
                if (chatBean.chatUser == null) {
                    continue;
                }
                if (chatBean.chatUser.getContactId() == message.getFromId()
                        || chatBean.chatUser.getContactId() == message.getToId()) {
                    chatBean.lastMsg = message;
                    chatBean.unReadNum += 1;
                    mChatList.remove(chatBean);
                    getAdapter().receiveNewMsg(chatBean);
                    getAdapter().notifyDataSetChanged();
                    resetTotalUnReadNum();
                    return;
                }
            }
            ChatBean chatBean;
            if (mFriendList != null) {
                for (UserBean bean : mFriendList) {
                    if (bean.getContactId() == message.getFromId()
                            || bean.getContactId() == message.getToId()) {
                        chatBean = new ChatBean();
                        chatBean.chatUser = bean;
                        chatBean.lastMsg = message;
                        chatBean.unReadNum += 1;
                        chatBean.chatSubBean = mSettings.get("user_" + bean.getContactId());
                        getAdapter().receiveNewMsg(chatBean);
                        getAdapter().notifyDataSetChanged();
                        resetTotalUnReadNum();
                        return;
                    }
                }
            }
            if (myInfo.isService() && !TextUtils.isEmpty(message.getExtra())) {  //客服消息要特殊处理，需要把陌生人显示出来
                ArrayList<UserBean> users = getGson().fromJson(message.getExtra(), new TypeToken<ArrayList<UserBean>>() {
                }.getType());
                chatBean = new ChatBean();
                if (users != null && !users.isEmpty()) {
                    for (UserBean userBean : users) {
                        if (userBean.getUserId() != myInfo.getUserId()) {
                            chatBean.chatUser = userBean;
                            chatBean.lastMsg = message;
                            chatBean.chatSubBean = mSettings.get("user_" + userBean.getContactId());
                            chatBean.unReadNum = DatabaseOperate.getInstance().getUnReadNum(myInfo.getUserId(), userBean.getContactId());
                            getAdapter().receiveNewMsg(chatBean);
                            getAdapter().notifyDataSetChanged();
                            resetTotalUnReadNum();
                            break;
                        }
                    }
                }
            } else {
                ((ChatBaseActivity) getActivity()).getFriendFromServer();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveGroupMsg(GroupMessageBean message) {
        if (getView() != null && message != null) {
            for (ChatBean chatBean : mChatList) {
                if (chatBean.group == null) {
                    continue;
                }
                if (chatBean.group.getGroupId() == message.getGroupId()) {
                    if (message.getMsgType() == MessageType.TYPE_UPDATE_GROUP_NAME.ordinal()) {
                        chatBean.group.setName(message.getContent());
                        updateGroup(chatBean.group);
                    } else if (message.getMsgType() == MessageType.TYPE_REMOVE_FROM_GROUP.ordinal()) {
                        ArrayList<UserBean> list = getGson().fromJson(message.getExtra(), new TypeToken<ArrayList<UserBean>>() {
                        }.getType());
                        for (UserBean bean : list) {
                            if (bean.getUserId() == DataManager.getInstance().getUserId()) {
                                getAdapter().removeGroupChat(chatBean.group.getGroupId());
                                removeFromGroupInList(chatBean.group);
                                resetTotalUnReadNum();
                                return;
                            }
                        }
                    }
                    chatBean.lastMsg = message;
                    chatBean.unReadNum += 1;
                    mChatList.remove(chatBean);
                    getAdapter().receiveNewMsg(chatBean);
                    getAdapter().notifyDataSetChanged();
                    resetTotalUnReadNum();
                    return;
                }
            }
            if (mGroupList != null) {
                for (GroupBean bean : mGroupList) {
                    if (bean.getGroupId() == message.getGroupId()) {
                        if (message.getMsgType() == MessageType.TYPE_REMOVE_FROM_GROUP.ordinal()) {
                            ArrayList<UserBean> list = getGson().fromJson(message.getExtra(), new TypeToken<ArrayList<UserBean>>() {
                            }.getType());
                            for (UserBean userBean : list) {
                                if (userBean.getUserId() == DataManager.getInstance().getUserId()) {
                                    removeFromGroupInList(bean);
                                    return;
                                }
                            }
                        }
                        ChatBean chatBean = new ChatBean();
                        chatBean.group = bean;
                        chatBean.lastMsg = message;
                        chatBean.unReadNum += 1;
                        chatBean.chatSubBean = mSettings.get("group_" + bean.getGroupId());
                        getAdapter().receiveNewMsg(chatBean);
                        getAdapter().notifyDataSetChanged();
                        resetTotalUnReadNum();
                        return;
                    }
                }
            }
            ((ChatBaseActivity) getActivity()).getGroupFromServer();
        }
    }

    private void resetTotalUnReadNum() {
        int totalNum = 0;
        for (ChatBean bean : mChatList) {
            if (!bean.isNotInterupt()) {
                totalNum += bean.unReadNum;
            }
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put(Constants.NEW_MSG_NUM_CHANGE, totalNum);
        EventBus.getDefault().post(map);
    }

    public void setData(ArrayList<UserBean> friendList, ArrayList<GroupBean> groupList) {
        mFriendList = friendList;
        mGroupList = groupList;
        if (getView() != null) {
            initChatList();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveAvatarUrl(CaptureEvent event) {
        if (getView() != null && !TextUtils.isEmpty(event.getUrl())) {
            final String text = event.getUrl();
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (text.contains("tuijianma=")) {
                        String strs = text.split("tuijianma=")[1];
                        String id = strs.split("&")[0];
                        searchContact(id);
                    } else if (text.startsWith("chat_group_")) {
                        String id = text.substring(11);
                        addToGroup(Long.parseLong(id));
                    }
                }
            }, 200);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveInfo(HashMap<String, Object> map) {
        if (getView() != null && map != null) {
            if (map.containsKey(Constants.SEND_MSG_FAILED)) {
                String msgId = (String) map.get(Constants.SEND_MSG_FAILED);
                for (ChatBean bean : mChatList) {
                    if (bean.lastMsg != null && bean.lastMsg.getMessageId().equals(msgId)) {
                        bean.lastMsg.setSendStatus(-1);
                        getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            } else if (map.containsKey(Constants.END_GROUP)) {
                long groupId = (long) map.get(Constants.END_GROUP);
                getAdapter().removeGroupChat(groupId);
                resetTotalUnReadNum();
            } else if (map.containsKey(Constants.SEND_MSG_SUCCESS)) {
                String msgId = (String) map.get(Constants.SEND_MSG_SUCCESS);
                for (ChatBean bean : mChatList) {
                    if (bean.lastMsg != null && bean.lastMsg.getMessageId().equals(msgId)) {
                        bean.lastMsg.setSendStatus(1);
                        mAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        }
    }

    private void addToGroup(long groupId) {
        ChatHttpMethods.getInstance().groupQrcode(groupId, new HttpObserver(new SubscriberOnNextListener<GroupBean>() {
            @Override
            public void onNext(GroupBean bean, String msg) {
                if (getView() == null || bean == null) {
                    return;
                }
                if (bean.getPayinState() == 1) {
                    showPayInGroupDialog(bean, null);
                } else {
                    ArrayList<UserBean> list = new ArrayList<>();
                    list.add(DataManager.getInstance().getUser());
                    sendInviteToGroupMsg(bean, null, list);
                    showToast(R.string.chat_add_group_success);
                    addGroupInList(bean);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_EXTRA, bean);
                    gotoPager(GroupChatFragment.class, bundle);
                }
            }
        }, getActivity(), (ChatBaseActivity) getActivity()));
    }

    private void searchContact(String text) {
        ChatHttpMethods.getInstance().searchContact(text, new HttpObserver(new SubscriberOnNextListener<UserBean>() {
            @Override
            public void onNext(UserBean user, String msg) {
                if (getView() == null || user == null) {
                    return;
                }
                user.setContactId(user.getUserId());
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BUNDLE_EXTRA, user);
                boolean isFriend = false;
                ArrayList<UserBean> list = DataManager.getInstance().getFriends();
                if (list != null && !list.isEmpty()) {
                    for (UserBean bean : list) {
                        if (bean.getContactId() == user.getUserId()) {
                            isFriend = true;
                            break;
                        }
                    }
                }
                if (!isFriend) {
                    bundle.putInt(Constants.BUNDLE_EXTRA_2, VerifyApplyFragment.ADD_BY_QRCODE);
                }
                gotoPager(isFriend ? UserInfoFragment.class : VerifyApplyFragment.class, bundle);
            }
        }, getActivity(), (ChatBaseActivity) getActivity()));
    }


    public void showMoreOperatorDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_chat_list_more_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                UserBean myInfo = DataManager.getInstance().getUser();
                if (myInfo.isService()) {
                    view.findViewById(R.id.tvFeedback).setVisibility(View.GONE);
                }
                dialogFragment.setDialogViewsOnClickListener(view, R.id.llRoot, R.id.tvStartGroupChat,
                        R.id.tvAddFriend, R.id.tvScan, R.id.tvFeedback);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.tvStartGroupChat) {
                    if (mFriendList == null || mFriendList.isEmpty()) {
                        showToast(R.string.chat_no_friend);
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BUNDLE_EXTRA, SelectFriendFragment.FROM_GROUP);
                    gotoPager(SelectFriendFragment.class, bundle);
                } else if (viewId == R.id.tvAddFriend) {
                    gotoPager(AddFriendFragment.class);
                } else if (viewId == R.id.tvScan) {
                    if (!Utils.isGrantPermission(getActivity(),
                            Manifest.permission.CAMERA)) {
                        ((ChatBaseActivity) getActivity()).requestPermission(0, Manifest.permission.CAMERA);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alsc://capture"));
                        startActivity(intent);
                    }
                } else if (viewId == R.id.tvFeedback) {
                    gotoPager(LeaveMsgFragment.class);
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }

    private void showDeleteChatRecord(final ChatBean chatBean) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.chat_tip));
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.chat_delete_chat_tip));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.chat_cancel));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.chat_ok));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn2) {
                    UserBean myInfo = DataManager.getInstance().getUser();
                    if (chatBean.chatUser != null) {
                        DatabaseOperate.getInstance().deleteUserChatRecord(myInfo.getUserId(), chatBean.chatUser.getContactId());
                    } else {
                        DatabaseOperate.getInstance().deleteGroupChatRecord(myInfo.getUserId(), chatBean.group.getGroupId());
                    }
                    getAdapter().removeChatUser(chatBean);
                    resetTotalUnReadNum();
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }
}
