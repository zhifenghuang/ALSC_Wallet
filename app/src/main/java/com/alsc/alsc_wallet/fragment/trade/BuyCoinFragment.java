package com.alsc.alsc_wallet.fragment.trade;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.BuyCoinAdapter;
import com.common.fragment.BaseFragment;

import java.util.ArrayList;

public class BuyCoinFragment extends BaseFragment {

    private BuyCoinAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycleview;
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


    private BuyCoinAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new BuyCoinAdapter(getActivity());
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
