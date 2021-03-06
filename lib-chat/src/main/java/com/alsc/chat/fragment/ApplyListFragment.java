package com.alsc.chat.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.alsc.chat.adapter.ApplyAdapter;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.UserBean;
import com.alsc.chat.http.ChatHttpMethods;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

public class ApplyListFragment extends ChatBaseFragment {

    private ApplyAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_list;
    }

    @Override
    protected void onViewCreated(View view) {
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_new_friend);
        setViewVisible(R.id.tvLeft);
        setText(R.id.tvLeft, R.string.chat_add_friend);
        setViewsOnClickListener(R.id.tvLeft);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        reviewContact();
    }

    private ApplyAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ApplyAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    UserBean bean = mAdapter.getItem(position);
                    UserBean userInfo = new UserBean();
                    userInfo.setUserId(bean.getUserId());
                    userInfo.setContactId(bean.getUserId());
                    userInfo.setDistrict(bean.getDistrict());
                    userInfo.setAvatarUrl(bean.getAvatarUrl());
                    userInfo.setGender(bean.getGender());
                    userInfo.setNickName(bean.getNickName());
                    userInfo.setLoginAccount(bean.getLoginAccount());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_EXTRA, userInfo);
                    gotoPager(UserInfoFragment.class, bundle);
                }
            });
        }
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvLeft) {
            gotoPager(AddFriendFragment.class);
        }
    }

    private void reviewContact() {
        ChatHttpMethods.getInstance().reviewContact(new HttpObserver(new SubscriberOnNextListener<ArrayList<UserBean>>() {
            @Override
            public void onNext(ArrayList<UserBean> list, String msg) {
                if (getView() == null) {
                    return;
                }
                getAdapter().setNewData(list);
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
