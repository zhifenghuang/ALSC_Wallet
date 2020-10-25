package com.alsc.alsc_wallet.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.MainActivity;
import com.alsc.alsc_wallet.fragment.chat.ChatListFragment;
import com.alsc.alsc_wallet.fragment.chat.ContactFragment;
import com.alsc.chat.fragment.AddFriendFragment;
import com.alsc.chat.fragment.LeaveMsgFragment;
import com.alsc.chat.fragment.SearchFragment;
import com.alsc.chat.fragment.SelectFriendFragment;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.common.activity.BaseActivity;
import com.common.dialog.MyDialogFragment;
import com.common.fragment.BaseFragment;
import com.common.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatMsgFragment extends BaseFragment {

    private ArrayList<BaseFragment> mBaseFragment;
    private Fragment mCurrentFragment;

    private ArrayList<UserBean> mFriendList;
    private ArrayList<GroupBean> mGroupList;
    private ChatListFragment mChatListFragment;
    private ContactFragment mFriendListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_msg;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        initFragments();
        switchFragment(mBaseFragment.get(0));
        setViewsOnClickListener(R.id.llChatMsg, R.id.llContact, R.id.ivAdd, R.id.ivSearch);
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mChatListFragment = new ChatListFragment();
        mBaseFragment.add(mChatListFragment);
        mFriendListFragment = new ContactFragment();
        mBaseFragment.add(mFriendListFragment);
    }

    private void resetBtns(LinearLayout ll1, LinearLayout ll2) {
        ((TextView) ll1.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        ll1.getChildAt(1).setVisibility(View.VISIBLE);
        ((TextView) ll2.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        ll2.getChildAt(1).setVisibility(View.INVISIBLE);
    }

    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.add(R.id.fl, to, to.toString()).commit();
            } else {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.show(to).commit();
            }
        }
        mCurrentFragment = to;
    }


    @Override
    public void updateUIText() {
        UserBean myInfo = DataManager.getInstance().getUser();
        Utils.displayAvatar(getActivity(), R.drawable.chat_default_group_avatar, myInfo.getAvatarUrl(), fv(R.id.ivMyAvatar));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.llChatMsg:
                switchFragment(mBaseFragment.get(0));
                resetBtns((LinearLayout) v, fv(R.id.llContact));
                break;
            case R.id.llContact:
                switchFragment(mBaseFragment.get(1));
                resetBtns((LinearLayout) v, fv(R.id.llChatMsg));
                break;
            case R.id.ivAdd:
                showMoreOperatorDialog();
                break;
            case R.id.ivSearch:
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BUNDLE_EXTRA, SearchFragment.SEARCH_LOCAL_FRIEND);
                gotoPager(SearchFragment.class, bundle);
                break;
        }
    }

    public void setData(ArrayList<UserBean> friendList, ArrayList<GroupBean> groupList) {
        mFriendList = friendList;
        mGroupList = groupList;
        mChatListFragment.setData(mFriendList, mGroupList);
        mFriendListFragment.setData(mFriendList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(HashMap<String, Object> map) {
        if (map != null) {
            if (map.containsKey(Constants.REDRESH_FRIENDS)) {
                ((MainActivity) getActivity()).getFriendFromServer();
            } else if (map.containsKey(Constants.REMOVE_FRIEND)) {
                long userId = (long) map.get(Constants.REMOVE_FRIEND);
                removeFriend(userId);
                mFriendListFragment.setData(mFriendList);
                mChatListFragment.setData(mFriendList, mGroupList);
            } else if (map.containsKey(Constants.NEW_VERIFY)) {
                long time = (long) map.get(Constants.NEW_VERIFY);
                boolean hasNew = DataManager.getInstance().isHasNewVerify(time);
                mFriendListFragment.setNewVerify(hasNew);
                UserBean myInfo = DataManager.getInstance().getUser();
                if (myInfo.getAllowAdd() == 0) {
                    ((MainActivity) getActivity()).getFriendFromServer();
                }
            } else if (map.containsKey(Constants.NEW_MSG_NUM_CHANGE)) {
//                int unReadNum = (int) map.get(Constants.NEW_MSG_NUM_CHANGE);
//                showUnReadNum(unReadNum);
            } else if (map.containsKey(Constants.BLOCK_FRIEND)) {
                long userId = (long) map.get(Constants.BLOCK_FRIEND);
                blockFriend(userId, 1);
                mFriendListFragment.setData(mFriendList);
                mChatListFragment.setData(mFriendList, mGroupList);
            } else if (map.containsKey(Constants.REMOVE_BLOCK)) {
                long userId = (long) map.get(Constants.REMOVE_BLOCK);
                blockFriend(userId, 0);
                mFriendListFragment.setData(mFriendList);
                mChatListFragment.setData(mFriendList, mGroupList);
            }
        }
    }

    private void removeFriend(long userId) {
        if (mFriendList == null || mFriendList.isEmpty()) {
            return;
        }
        for (UserBean userBean : mFriendList) {
            if (userBean.getContactId() == userId) {
                DatabaseOperate.getInstance().deleteUserChatRecord(DataManager.getInstance().getUser().getUserId(), userId);
                mFriendList.remove(userBean);
                DataManager.getInstance().saveFriends(mFriendList);
                return;
            }
        }
    }

    private void blockFriend(long userId, int block) {
        if (mFriendList == null || mFriendList.isEmpty()) {
            return;
        }
        for (UserBean userBean : mFriendList) {
            if (userBean.getContactId() == userId) {
                userBean.setBlock(block);
                DataManager.getInstance().saveFriends(mFriendList);
                return;
            }
        }
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }


    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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
                        ((BaseActivity) getActivity()).requestPermission(0, Manifest.permission.CAMERA);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("wallet://capture"));
                        startActivity(intent);
                    }
                } else if (viewId == R.id.tvFeedback) {
                    gotoPager(LeaveMsgFragment.class);
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }
}
