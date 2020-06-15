package com.alsc.alsc_wallet.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.BuyCoinAdapter;
import com.alsc.alsc_wallet.adapter.CoinSymbolAdapter;

import java.util.ArrayList;

public class TradeFragment extends BaseFragment {

    private CoinSymbolAdapter mAdapter;
    private BuyCoinAdapter mAdapter2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.coinRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("USDT");
        list.add("BTC");
        list.add("ETH");
        getAdapter().addData(list);

        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter2().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter2());
        list = new ArrayList<>();
        list.add("USDT");
        list.add("BTC");
        list.add("ETH");
        getAdapter2().addData(list);
    }

    private CoinSymbolAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new CoinSymbolAdapter(getActivity());
        }
        return mAdapter;
    }

    private BuyCoinAdapter getAdapter2() {
        if (mAdapter2 == null) {
            mAdapter2 = new BuyCoinAdapter(getActivity());
        }
        return mAdapter2;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }
}
