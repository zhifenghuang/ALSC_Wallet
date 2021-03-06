package com.alsc.chat.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alsc.chat.R;
import com.alsc.chat.http.ChatHttpMethods;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.cao.commons.bean.chat.GroupBean;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.GroupMessageBean;
import com.cao.commons.bean.chat.MessageType;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.zhangke.websocket.WebSocketHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupManagerFragment extends ChatBaseFragment {

    private GroupBean mGroup;
    private ArrayList<UserBean> mGroupMembers;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_manager;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        mGroup = (GroupBean) getArguments().getSerializable(Constants.BUNDLE_EXTRA);
        mGroupMembers = (ArrayList<UserBean>) getArguments().getSerializable(Constants.BUNDLE_EXTRA_2);
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_group_manager);
        setViewsOnClickListener(R.id.tvNewMemberRedPackage, R.id.tvEnterGroupWay,
                R.id.llAllForbidChat, R.id.tvForbidChatManager, R.id.llForbidAddFriend, R.id.llForbidSendUrl,
                R.id.tvGroupGive, R.id.tvEndGroup, R.id.tvMsgFilter);

        setImage(R.id.ivAllForbidChatSwitch, mGroup.getAllBlock() == 1 ?
                R.drawable.icon_switch_on : R.drawable.icon_switch_off);
        setImage(R.id.ivForbidAddFriendSwitch, mGroup.getDisableFriend() == 1 ?
                R.drawable.icon_switch_on : R.drawable.icon_switch_off);
        setImage(R.id.ivForbidSendUrlSwitch, mGroup.getDisableLink() == 1 ?
                R.drawable.icon_switch_on : R.drawable.icon_switch_off);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(GroupBean bean) {
        if (getView() != null) {
            mGroup = bean;
            if (mGroup.getGroupRole() != 3) {  //已经不是群主了，则要退出群管理页面
                goBack();
            }
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvNewMemberRedPackage) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            gotoPager(NewMemberRedPackageSettingFragment.class, bundle);
        } else if (id == R.id.tvEnterGroupWay) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            gotoPager(AddGroupWayFragment.class, bundle);
        } else if (id == R.id.tvMsgFilter) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            gotoPager(MsgFilterFragment.class, bundle);
        } else if (id == R.id.llAllForbidChat) {
            final int allBlock = mGroup.getAllBlock() == 1 ? 0 : 1;
            ChatHttpMethods.getInstance().allBlock(String.valueOf(mGroup.getGroupId()), String.valueOf(allBlock),
                    new HttpObserver(new SubscriberOnNextListener<GroupBean>() {
                        @Override
                        public void onNext(GroupBean bean, String msg) {
                            if (getView() == null) {
                                return;
                            }
                            mGroup.setAllBlock(allBlock);
                            EventBus.getDefault().post(mGroup);
                            setImage(R.id.ivAllForbidChatSwitch, mGroup.getAllBlock() == 1 ?
                                    R.drawable.icon_switch_on : R.drawable.icon_switch_off);

                            GroupMessageBean groupMsg = new GroupMessageBean();
                            groupMsg.setCmd(2100);
                            groupMsg.setMsgType(allBlock == 0 ? MessageType.TYPE_ALL_REMOVE_FORBID_CHAT.ordinal() : MessageType.TYPE_ALL_FORBID_CHAT.ordinal());
                            groupMsg.setFromId(DataManager.getInstance().getUserId());
                            groupMsg.setGroupId(mGroup.getGroupId());
                            WebSocketHandler.getDefault().send(groupMsg.toJson());

                            groupMsg.setSendStatus(1);
                            DatabaseOperate.getInstance().insert(groupMsg);
                            EventBus.getDefault().post(groupMsg);
                        }
                    }, getActivity(), (BaseActivity) getActivity()));

        } else if (id == R.id.tvForbidChatManager) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mGroup);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroupMembers);
            gotoPager(ForbidSayManagerFragment.class, bundle);
        } else if (id == R.id.llForbidAddFriend) {
            final int disableFriend = mGroup.getDisableFriend() == 1 ? 0 : 1;
            ChatHttpMethods.getInstance().disableFriend(String.valueOf(mGroup.getGroupId()), String.valueOf(disableFriend),
                    new HttpObserver(new SubscriberOnNextListener<GroupBean>() {
                        @Override
                        public void onNext(GroupBean bean, String msg) {
                            if (getView() == null) {
                                return;
                            }
                            mGroup.setDisableFriend(disableFriend);
                            EventBus.getDefault().post(mGroup);
                            setImage(R.id.ivForbidAddFriendSwitch, mGroup.getDisableFriend() == 1 ?
                                    R.drawable.icon_switch_on : R.drawable.icon_switch_off);

                            sendRefreshGroupSystemMsg();
                        }
                    }, getActivity(), (BaseActivity) getActivity()));
        } else if (id == R.id.llForbidSendUrl) {
            final int disableLink = mGroup.getDisableLink() == 1 ? 0 : 1;
            ChatHttpMethods.getInstance().disableLink(String.valueOf(mGroup.getGroupId()), String.valueOf(disableLink),
                    new HttpObserver(new SubscriberOnNextListener<GroupBean>() {
                        @Override
                        public void onNext(GroupBean bean, String msg) {
                            if (getView() == null) {
                                return;
                            }
                            mGroup.setDisableLink(disableLink);
                            EventBus.getDefault().post(mGroup);
                            setImage(R.id.ivForbidSendUrlSwitch, mGroup.getDisableLink() == 1 ?
                                    R.drawable.icon_switch_on : R.drawable.icon_switch_off);

                            sendRefreshGroupSystemMsg();
                        }
                    }, getActivity(), (BaseActivity) getActivity()));
        } else if (id == R.id.tvGroupGive) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_EXTRA, SelectFriendFragment.FROM_TRANSFER_GROUP);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mGroupMembers);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_3, mGroup);
            gotoPager(SelectFriendFragment.class, bundle);
        } else if (id == R.id.tvEndGroup) {
            showEndGroupDialog();
        }
    }

    private void sendRefreshGroupSystemMsg() {
        GroupMessageBean messageBean = GroupMessageBean.getGroupSystemMsg(DataManager.getInstance().getUserId(), mGroup.getGroupId(),
                Constants.REFRESH_GROUP_INFO, mGroup.getGroupId());
        WebSocketHandler.getDefault().send(messageBean.toJson());
    }

    private void showEndGroupDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.chat_tip));
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.chat_chat_are_you_sure_end_group));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.chat_cancel));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.chat_ok));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn2) {
                    ChatHttpMethods.getInstance().dismissGroup(String.valueOf(mGroup.getGroupId()),
                            new HttpObserver(new SubscriberOnNextListener() {
                                @Override
                                public void onNext(Object o, String msg) {
                                    if (getView() == null) {
                                        return;
                                    }
                                    removeGroup();
                                    UserBean myInfo = DataManager.getInstance().getUser();
                                    DatabaseOperate.getInstance().deleteGroupChatRecord(myInfo.getUserId(), mGroup.getGroupId());
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put(Constants.END_GROUP, mGroup.getGroupId());
                                    EventBus.getDefault().post(map);
                                    goBack();
                                }
                            }, getActivity(), (BaseActivity) getActivity()));
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
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
}
