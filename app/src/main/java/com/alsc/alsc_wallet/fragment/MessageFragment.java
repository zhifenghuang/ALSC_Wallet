package com.alsc.alsc_wallet.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.MessageAdapter;

import java.util.ArrayList;

public class MessageFragment extends BaseFragment {

    private MessageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("USDT");
        list.add("BTC");
        list.add("ETH");
        getAdapter().addData(list);
    }

    private MessageAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new MessageAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }
}
