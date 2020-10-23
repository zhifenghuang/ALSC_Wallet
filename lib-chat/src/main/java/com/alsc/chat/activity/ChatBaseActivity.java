package com.alsc.chat.activity;

import com.alsc.chat.fragment.ChatListFragment;
import com.alsc.chat.fragment.FriendListFragment;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.manager.ChatManager;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.OnHttpErrorListener;
import com.common.http.SubscriberOnNextListener;

import java.util.ArrayList;

public abstract class ChatBaseActivity extends BaseActivity {

    protected ArrayList<UserBean> mFriendList;
    protected ArrayList<GroupBean> mGroupList;

    private boolean mIsGetFriend;
    private boolean mIsGetGroup;

    protected ChatListFragment mChatListFragment;
    protected FriendListFragment mFriendListFragment;

    public void onResume() {
        super.onResume();
        if (mChatListFragment != null) {
            mFriendList = DataManager.getInstance().getFriends();
            mGroupList = DataManager.getInstance().getGroups();
            mChatListFragment.setData(mFriendList, mGroupList);
            if (mFriendListFragment != null) {
                mFriendListFragment.setData(mFriendList);
            }
        }
    }

    public void getFriendFromServer() {
        if (mIsGetFriend) {
            return;
        }
        UserBean userBean = DataManager.getInstance().getUser();
        if (userBean == null) {
            return;
        }
        mIsGetFriend = true;
        ChatHttpMethods.getInstance().getFriends(new HttpObserver(new SubscriberOnNextListener<ArrayList<UserBean>>() {
            @Override
            public void onNext(ArrayList<UserBean> list, String msg) {
                DataManager.getInstance().saveFriends(list);
                mFriendList = list;
                if (mChatListFragment != null) {
                    mChatListFragment.setData(mFriendList, mGroupList);
                }
                if (mFriendListFragment != null) {
                    mFriendListFragment.setData(mFriendList);
                }
                mIsGetFriend = false;
            }
        }, this, false, new OnHttpErrorListener() {
            @Override
            public void onConnectError(Throwable e) {
                mIsGetFriend = false;
            }

            @Override
            public void onServerError(int errorCode, String errorMsg) {
                mIsGetFriend = false;
                if (errorCode == 401) {
                    ChatManager.getInstance().showLoginOutDialog();
                }
            }
        }));
    }

    public void getGroupFromServer() {
        if (mIsGetGroup) {
            return;
        }
        UserBean userBean = DataManager.getInstance().getUser();
        if (userBean == null) {
            return;
        }
        mIsGetGroup = true;
        ChatHttpMethods.getInstance().getGroups(1, Integer.MAX_VALUE - 1,
                new HttpObserver(new SubscriberOnNextListener<ArrayList<GroupBean>>() {
                    @Override
                    public void onNext(ArrayList<GroupBean> list, String msg) {
                        DataManager.getInstance().saveGroups(list);
                        mGroupList = list;
                        if (mChatListFragment != null) {
                            mChatListFragment.setData(mFriendList, mGroupList);
                        }
                        mIsGetGroup = false;
                    }
                }, this, false, new OnHttpErrorListener() {
                    @Override
                    public void onConnectError(Throwable e) {
                        mIsGetGroup = false;
                    }

                    @Override
                    public void onServerError(int errorCode, String errorMsg) {
                        mIsGetGroup = false;
                        if (errorCode == 401) {
                            ChatManager.getInstance().showLoginOutDialog();
                        }
                    }
                }));
    }
}
