package com.alsc.chat.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.chat.R;
import com.alsc.chat.adapter.PayInGroupAdapter;
import com.alsc.chat.http.ChatHttpMethods;
import com.common.activity.BaseActivity;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.alsc.chat.utils.Constants;
import com.cao.commons.bean.chat.EnvelopeBean;
import com.cao.commons.bean.chat.GroupBean;

import java.util.ArrayList;

public class PayEnterGroupDetailFragment extends ChatBaseFragment {

    private GroupBean mGroup;

    private PayInGroupAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay_enter_group_record;
    }

    @Override
    protected void onViewCreated(View view) {
        mGroup = (GroupBean) getArguments().getSerializable(Constants.BUNDLE_EXTRA);
        setTopStatusBarStyle(view);
        setText(R.id.tvTitle, R.string.chat_pay_enter_group_record);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        payInGroupRecord();
    }

    private PayInGroupAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new PayInGroupAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }

    private void payInGroupRecord() {
        ChatHttpMethods.getInstance().newcomerList(String.valueOf(mGroup.getGroupId()), 6,
                new HttpObserver(new SubscriberOnNextListener<ArrayList<EnvelopeBean>>() {
                    @Override
                    public void onNext(ArrayList<EnvelopeBean> list, String msg) {
                        if (getView() == null) {
                            return;
                        }
                        getAdapter().setNewData(list);
                    }
                }, getActivity(), (BaseActivity) getActivity()));
    }

}
