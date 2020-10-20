package com.alsc.alsc_wallet.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.MainActivity;
import com.alsc.alsc_wallet.adapter.HotWalletAdapter;
import com.common.bean.CoinSymbolBean;
import com.alsc.alsc_wallet.fragment.online.WalletDetailFragment;
import com.common.fragment.BaseFragment;

public class OnlineWalletFragment extends BaseFragment {

    private HotWalletAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_wallet;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.rl, R.id.tvColdWallet);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eth, "ETH", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_usdt_2, "USDT", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eos, "EOS", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_ltc, "LTC", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_dash, "DASH", "1FyMFyFyFy......h4FyAVz"));
        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_zec, "ZEC", "1FyMFyFyFy......h4FyAVz"));
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl:
                gotoPager(WalletDetailFragment.class);
                break;
            case R.id.tvColdWallet:
                ((MainActivity) getActivity()).setWalletType(1);
                break;
        }
    }

    private HotWalletAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new HotWalletAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
