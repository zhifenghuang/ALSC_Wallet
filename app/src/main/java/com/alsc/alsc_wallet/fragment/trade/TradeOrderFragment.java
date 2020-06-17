package com.alsc.alsc_wallet.fragment.trade;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.CoinSymbolAdapter;
import com.alsc.alsc_wallet.adapter.TradeOrderAdapter;
import com.alsc.alsc_wallet.fragment.BaseFragment;

import java.util.ArrayList;

public class TradeOrderFragment extends BaseFragment {

    private CoinSymbolAdapter mAdapter;
    private TradeOrderAdapter mAdapter2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade_order;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle,R.string.wallet_money_order);
        RecyclerView recyclerView = view.findViewById(R.id.horRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.wallet_puchase));
        list.add(getString(R.string.wallet_compling));
        list.add(getString(R.string.wallet_sell));
        list.add(getString(R.string.wallet_finished));
        getAdapter().addData(list);

        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter2().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter2());
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        getAdapter2().addData(list);
    }

    private CoinSymbolAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new CoinSymbolAdapter(getActivity());
        }
        return mAdapter;
    }

    private TradeOrderAdapter getAdapter2() {
        if (mAdapter2 == null) {
            mAdapter2 = new TradeOrderAdapter(getActivity());
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
