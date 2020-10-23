package com.alsc.chat.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.alsc.chat.adapter.LabelFriendAdapter;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.UserBean;
import com.alsc.chat.http.ChatHttpMethods;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

public class StarFriendFragment extends ChatBaseFragment {

    private LabelFriendAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_label_friends;
    }

    @Override
    protected void onViewCreated(View view) {
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_star_friends);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getStarFriend();
    }

    private LabelFriendAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new LabelFriendAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_EXTRA, getAdapter().getItem(position));
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

    }

    private void getStarFriend() {
        ChatHttpMethods.getInstance().getFriends(1, new HttpObserver(new SubscriberOnNextListener<ArrayList<UserBean>>() {
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
