package com.alsc.chat.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.alsc.chat.adapter.GroupAdapter;
import com.cao.commons.bean.chat.GroupBean;
import com.alsc.chat.http.ChatHttpMethods;
import com.cao.commons.manager.DataManager;
import com.alsc.chat.utils.Constants;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupListFragment extends ChatBaseFragment {

    public static final int FROM_TRANSFER_MSG = 1;

    private GroupAdapter mAdapter;

    private int mFromType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_list;
    }

    @Override
    protected void onViewCreated(View view) {
        mFromType = getArguments().getInt(Constants.BUNDLE_EXTRA, 0);
 //       EventBus.getDefault().register(this);
        setTopStatusBarStyle(view);
        if (mFromType != FROM_TRANSFER_MSG) {
//            setViewVisible(R.id.btnRight);
//            setText(R.id.btnRight, R.string.chat_create);
//            setViewsOnClickListener(R.id.btnRight);
            setText(R.id.tvTitle, R.string.chat_group_chat);
        }else {
            setText(R.id.tvTitle, R.string.chat_choose_group);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().setNewData(DataManager.getInstance().getGroups());
        getGroups();
    }


    private GroupAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new GroupAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mFromType != FROM_TRANSFER_MSG) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, mAdapter.getItem(position));
                        gotoPager(GroupChatFragment.class, bundle);
                        ((BaseActivity) getActivity()).finishAllOtherActivity();
                    } else {
                        HashMap<String, GroupBean> map = new HashMap<>();
                        map.put(Constants.SELECT_A_GROUP, mAdapter.getItem(position));
                        EventBus.getDefault().post(map);
                        goBack();
                    }
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

    private void getGroups() {
        ChatHttpMethods.getInstance().getGroups(1, Integer.MAX_VALUE - 1, new HttpObserver(new SubscriberOnNextListener<ArrayList<GroupBean>>() {
            @Override
            public void onNext(ArrayList<GroupBean> list, String msg) {
                if (getView() == null || list == null) {
                    return;
                }
                getAdapter().setNewData(list);
                DataManager.getInstance().saveGroups(list);
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
