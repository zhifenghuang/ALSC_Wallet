package com.alsc.chat.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alsc.chat.R;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.cao.commons.bean.chat.ChatSubBean;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.UploadAvatarEvent;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.alsc.chat.http.ChatHttpMethods;
import com.cao.commons.manager.DataManager;
import com.alsc.chat.manager.UPYFileUploadManger;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupDetailFragment extends ChatBaseFragment {

    private GroupBean mGroup;
    private ArrayList<UserBean> mGroupUsers;

    private HashMap<String, ChatSubBean> mSettings;
    private ChatSubBean mChatSubBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.chat_detail));
        mSettings = DataManager.getInstance().getChatSubSettings();
        EventBus.getDefault().register(this);
        setTopStatusBarStyle(view);
        setViewsOnClickListener(R.id.llGroupInfo, R.id.llGroupQrCode,
                R.id.tvGroupManager, R.id.tvGroupNotice,
                R.id.llGroupMsgSwitch, R.id.llTopChat,
                R.id.llInGroupNick, R.id.llShowNickInGroup,
                R.id.tvSearchMsg, R.id.tvClearMsg,
                R.id.tvDeleteQuit, R.id.tvAddMember);
        mGroup = (GroupBean) getArguments().getSerializable(Constants.BUNDLE_EXTRA);

        mGroupUsers = (ArrayList<UserBean>) getArguments().getSerializable(Constants.BUNDLE_EXTRA_2);
        mChatSubBean = mSettings.get("group_" + mGroup.getGroupId());
        if (mChatSubBean == null) {
            mChatSubBean = new ChatSubBean();
        }
        setImage(R.id.ivGroupMsgSwitch, mChatSubBean.getNotInterupt() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
        setImage(R.id.ivTopChatSwitch, mChatSubBean.getIsTop() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
        setImage(R.id.ivShowNickInGroup, mChatSubBean.getIsShowMemberNick() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);

        if (mGroupUsers == null) {
            mGroupUsers = new ArrayList<>();
        }
        getGroupUsers();
        UserBean myInfo = DataManager.getInstance().getUser();
        String myNickInGroup = myInfo.getNickName();
        for (UserBean user : mGroupUsers) {
            if (user.getUserId() == myInfo.getUserId()) {
                myNickInGroup = user.getNickName();
                break;
            }
        }
        mGroup.setMyNickInGroup(myNickInGroup);
    }

    private void showGroupDetail() {
        boolean isShowAddButton = false;
        if (mGroup.getJoinStint() == 0) {
            if (mGroup.getJoinType() == 0 && mGroup.getGroupRole() == 3) {  //只有群主能拉人
                isShowAddButton = true;
            } else if (mGroup.getJoinType() == 1) {
                isShowAddButton = true;
            }
        }
        fv(R.id.tvAddMember).setVisibility(isShowAddButton ? View.VISIBLE : View.GONE);
        setText(R.id.tvGroupName, mGroup.getName());
        setText(R.id.tvInGroupNick, mGroup.getMyNickInGroup());
        Utils.displayAvatar(getActivity(), R.drawable.chat_default_group_avatar, mGroup.getIcon(), fv(R.id.ivGroupCover));
    }


    @Override
    public void updateUIText() {
        if (mGroup.getGroupRole() == 3) {
            setViewVisible(R.id.tvGroupManager, R.id.lineGroupManager);
        } else {
            setViewGone(R.id.tvGroupManager, R.id.lineGroupManager);
        }
        Object object = DataManager.getInstance().getObject();
        if (object instanceof ArrayList) {
            mGroupUsers.addAll((ArrayList<UserBean>) object);
            DataManager.getInstance().saveGroupUsers(mGroup.getGroupId(), mGroupUsers);
        }
        DataManager.getInstance().setObject(null);
        setText(R.id.tvGroupMemberNum, getString(R.string.chat_member_num_xxx, String.valueOf(mGroupUsers.size())));
        showGroupDetail();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.llGroupInfo) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroupUsers);
            gotoPager(GroupInfoFragment.class, bundle);
        } else if (id == R.id.llGroupQrCode) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, QrcodeFragment.GROUP_QRCODE);
            mGroup.setMemberNum(mGroupUsers.size());
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroup);
            gotoPager(QrcodeFragment.class, bundle);
        } else if (id == R.id.tvGroupNotice) {
            if (mGroup.getGroupRole() != 3) {
                ((BaseActivity) getActivity()).showOneBtnDialog("",
                        getString(R.string.chat_only_ower_can_update_notice),
                        getString(R.string.chat_ok));
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            bundle.putInt(Constants.BUNDLE_EXTRA_2, UpdateGroupInfoFragment.UPDATE_GROUP_NOTICE);
            gotoPager(UpdateGroupInfoFragment.class, bundle);
        } else if (id == R.id.tvGroupManager) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroupUsers);
            gotoPager(GroupManagerFragment.class, bundle);
        } else if (id == R.id.llGroupMsgSwitch) {
            int msgSwitch = mChatSubBean.getNotInterupt() == 1 ? 0 : 1;
            mChatSubBean.setNotInterupt(msgSwitch);
            setImage(R.id.ivGroupMsgSwitch, mChatSubBean.getNotInterupt() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
            mSettings.put("group_" + mGroup.getGroupId(), mChatSubBean);
            DataManager.getInstance().saveChatSubSettings(mSettings);
        } else if (id == R.id.llTopChat) {
            int topSwitch = mChatSubBean.getIsTop() == 1 ? 0 : 1;
            mChatSubBean.setIsTop(topSwitch);
            setImage(R.id.ivTopChatSwitch, mChatSubBean.getIsTop() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
            mSettings.put("group_" + mGroup.getGroupId(), mChatSubBean);
            DataManager.getInstance().saveChatSubSettings(mSettings);
        } else if (id == R.id.llInGroupNick) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            bundle.putInt(Constants.BUNDLE_EXTRA_2, UpdateGroupInfoFragment.UPDATE_IN_GROUP_NICK);
            gotoPager(UpdateGroupInfoFragment.class, bundle);
        } else if (id == R.id.llShowNickInGroup) {
            int isShowMemberNick = mChatSubBean.getIsShowMemberNick() == 1 ? 0 : 1;
            mChatSubBean.setIsShowMemberNick(isShowMemberNick);
            setImage(R.id.ivShowNickInGroup, mChatSubBean.getIsShowMemberNick() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
            mSettings.put("group_" + mGroup.getGroupId(), mChatSubBean);
            DataManager.getInstance().saveChatSubSettings(mSettings);
        } else if (id == R.id.tvSearchMsg) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_EXTRA, SearchFragment.SEARCH_GROUP_CHAT_RECORD);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroup);
            gotoPager(SearchFragment.class, bundle);
        } else if (id == R.id.tvClearMsg) {
            showDeleteDialog(0);
        } else if (id == R.id.tvDeleteQuit) {
            showDeleteDialog(1);
        } else if (id == R.id.tvAddMember) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, SelectFriendFragment.FROM_GROUP_DETAIL);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroupUsers);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_3, mGroup);
            gotoPager(SelectFriendFragment.class, bundle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(GroupBean bean) {
        if (getView() != null) {
            mGroup = bean;
            saveNewGroup(mGroup);
            setText(R.id.tvGroupName, mGroup.getName());
            if (TextUtils.isEmpty(mGroup.getMyNickInGroup())) {
                return;
            }
            UserBean myInfo = DataManager.getInstance().getUser();
            for (UserBean user : mGroupUsers) {
                if (user.getUserId() == myInfo.getUserId()) {
                    user.setMemo(mGroup.getMyNickInGroup());
                    break;
                }
            }
            DataManager.getInstance().saveGroupUsers(mGroup.getGroupId(), mGroupUsers);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveInfo(HashMap<String, Object> map) {
        if (getView() != null && map != null) {
            if (map.containsKey(Constants.EDIT_GROUP_MEMBER)) {
                setText(R.id.tvGroupMemberNum, getString(R.string.chat_member_num_xxx, String.valueOf(mGroupUsers.size())));
            } else if (map.containsKey(Constants.END_GROUP)) {
                long groupId = (long) map.get(Constants.END_GROUP);
                if (groupId == mGroup.getGroupId()) {
                    goBack();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveAvatarFile(File file) {
        if (getView() != null) {
            UPYFileUploadManger.getInstance().uploadFile(file);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveAvatarUrl(UploadAvatarEvent avatar) {
        if (getView() != null && avatar.isSuccess()) {
            mGroup.setIcon(avatar.getUrl());
            saveNewGroup(mGroup);
            ChatHttpMethods.getInstance().updateGroupIcon(String.valueOf(mGroup.getGroupId()), avatar.getUrl(), new HttpObserver(new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o, String msg) {

                }
            }, getActivity(), (BaseActivity) getActivity()));
        }
    }

    private void saveNewGroup(GroupBean bean) {
        ArrayList<GroupBean> list = DataManager.getInstance().getGroups();
        if (list == null || list.isEmpty()) {
            return;
        }
        int index = 0;
        for (GroupBean group : list) {
            if (group.getGroupId() == bean.getGroupId()) {
                list.set(index, bean);
                DataManager.getInstance().saveGroups(list);
                return;
            }
            ++index;
        }
    }

    private void showDeleteDialog(int type) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.chat_tip));
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(type == 0 ?
                        R.string.chat_are_you_sure_delete_group_chat_record : R.string.chat_are_you_sure_delete_group));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.chat_cancel));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.chat_ok));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn2) {
                    if (type == 0) {
                        UserBean myInfo = DataManager.getInstance().getUser();
                        DatabaseOperate.getInstance().deleteGroupChatRecord(myInfo.getUserId(), mGroup.getGroupId());
                        HashMap<String, String> map = new HashMap<>();
                        map.put(Constants.CLEAR_MESSAGE, "");
                        EventBus.getDefault().post(map);
                    } else if (type == 1) {
                        deleteGroup();
                    }
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }

    private void deleteGroup() {
        ChatHttpMethods.getInstance().exitGroup(String.valueOf(mGroup.getGroupId()), new HttpObserver(new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o, String msg) {
                UserBean myInfo = DataManager.getInstance().getUser();
                DatabaseOperate.getInstance().deleteGroupChatRecord(myInfo.getUserId(), mGroup.getGroupId());
                removeGroup();
                HashMap<String, Object> map = new HashMap<>();
                map.put(Constants.END_GROUP, mGroup.getGroupId());
                EventBus.getDefault().post(map);
                goBack();
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }

    private void removeGroup() {
        ArrayList<GroupBean> list = DataManager.getInstance().getGroups();
        for (GroupBean bean : list) {
            if (bean.getGroupId() == mGroup.getGroupId()) {
                list.remove(bean);
                DataManager.getInstance().saveGroups(list);
                return;
            }
        }
    }


    /**
     * 获取群用户
     */
    private void getGroupUsers() {
        ChatHttpMethods.getInstance().getGroupUsers(String.valueOf(mGroup.getGroupId()), new HttpObserver(new SubscriberOnNextListener<ArrayList<UserBean>>() {
            @Override
            public void onNext(ArrayList<UserBean> list, String msg) {
                if (getView() == null || list == null) {
                    return;
                }
                mGroupUsers = list;
                setText(R.id.tvGroupMemberNum, getString(R.string.chat_member_num_xxx, String.valueOf(mGroupUsers.size())));
                DataManager.getInstance().saveGroupUsers(mGroup.getGroupId(), list);
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
