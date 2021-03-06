package com.alsc.chat.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.common.activity.BaseActivity;
import com.alsc.chat.adapter.LabelUserAdapter;
import com.alsc.chat.http.ChatHttpMethods;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.GroupBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.List;

public class AddGroupFragment extends ChatBaseFragment {

    private LabelUserAdapter mAdapter;

    private ArrayList<UserBean> mGroupUsers, mShowGroupUsers;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_group;
    }

    @Override
    protected void onViewCreated(View view) {
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_group_chat);
        setViewVisible(R.id.tvLeft);
        setText(R.id.tvLeft, R.string.chat_save);
        setViewsOnClickListener(R.id.tvLeft);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        mGroupUsers = new ArrayList<>();
        mShowGroupUsers = new ArrayList<>();
    }

    private LabelUserAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new LabelUserAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public void updateUIText() {
        Object object = DataManager.getInstance().getObject();
        if (object instanceof ArrayList) {
            mGroupUsers.addAll((ArrayList<UserBean>) object);
        }
        mShowGroupUsers.clear();
        mShowGroupUsers.addAll(mGroupUsers);
        mShowGroupUsers.add(null);
        mShowGroupUsers.add(null);
        getAdapter().setUsers(mGroupUsers);
        getAdapter().setNewData(mShowGroupUsers);
        getAdapter().notifyDataSetChanged();
        DataManager.getInstance().setObject(null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvLeft) {
            String groupName = getTextById(R.id.etGroupName);
            if (TextUtils.isEmpty(groupName)) {
                return;
            }
            final List<UserBean> list = getAdapter().getData();
            if (list.size() == 2) {
                return;
            }
            ArrayList<Long> userIds = new ArrayList<>();
            for (UserBean bean : list) {
                if (bean != null) {
                    userIds.add(bean.getContactId());
                }
            }
            userIds.add(DataManager.getInstance().getUser().getUserId());
            ChatHttpMethods.getInstance().createGroup(groupName, userIds, new HttpObserver(new SubscriberOnNextListener<GroupBean>() {
                @Override
                public void onNext(GroupBean group, String msg) {
                    sendInviteToGroupMsg(group, DataManager.getInstance().getUser(), list);
                    if (getView() == null) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_EXTRA, group);
                    gotoPager(GroupChatFragment.class, bundle);
                    ((BaseActivity) getActivity()).finishAllOtherActivity();
                }
            }, getActivity(), (BaseActivity) getActivity()));
        }
    }
}
