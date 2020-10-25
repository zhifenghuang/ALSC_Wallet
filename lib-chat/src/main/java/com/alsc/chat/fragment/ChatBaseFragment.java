package com.alsc.chat.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.alsc.chat.R;
import com.common.activity.BaseActivity;
import com.alsc.chat.dialog.InputPasswordDialog;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.EnvelopeBean;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.GroupMessageBean;
import com.cao.commons.bean.chat.MessageType;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.common.fragment.BaseFragment;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.google.gson.Gson;
import com.zhangke.websocket.WebSocketHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fragment基类提供公共的页面跳转方面，公共弹窗等方法
 *
 * @author xiangwei.ma
 */
public abstract class ChatBaseFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }

    protected void showPayInGroupDialog(final GroupBean group, final UserBean inviteUser) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.chat_tip));
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.chat_the_group_need_pay_xxx_alsc, String.valueOf(group.getPayAmount())));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.chat_i_think));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.chat_pay_enter_group_1));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn2) {
                    showInputPayPasswordDialog(group, inviteUser);
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }

    private void showInputPayPasswordDialog(final GroupBean group, final UserBean inviteUser) {
        InputPasswordDialog dialog = new InputPasswordDialog(mContext, -1, null, null);
        dialog.setOnInputPasswordListener(new InputPasswordDialog.OnInputPasswordListener() {
            @Override
            public void afterCheckPassword() {
                if (getView() == null) {
                    return;
                }
                payEnterGroup(group, inviteUser);
            }
        });
        dialog.show();
    }

    private void payEnterGroup(final GroupBean group, final UserBean inviteUser) {
        ChatHttpMethods.getInstance().envelopeSend(group.getPayAmount(), 1, "", 6, -1, group.getGroupId(),
                new HttpObserver(new SubscriberOnNextListener<EnvelopeBean>() {
                    @Override
                    public void onNext(EnvelopeBean bean, String msg) {
                        ArrayList<UserBean> list = new ArrayList<>();
                        list.add(DataManager.getInstance().getUser());
                        sendInviteToGroupMsg(group, inviteUser, list);
                        if (bean == null) {
                            return;
                        }
                        showToast(R.string.chat_pay_enter_group_success);
                        addGroupInList(group);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, group);
                        gotoPager(GroupChatFragment.class, bundle);
                        ((BaseActivity) getActivity()).finishAllOtherActivity();
                    }
                }, getActivity(), (BaseActivity) getActivity()));
    }

    protected void removeFromGroupInList(GroupBean group) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == group.getGroupId()) {
                groups.remove(bean);
                DataManager.getInstance().saveGroups(groups);
                break;
            }
        }
    }


    protected void addGroupInList(GroupBean group) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        boolean isHad = false;
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == group.getGroupId()) {
                isHad = true;
                break;
            }
        }
        if (!isHad) {
            groups.add(group);
            DataManager.getInstance().saveGroups(groups);
        }
    }

    protected void updateGroup(GroupBean group) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == group.getGroupId()) {
                groups.remove(bean);
                groups.add(group);
                DataManager.getInstance().saveGroups(groups);
                break;
            }
        }
    }

    protected boolean isInGroup(long groupId) {
        ArrayList<GroupBean> groups = DataManager.getInstance().getGroups();
        for (GroupBean bean : groups) {
            if (bean.getGroupId() == groupId) {
                return true;
            }
        }
        return false;
    }

    public void sendInviteToGroupMsg(GroupBean group, UserBean inviteUser, List<UserBean> list) {
        GroupMessageBean groupMessageBean = new GroupMessageBean();
        groupMessageBean.setCmd(2100);
        groupMessageBean.setMsgType(inviteUser == null ? MessageType.TYPE_IN_GROUP_BY_QRCODE.ordinal() : MessageType.TYPE_INVITE_TO_GROUP.ordinal());
        groupMessageBean.setGroupId(group.getGroupId());
        if (inviteUser != null) {
            groupMessageBean.setContent(new Gson().toJson(inviteUser.toMap()));
        }
        groupMessageBean.setFromId(DataManager.getInstance().getUserId());
        ArrayList<HashMap<String, Object>> userList = new ArrayList<>();
        for (UserBean bean : list) {
            if (bean != null) {
                userList.add(bean.toMap());
            }
        }
        groupMessageBean.setExtra(new Gson().toJson(userList));
        if (TextUtils.isEmpty(groupMessageBean.getExtra())) {
            return;
        }
        WebSocketHandler.getDefault().send(groupMessageBean.toJson());
        groupMessageBean.setSendStatus(1);
        DatabaseOperate.getInstance().insert(groupMessageBean);
        EventBus.getDefault().post(groupMessageBean);
    }


    public void sendUpdateGroupMsg(GroupBean group, int msgType, String text) {
        GroupMessageBean groupMessageBean = new GroupMessageBean();
        groupMessageBean.setCmd(2100);
        groupMessageBean.setMsgType(msgType);
        groupMessageBean.setGroupId(group.getGroupId());
        groupMessageBean.setContent(text);
        groupMessageBean.setFromId(DataManager.getInstance().getUserId());
        ArrayList<HashMap<String, Object>> userList = new ArrayList<>();
        userList.add(DataManager.getInstance().getUser().toMap());
        groupMessageBean.setExtra(new Gson().toJson(userList));
        WebSocketHandler.getDefault().send(groupMessageBean.toJson());
        groupMessageBean.setSendStatus(1);
        DatabaseOperate.getInstance().insert(groupMessageBean);
        EventBus.getDefault().post(groupMessageBean);
    }


}
