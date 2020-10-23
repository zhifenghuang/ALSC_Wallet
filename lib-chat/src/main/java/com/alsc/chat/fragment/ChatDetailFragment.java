package com.alsc.chat.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alsc.chat.R;
import com.cao.commons.bean.chat.BasicMessage;
import com.cao.commons.bean.chat.ChatSettingBean;
import com.cao.commons.bean.chat.ChatSubBean;
import com.cao.commons.bean.chat.MessageBean;
import com.cao.commons.bean.chat.MessageType;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.alsc.chat.http.ChatHttpMethods;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.cao.commons.manager.DataManager;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.Utils;
import com.google.gson.Gson;
import com.zhangke.websocket.WebSocketHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class ChatDetailFragment extends ChatBaseFragment {

    private UserBean mUserInfo;

    private HashMap<String, ChatSubBean> mSettings;
    private ChatSubBean mChatSubBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        mSettings = DataManager.getInstance().getChatSubSettings();
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_detail);
        mUserInfo = (UserBean) getArguments().getSerializable(Constants.BUNDLE_EXTRA);
        mChatSubBean = mSettings.get("user_" + mUserInfo.getContactId());
        if (mChatSubBean == null) {
            mChatSubBean = new ChatSubBean();
        }
        setText(R.id.tvId, getString(R.string.chat_account_2, mUserInfo.getLoginAccount()));
        setImage(R.id.ivMsgSwitch, mChatSubBean.getNotInterupt() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
        setImage(R.id.ivTopChatSwitch, mChatSubBean.getIsTop() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
        setViewsOnClickListener(R.id.llAddLabel, R.id.llAddMemo, R.id.llSetStarFriend,
                R.id.llRecommendFriend, R.id.tvReadDelete, R.id.llAddToBlackList, R.id.tvDeleteFriend,
                R.id.tvSearchMsg, R.id.llMsgSwitch, R.id.llTopChat);
        resetUI();
        refreshUserInfo();
    }

    private void resetUI() {
        setText(R.id.tvNick, mUserInfo.getNickName2());
        setText(R.id.tvMemo, "(" + mUserInfo.getMemo() + ")");
        setText(R.id.tvMemo2, mUserInfo.getMemo());
        setText(R.id.tvLocation, getString(mUserInfo.getGender() == 1 ? R.string.chat_male : R.string.chat_female) + "  " +
                (TextUtils.isEmpty(mUserInfo.getDistrict()) ? getString(R.string.chat_default_area) : mUserInfo.getDistrict()));
        Utils.displayAvatar(getActivity(), R.drawable.chat_default_group_avatar, mUserInfo.getAvatarUrl(), fv(R.id.ivAvatar));
        setImage(R.id.ivSwitchStarFriend, mUserInfo.getStar() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
        setImage(R.id.ivBlackSwitch, mUserInfo.getBlock() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.llMsgSwitch) {
            int msgSwitch = mChatSubBean.getNotInterupt() == 1 ? 0 : 1;
            mChatSubBean.setNotInterupt(msgSwitch);
            setImage(R.id.ivMsgSwitch, mChatSubBean.getNotInterupt() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
            mSettings.put("user_" + mUserInfo.getContactId(), mChatSubBean);
            DataManager.getInstance().saveChatSubSettings(mSettings);
        } else if (id == R.id.llTopChat) {
            int topSwitch = mChatSubBean.getIsTop() == 1 ? 0 : 1;
            mChatSubBean.setIsTop(topSwitch);
            setImage(R.id.ivTopChatSwitch, mChatSubBean.getIsTop() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
            mSettings.put("user_" + mUserInfo.getContactId(), mChatSubBean);
            DataManager.getInstance().saveChatSubSettings(mSettings);
        } else if (id == R.id.ivAvatar) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mUserInfo);
            gotoPager(UserInfoFragment.class, bundle);
        } else if (id == R.id.tvSearchMsg) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_EXTRA, SearchFragment.SEARCH_CHAT_RECORD);
            bundle.putSerializable(Constants.BUNDLE_EXTRA_2, mUserInfo);
            gotoPager(SearchFragment.class, bundle);
        } else if (id == R.id.llAddLabel) {
            gotoPager(LabelFragment.class);
        } else if (id == R.id.llAddMemo) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, mUserInfo);
            gotoPager(UpdateNickFragment.class, bundle);
        } else if (id == R.id.llSetStarFriend) {
            operatorStar(mUserInfo.getStar() == 0 ? 1 : 0);
        } else if (id == R.id.tvDeleteFriend) {
            showDeleteContact(0);
        } else if (id == R.id.llAddToBlackList) {
            if (mUserInfo.getBlock() == 0) {
                showDeleteContact(1);
            } else {
                blockUser(0);
            }
        } else if (id == R.id.tvReadDelete) {
            ChatSettingBean bean = DataManager.getInstance().getChatSetting();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_EXTRA, ChooseFragment.CHOOSE_DELETE_TYPE);
            bundle.putInt(Constants.BUNDLE_EXTRA_2, bean.getReadDeleteType());
            gotoPager(ChooseFragment.class, bundle);
        } else if (id == R.id.llRecommendFriend) {
            BasicMessage msg = new MessageBean();
            msg.setMsgType(MessageType.TYPE_RECOMAND_USER.ordinal());
            msg.setContent(new Gson().toJson(mUserInfo.toMap()));
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_EXTRA, msg);
            gotoPager(TransferMsgFragment.class, bundle);
        }
    }

    private void showDeleteContact(final int type) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                ((TextView) view.findViewById(R.id.tv1)).setText(getString(R.string.chat_tip));
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(type == 0 ? R.string.chat_are_you_sure_delete_contact : R.string.chat_are_you_sure_block_contact, mUserInfo.getNickName()));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.chat_cancel));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.chat_ok));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn2) {
                    if (type == 0) {
                        removeFriend();
                    } else {
                        blockUser(1);
                    }
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }

    private void removeFriend() {
        ChatHttpMethods.getInstance().removeContact(mUserInfo.getContactId(), new HttpObserver(new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o, String msg) {
                UserBean myInfo = DataManager.getInstance().getUser();
                MessageBean bean = MessageBean.getSystemMsg(myInfo.getUserId(), mUserInfo.getContactId(), Constants.REMOVE_FRIEND, myInfo.getUserId());
                WebSocketHandler.getDefault().send(bean.toJson());
                if (getView() == null) {
                    return;
                }
                DatabaseOperate.getInstance().deleteUserChatRecord(myInfo.getUserId(), mUserInfo.getContactId());
                HashMap<String, Long> map = new HashMap<>();
                map.put(Constants.REMOVE_FRIEND, mUserInfo.getContactId());
                EventBus.getDefault().post(map);
                goBack();
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }

    private void blockUser(final int block) {
        ChatHttpMethods.getInstance().operateContact(mUserInfo.getContactId(), -1, "", block, new HttpObserver(new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o, String msg) {
                if (getView() == null) {
                    return;
                }
                mUserInfo.setBlock(block);
                HashMap<String, Long> map = new HashMap<>();
                if (block == 1) {
                    map.put(Constants.BLOCK_FRIEND, mUserInfo.getContactId());
                } else {
                    map.put(Constants.REMOVE_BLOCK, mUserInfo.getContactId());
                }
                setImage(R.id.ivBlackSwitch, mUserInfo.getBlock() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
                EventBus.getDefault().post(map);
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }

    private void operatorStar(final int star) {
        ChatHttpMethods.getInstance().operateContact(mUserInfo.getContactId(), star, "", -1, new HttpObserver(new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o, String msg) {
                if (getView() == null) {
                    return;
                }
                mUserInfo.setStar(star);
                setImage(R.id.ivSwitchStarFriend, mUserInfo.getStar() == 1 ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
                HashMap<String, UserBean> map = new HashMap<>();
                map.put(Constants.EDIT_FRIEND, mUserInfo);
                EventBus.getDefault().post(map);
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }

    private void refreshUserInfo() {
        ChatHttpMethods.getInstance().getContactProfile(String.valueOf(mUserInfo.getContactId()), new HttpObserver(new SubscriberOnNextListener<UserBean>() {
            @Override
            public void onNext(UserBean bean, String msg) {
                if (getView() == null || bean == null) {
                    return;
                }
                mUserInfo = bean;
                resetUI();
                HashMap<String, UserBean> map = new HashMap<>();
                map.put(Constants.EDIT_FRIEND, mUserInfo);
                EventBus.getDefault().post(map);
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(HashMap<String, Object> map) {
        if (getView() != null && map != null) {
            if (map.containsKey(Constants.EDIT_FRIEND)) {
                mUserInfo = (UserBean) map.get(Constants.EDIT_FRIEND);
                resetUI();
            } else if (map.containsKey(Constants.REMOVE_FRIEND)) {
                goBack();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
